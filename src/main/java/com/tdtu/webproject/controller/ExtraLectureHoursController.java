package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.ExtraLectureHoursService;
import generater.openapi.api.ExtraHoursApi;
import generater.openapi.model.ExtraLectureHoursResponse;
import generater.openapi.model.ExtraLectureHoursResponseResults;
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
public class ExtraLectureHoursController implements ExtraHoursApi {
    private final ExtraLectureHoursService extraLectureHoursService;

    @Override
    public ResponseEntity<ExtraLectureHoursResponse> extraHoursContractual(
            @NotNull @DecimalMin("190001") @DecimalMax("299903")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "semester", required = true) BigDecimal semester) {
        return ResponseEntity.ok(ExtraLectureHoursResponse.builder()
                .status(HttpStatus.OK.value())
                .results(ExtraLectureHoursResponseResults.builder()
                        .extraLectureHours(extraLectureHoursService.getExtraHoursContractual(semester))
                        .build())
                .build());
    }
}
