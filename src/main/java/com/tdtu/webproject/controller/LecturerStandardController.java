package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.LecturerStandardService;
import generater.openapi.api.LecturerStandardApi;
import generater.openapi.model.LecturerStandardsResponse;
import generater.openapi.model.LecturerStandardsResponseResults;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class LecturerStandardController implements LecturerStandardApi {
    private final LecturerStandardService lecturerStandardService;

    @Override
    public ResponseEntity<LecturerStandardsResponse> lecturerStandards(
            @NotNull @DecimalMin("190001") @DecimalMax("299903")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "semester", required = true) BigDecimal semester) {
        return ResponseEntity.ok(LecturerStandardsResponse.builder()
                .status(HttpStatus.OK.value())
                .results(LecturerStandardsResponseResults.builder()
                        .lecturerStandards(lecturerStandardService.getLecturerStandards(semester))
                        .build())
                .build());
    }
}
