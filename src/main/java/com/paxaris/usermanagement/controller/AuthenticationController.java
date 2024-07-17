package com.paxaris.usermanagement.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import com.paxaris.usermanagement.constant.UserStatus;
import com.paxaris.usermanagement.dto.AuthResponse;
import com.paxaris.usermanagement.dto.LogOutRequest;
import com.paxaris.usermanagement.dto.MessageResponse;
import com.paxaris.usermanagement.dto.SignupRequest;
import com.paxaris.usermanagement.dto.TokenRefreshRequest;
import com.paxaris.usermanagement.dto.TokenRefreshResponse;
import com.paxaris.usermanagement.dto.UserDetailsRequestDTO;
import com.paxaris.usermanagement.dto.UserLoginDTO;
import com.paxaris.usermanagement.mastertenant.config.DBContextHolder;
import com.paxaris.usermanagement.mastertenant.entity.MasterTenant;
import com.paxaris.usermanagement.mastertenant.exception.TokenRefreshException;
import com.paxaris.usermanagement.mastertenant.service.MasterTenantService;
import com.paxaris.usermanagement.mastertenant.service.RefreshTokenService;
import com.paxaris.usermanagement.security.UserTenantInformation;
import com.paxaris.usermanagement.tenant.entity.RefreshToken;
import com.paxaris.usermanagement.tenant.entity.Role;
import com.paxaris.usermanagement.tenant.entity.User;
import com.paxaris.usermanagement.tenant.repository.RefreshTokenRepository;
import com.paxaris.usermanagement.tenant.repository.RoleRepository;
import com.paxaris.usermanagement.tenant.repository.UserRepository;
import com.paxaris.usermanagement.util.JwtTokenUtil;

/**
 * @author Rohit Mehra
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	private Map<String, String> mapValue = new HashMap<>();
	private Map<String, String> userDbMap = new HashMap<>();

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RefreshTokenService refreshTokenService;
	@Autowired
	RefreshTokenRepository refreshTokenRepository;
	@Autowired
	MasterTenantService masterTenantService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtTokenUtil jwtUtils;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> userLogin(@RequestBody @NotNull UserLoginDTO userLoginDTO) {
		LOGGER.info("userLogin() method call...");
		if (null == userLoginDTO.getUserName() || userLoginDTO.getUserName().isEmpty()) {
			return new ResponseEntity<>("User name is required", HttpStatus.BAD_REQUEST);
		}

		validateAndLoadCrntDB(userLoginDTO.getTenantOrClientId(), userLoginDTO.getUserName());
		User user = userRepository.findByUserName(userLoginDTO.getUserName());
		if(user==null) {
			return new ResponseEntity<>("User name not exists", HttpStatus.BAD_REQUEST);
		}else if(!encoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
			return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
		}
		
		final String token = jwtTokenUtil.generateToken(user.getUserId(),user.getUserName(),
				String.valueOf(userLoginDTO.getTenantOrClientId()), user.getRoles());
		// Map the value into applicationScope bean
		setMetaDataAfterLogin();
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserName());
		return ResponseEntity.ok(new AuthResponse(user.getUserName(), token, refreshToken.getToken()));
	}

	private void validateAndLoadCrntDB(int tenant, String userName) {
		// set database parameter
		MasterTenant masterTenant = masterTenantService.findByClientId(tenant);
		if (null == masterTenant || masterTenant.getStatus().toUpperCase().equals(UserStatus.INACTIVE)) {
			throw new RuntimeException("Please contact service provider.");
		}
		// Entry Client Wise value dbName store into bean.
		DBContextHolder.setCurrentDb(masterTenant.getDbName());
		mapValue.put(userName, masterTenant.getDbName());
	}

	@Bean(name = "userTenantInfo")
	@ApplicationScope
	public UserTenantInformation setMetaDataAfterLogin() {
		UserTenantInformation tenantInformation = new UserTenantInformation();
		if (mapValue.size() > 0) {
			for (String key : mapValue.keySet()) {
				if (null == userDbMap.get(key)) {
					// Here Assign putAll due to all time one come.
					userDbMap.putAll(mapValue);
				} else {
					userDbMap.put(key, mapValue.get(key));
				}
			}
			mapValue = new HashMap<>();
		}
		tenantInformation.setMap(userDbMap);
		return tenantInformation;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		validateAndLoadCrntDB(signUpRequest.getTenantOrClientId(), signUpRequest.getUserName());
		if (userRepository.existsByUserName(signUpRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getFullName(), signUpRequest.getGender(), signUpRequest.getUserName(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), UserStatus.ACTIVE.name());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			throw new RuntimeException("Error: Role is not found.");
		} else {
			strRoles.forEach(role -> {
				Role adminRole = roleRepository.findByName(role)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);

			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		validateAndLoadCrntDB(request.getTenantOrClientId(), request.getUserName());
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtTokenUtil.generateToken(user.getUserId(),user.getUserName(),
							String.valueOf(request.getTenantOrClientId()), user.getRoles());
					;
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
		validateAndLoadCrntDB(logOutRequest.getTenantOrClientId(), logOutRequest.getUserName());
		refreshTokenRepository.deleteByUser(userRepository.findByUserName(logOutRequest.getUserName()));
		return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	}

	@PostMapping("/get-user-info")
	public ResponseEntity<?> fetchUserDetails(@Valid @RequestBody UserDetailsRequestDTO userDetailsRequestDTO) {
		validateAndLoadCrntDB(userDetailsRequestDTO.getTenantOrClientId(), userDetailsRequestDTO.getUserName());
		return ResponseEntity.ok(userRepository.findByUserName(userDetailsRequestDTO.getUserName()));
	}
}
