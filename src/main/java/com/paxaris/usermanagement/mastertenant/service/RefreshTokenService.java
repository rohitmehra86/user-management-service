package com.paxaris.usermanagement.mastertenant.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paxaris.usermanagement.mastertenant.exception.TokenRefreshException;
import com.paxaris.usermanagement.tenant.entity.RefreshToken;
import com.paxaris.usermanagement.tenant.repository.RefreshTokenRepository;
import com.paxaris.usermanagement.tenant.repository.UserRepository;

@Service
public class RefreshTokenService {
	@Value("${userManagement.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(String userName) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findByUserName(userName));
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	@Transactional("masterTransactionManager")
	public int deleteByUserName(String userName) {
		return refreshTokenRepository.deleteByUser(userRepository.findByUserName(userName));
	}
}
