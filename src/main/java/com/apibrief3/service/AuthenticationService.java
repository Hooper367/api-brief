package com.apibrief3.service;


import com.apibrief3.config.JwtService;
import com.apibrief3.exception.EntityAlreadyExistsException;
import com.apibrief3.exception.EntityNotFoundException;
import com.apibrief3.model.Role;
import com.apibrief3.model.User;
import com.apibrief3.record.authentication.AuthenticationRequest;
import com.apibrief3.record.authentication.AuthenticationResponse;
import com.apibrief3.record.userRequest.UserRegistrationRequest;
import com.apibrief3.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public record AuthenticationService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        AuthenticationManager authenticationManager
) {

  public AuthenticationResponse register(UserRegistrationRequest request) {

    // TODO : Random generate 4 numbers code then hash it
    if (userRepository.existsByEmailIgnoreCase(request.email())){
        throw new EntityAlreadyExistsException(User.class, "email", request.email());
    }

    User user = User.builder()
        .email(request.email().toLowerCase())
        .firstName(request.firstName())
        .lastName(request.lastName())
        .password(passwordEncoder.encode(request.password()))
        .role(Role.USER)
        .build();

    userRepository.save(user);

    return new AuthenticationResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user));
  }

  public AuthenticationResponse login(AuthenticationRequest request) {

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email().toLowerCase(), request.password()));

    User user = userRepository.findByEmailIgnoreCase(request.email())
            .orElseThrow(() -> new EntityNotFoundException(User.class, "email", request.email()));

    return new AuthenticationResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user));
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);

    if (userEmail != null) {
      User user = userRepository.findByEmailIgnoreCase(userEmail)
              .orElseThrow(() -> new EntityNotFoundException(User.class, "email", userEmail));

      if (jwtService.isTokenValid(refreshToken, user)) {
        String accessToken = jwtService.generateToken(user);
        AuthenticationResponse authResponse = new AuthenticationResponse(accessToken, refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
