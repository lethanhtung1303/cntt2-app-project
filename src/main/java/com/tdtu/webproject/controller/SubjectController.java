package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.SubjectService;
import generater.openapi.api.SubjectApi;
import generater.openapi.model.SubjectResponse;
import generater.openapi.model.SubjectResponseResults;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class SubjectController implements SubjectApi {
    private final SubjectService subjectService;

    @Override
    public ResponseEntity<SubjectResponse> subject(
            @ApiParam(value = "")
            @Validated
            @RequestParam(value = "subjectIds", required = false) String subjectIds) {
        return ResponseEntity.ok(SubjectResponse.builder()
                .status(HttpStatus.OK.value())
                .results(SubjectResponseResults.builder()
                        .resultsTotalCount(subjectService.count(subjectIds))
                        .subjects(subjectService.find(subjectIds))
                        .build())
                .build());
    }
}
