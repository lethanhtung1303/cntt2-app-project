package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.LecturerTeachingHistoryService;
import generater.openapi.api.LecturerHistoryApi;
import generater.openapi.model.*;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class LecturerTeachingHistoryController implements LecturerHistoryApi {
    private final LecturerTeachingHistoryService lecturerTeachingHistoryService;

    @Override
    public ResponseEntity<TeachingHistoryResponse> getTeachingHistory(
            @ApiParam(value = "")
            @Valid
            @RequestBody TeachingHistoryRequest teachingHistoryRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(TeachingHistoryResponse.builder()
                .status(HttpStatus.OK.value())
                .results(TeachingHistoryResponseResults.builder()
                        .teachingHistory(lecturerTeachingHistoryService.getTeachingHistory(teachingHistoryRequest.getLecturerId(), teachingHistoryRequest.getSemester()))
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<TeachingHistoryDeleteResponse> deleteTeachingHistory(
            @ApiParam(value = "")
            @Valid
            @RequestBody TeachingHistoryDeleteRequest teachingHistoryDeleteRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(TeachingHistoryDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(lecturerTeachingHistoryService.deleteTeachingHistory(teachingHistoryDeleteRequest))
                .build());
    }

    @Override
    public ResponseEntity<TeachingHistoryResponse> teachingHistoryDetail(
            @NotNull @Pattern(regexp = "^(\\d{1,19},)*\\d{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "historyId", required = true) String historyId) {
        return ResponseEntity.ok(TeachingHistoryResponse.builder()
                .status(HttpStatus.OK.value())
                .results(TeachingHistoryResponseResults.builder()
                        .teachingHistory(lecturerTeachingHistoryService.getTeachingHistoryDetail(historyId))
                        .build())
                .build());
    }
}
