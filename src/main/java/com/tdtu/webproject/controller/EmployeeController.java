package com.tdtu.webproject.controller;

import generater.openapi.api.EmployeeApi;
import generater.openapi.model.EmployeeDetailResponse;
import generater.openapi.model.EmployeeResponse;
import generater.openapi.model.EmployeeResponseResults;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {
    @Override
    public ResponseEntity<EmployeeResponse> employee(@Pattern(regexp = "^[0-9]{1,19}$") @ApiParam(value = "") @Validated @RequestParam(value = "employeeId", required = false) String employeeId) {
        return ResponseEntity.ok(EmployeeResponse.builder()
                .status(HttpStatus.OK.value())
                .results(EmployeeResponseResults.builder()
                        .resultsTotalCount(1L)
                        .employees(List.of(EmployeeDetailResponse.builder()
                                .ID(1)
                                .build()))
                        .build())
                .build());
    }
}
