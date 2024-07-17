package com.paxaris.usermanagement.controller;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paxaris.usermanagement.security.RequestAuthorization;
import com.paxaris.usermanagement.tenant.service.ProductService;

/**
 * @author Rohit Mehra
 */
@RestController
@RequestMapping("/api/validate")
public class ValidateRequestController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateRequestController.class);

	@RequestAuthorization
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Object> isRequstValid() {
		LOGGER.info("is valid request");
		return new ResponseEntity<>("{\"success\":true}", HttpStatus.OK);
	}

}
