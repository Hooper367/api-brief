package com.apibrief3.controller;


import com.apibrief3.exception.ValidationException;
import com.apibrief3.record.authentication.AuthenticationRequest;
import com.apibrief3.record.authentication.AuthenticationResponse;
import com.apibrief3.record.userRequest.UserRegistrationRequest;
import com.apibrief3.repository.UserRepository;
import com.apibrief3.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/auth")
public record AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository) {

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserRegistrationRequest request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) throw new ValidationException(bindingResult.getFieldErrors());
    return ResponseEntity.ok(authenticationService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) throw new ValidationException(bindingResult.getFieldErrors());
    return ResponseEntity.ok(authenticationService.login(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    authenticationService.refreshToken(request, response);
  }

}
