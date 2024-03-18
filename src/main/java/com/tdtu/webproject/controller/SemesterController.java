package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.SemesterService;
import generater.openapi.api.SemesterApi;
import generater.openapi.model.SemesterResponse;
import generater.openapi.model.SemesterResponseResults;
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
public class SemesterController implements SemesterApi {
    private final SemesterService semesterService;

    @Override
    public ResponseEntity<SemesterResponse> semester(
            @Pattern(regexp = "^(\\d{1,19},)*\\d{1,19}$")
            @ApiParam(value = "")
            @Validated
            @RequestParam(value = "semesters", required = false) String semesters) {
        return ResponseEntity.ok(SemesterResponse.builder()
                .status(HttpStatus.OK.value())
                .results(SemesterResponseResults.builder()
                        .semesters(semesterService.findAll())
                        .build())
                .build());
    }
}
