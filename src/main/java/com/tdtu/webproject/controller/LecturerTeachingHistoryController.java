package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.LecturerTeachingHistoryService;
import generater.openapi.api.LecturerHistoryApi;
import generater.openapi.model.TeachingHistoryRequest;
import generater.openapi.model.TeachingHistoryResponse;
import generater.openapi.model.TeachingHistoryResponseResults;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
