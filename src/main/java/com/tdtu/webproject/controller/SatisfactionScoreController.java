package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.SatisfactionScoreService;
import generater.openapi.api.SatisfactionScoreApi;
import generater.openapi.model.SatisfactionScoreCreateRequest;
import generater.openapi.model.SatisfactionScoreCreateResponse;
import generater.openapi.model.SatisfactionScoreDeleteRequest;
import generater.openapi.model.SatisfactionScoreDeleteResponse;
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
public class SatisfactionScoreController implements SatisfactionScoreApi {

    private final SatisfactionScoreService satisfactionScoreService;

    @Override
    public ResponseEntity<SatisfactionScoreDeleteResponse> deleteSatisfactionScore(
            @ApiParam(value = "")
            @Valid
            @RequestBody SatisfactionScoreDeleteRequest satisfactionScoreDeleteRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(SatisfactionScoreDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(satisfactionScoreService.deleteSatisfactionScore(satisfactionScoreDeleteRequest))
                .build());
    }

    @Override
    public ResponseEntity<SatisfactionScoreCreateResponse> createSatisfactionScore(
            @ApiParam(value = "")
            @Valid
            @RequestBody SatisfactionScoreCreateRequest satisfactionScoreCreateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(SatisfactionScoreCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(satisfactionScoreService.createSatisfactionScore(satisfactionScoreCreateRequest.getLecturerId(), satisfactionScoreCreateRequest.getSatisfactionScoreCreate(), satisfactionScoreCreateRequest.getCreateBy()))
                .build());
    }
}
