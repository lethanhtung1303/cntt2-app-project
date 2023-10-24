package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.EmployeeService;
import generater.openapi.api.EmployeeApi;
import generater.openapi.model.EmployeeResponse;
import generater.openapi.model.EmployeeResponseResults;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponse> employee(String employeeIds) {
        return ResponseEntity.ok(EmployeeResponse.builder()
                .status(HttpStatus.OK.value())
                .results(EmployeeResponseResults.builder()
                        .resultsTotalCount(employeeService.count(employeeIds))
                        .employees(employeeService.find(employeeIds))
                        .build())
                .build());
    }
}
