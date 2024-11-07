package com.melpy.perpus.auth;

import com.melpy.perpus.dto.UserDto;
import com.melpy.perpus.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @Operation(summary = "User login to get access token.")
    @Parameter()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email and access token", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "invalid credentials", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            String accessToken = jwtUtil.generateAccessToken(authentication);
            AuthResponse response = new AuthResponse(request.getEmail(), accessToken);
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Register a new user to create an account for login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully, returns user details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto request) {
        UserDto user = userService.createUser(request);
        return ResponseEntity.ok().body(user);
    }

}
