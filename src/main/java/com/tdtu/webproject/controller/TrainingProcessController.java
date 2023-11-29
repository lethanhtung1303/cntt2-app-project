package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.TrainingProcessService;
import generater.openapi.api.TrainingProcessApi;
import generater.openapi.model.*;
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
public class TrainingProcessController implements TrainingProcessApi {
    private final TrainingProcessService trainingProcessService;

    @Override
    public ResponseEntity<TrainingProcessCreateResponse> createTrainingProcess(
            @ApiParam(value = "")
            @Valid
            @RequestBody TrainingProcessCreateRequest trainingProcessCreateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(TrainingProcessCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(trainingProcessService.createTrainingProcess(trainingProcessCreateRequest.getLecturerId(), trainingProcessCreateRequest.getTrainingProcessCreate(), trainingProcessCreateRequest.getCreateBy()))
                .build());
    }

    @Override
    public ResponseEntity<TrainingProcessUpdateResponse> updateTrainingProcess(
            @ApiParam(value = "")
            @Valid
            @RequestBody TrainingProcessUpdateRequest trainingProcessUpdateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(TrainingProcessUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(trainingProcessService.updateTrainingProcess(trainingProcessUpdateRequest.getProcessId(), trainingProcessUpdateRequest.getTrainingProcessUpdate(), trainingProcessUpdateRequest.getUpdateBy()))
                .build());
    }

    @Override
    public ResponseEntity<TrainingProcessDeleteResponse> deleteTrainingProcess(
            @ApiParam(value = "")
            @Valid
            @RequestBody TrainingProcessDeleteRequest trainingProcessDeleteRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(TrainingProcessDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(trainingProcessService.deleteTrainingProcess(trainingProcessDeleteRequest))
                .build());
    }
}
