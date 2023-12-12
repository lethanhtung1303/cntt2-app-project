package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.NormsLectureHoursService;
import generater.openapi.api.LectureHoursApi;
import generater.openapi.model.NormsLectureHoursResponse;
import generater.openapi.model.NormsLectureHoursResponseResults;
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
public class LectureHoursController implements LectureHoursApi {
    private final NormsLectureHoursService normsLectureHoursService;

    @Override
    public ResponseEntity<NormsLectureHoursResponse> normsLectureHours(
            @NotNull @DecimalMin("190001") @DecimalMax("299903")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "semester", required = true) BigDecimal semester) {
        return ResponseEntity.ok(NormsLectureHoursResponse.builder()
                .status(HttpStatus.OK.value())
                .results(NormsLectureHoursResponseResults.builder()
                        .normsLectureHours(normsLectureHoursService.getNormsLectureHours(semester))
                        .build())
                .build());
    }
}
