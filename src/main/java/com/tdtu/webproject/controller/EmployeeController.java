package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.EmployeeService;
import generater.openapi.api.EmployeeApi;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponse> employee(
            @Pattern(regexp = "^(\\d{1,19},)*\\d{1,19}$")
            @ApiParam(value = "")
            @Validated
            @RequestParam(value = "employeeIds", required = false)
            String employeeIds) {
        return ResponseEntity.ok(EmployeeResponse.builder()
                .status(HttpStatus.OK.value())
                .results(EmployeeResponseResults.builder()
                        .resultsTotalCount(employeeService.count(employeeIds))
                        .employees(employeeService.find(employeeIds))
                        .build())
                .build());
    }
}
