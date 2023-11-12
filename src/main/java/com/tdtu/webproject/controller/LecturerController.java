package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.LecturerService;
import generater.openapi.api.LecturerApi;
import generater.openapi.model.LecturerResponse;
import generater.openapi.model.LecturerResponseResults;
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
public class LecturerController implements LecturerApi {

    private final LecturerService lecturerService;

    @Override
    public ResponseEntity<LecturerResponse> lecturer(
            @Pattern(regexp = "^(\\d{1,19},)*\\d{1,19}$")
            @ApiParam(value = "")
            @Validated
            @RequestParam(value = "lecturerIds", required = false)
            String lecturerIds) {
        return ResponseEntity.ok(LecturerResponse.builder()
                .status(HttpStatus.OK.value())
                .results(LecturerResponseResults.builder()
                        .resultsTotalCount(lecturerService.count(lecturerIds))
                        .lecturers(lecturerService.find(lecturerIds))
                        .build())
                .build());
    }
}
