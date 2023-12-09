package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.SchoolYearService;
import generater.openapi.api.SchoolYearApi;
import generater.openapi.model.SchoolYearResponse;
import generater.openapi.model.SchoolYearResponseResults;
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
public class SchoolYearController implements SchoolYearApi {
    private final SchoolYearService schoolYearService;

    @Override
    public ResponseEntity<SchoolYearResponse> schoolYear(
            @Pattern(regexp = "^(\\d{1,19},)*\\d{1,19}$")
            @ApiParam(value = "")
            @Validated
            @RequestParam(value = "semesters", required = false) String semesters) {
        return ResponseEntity.ok(SchoolYearResponse.builder()
                .status(HttpStatus.OK.value())
                .results(SchoolYearResponseResults.builder()
                        .schoolYear(schoolYearService.findAll())
                        .build())
                .build());
    }
}
