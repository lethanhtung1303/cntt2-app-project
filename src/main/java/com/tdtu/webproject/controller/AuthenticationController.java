package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.AuthenticationService;
import generater.openapi.api.AuthenticationApi;
import generater.openapi.model.LoginRequest;
import generater.openapi.model.UserResponse;
import generater.openapi.model.UserResponseResults;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<UserResponse> login(
            @ApiParam(value = "")
            @Valid
            @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult1) {
        UserResponseResults userResponse = authenticationService.findUser(loginRequest);
        return ResponseEntity.ok(UserResponse.builder()
                .status(HttpStatus.OK.value())
                .results(UserResponseResults.builder()
                        .userRoles(userResponse.getUserRoles())
                        .user(userResponse.getUser())
                        .build())
                .build());
    }
}
