package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.SubjectService;
import generater.openapi.api.SubjectApi;
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

    @Override
    public ResponseEntity<SubjectGroupResponse> subjectGroup(
            @ApiParam(value = "")
            @Validated
            @RequestParam(value = "groupIds", required = false) String groupIds) {
        return ResponseEntity.ok(SubjectGroupResponse.builder()
                .status(HttpStatus.OK.value())
                .results(SubjectGroupResponseResults.builder()
                        .resultsTotalCount(subjectService.countSubjectGroup(groupIds))
                        .subjectGroups(subjectService.findSubjectGroup(groupIds))
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<SubjectCreateResponse> createSubject(
            @ApiParam(value = "")
            @Valid
            @RequestBody SubjectCreateRequest subjectCreateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(SubjectCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(subjectService.createSubject(subjectCreateRequest.getSubjectCreate(), subjectCreateRequest.getCreateBy()))
                .build());
    }

    @Override
    public ResponseEntity<SubjectDeleteResponse> deleteSubject(
            @ApiParam(value = "")
            @Valid
            @RequestBody SubjectDeleteRequest subjectDeleteRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(SubjectDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(subjectService.deleteSubject(subjectDeleteRequest))
                .build());
    }

    @Override
    public ResponseEntity<SubjectUpdateResponse> updateSubject(
            @ApiParam(value = "")
            @Valid
            @RequestBody SubjectUpdateRequest subjectUpdateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(SubjectUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(subjectService.updateSubject(subjectUpdateRequest.getSubjectId(),
                        subjectUpdateRequest.getSubjectUpdate(), subjectUpdateRequest.getUpdateBy()))
                .build());
    }
}
