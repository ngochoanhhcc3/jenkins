package com.example.jwt2.javainuse.controller;

import com.example.jwt2.javainuse.config.GooglePojo;
import com.example.jwt2.javainuse.config.GoogleUtils;
import com.example.jwt2.javainuse.config.JwtTokenUtil;
import com.example.jwt2.javainuse.config.RestFB;
import com.example.jwt2.javainuse.entity.*;
import com.example.jwt2.javainuse.model.JwtRequest;
import com.example.jwt2.javainuse.service.UserService;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

	HttpHeaders responseHeaders = new HttpHeaders();

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private GoogleUtils googleUtils;

	@Autowired
	private UserService userService;

	@Autowired
	private RestFB restFb;

	@RequestMapping({ "/hello" })
	public String hello() {
		return "Fuck";
	}

	@RequestMapping({ "/hello/123" })
	public String hello1() {
		return "Fuck123";
	}

	@PostMapping("/register")
	public String register(@RequestBody UserDto userRequest){

		User uRegister = new User();
		uRegister.setUsername(userRequest.getUserName());
		uRegister.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
		userService.createUser(uRegister);

		User userRegister = userService.getUserByUserNameRegister(userRequest.getUserName());
		CourseRatingKey key = new CourseRatingKey();
		key.setUserId(userRegister.getId());
		key.setRoleId(Long.valueOf(userRequest.getRole()));
		UserRole userRole = new UserRole();
		userRole.setId(key);
		userService.createRole(userRole);

		return "ok";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		responseHeaders.remove("Authentication");
		responseHeaders.add("Authentication",token);
		return ResponseEntity.ok().headers(responseHeaders)
				.body(userDetails);
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@RequestMapping("/login-google")
	public String loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "error";
		}

		String accessToken = googleUtils.getToken(code);
		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(googlePojo.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);
		responseHeaders.remove("Authentication");
		responseHeaders.add("Authentication",token);
		return token;
	}

	@RequestMapping("/login-facebook")
	public String loginFacebook(HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "error";
		}
		String accessToken = restFb.getToken(code);

		com.restfb.types.User user = restFb.getUserInfo(accessToken);
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(user.getName());

		final String token = jwtTokenUtil.generateToken(userDetails);
		responseHeaders.remove("Authentication");
		responseHeaders.add("Authentication",token);
		return token;
	}

}
