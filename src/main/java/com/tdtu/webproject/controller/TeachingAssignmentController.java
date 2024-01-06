package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.TeachingAssignmentService;
import generater.openapi.api.AssignmentApi;
import generater.openapi.model.AssignmentRequest;
import generater.openapi.model.AssignmentResponse;
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
public class TeachingAssignmentController implements AssignmentApi {
    private final TeachingAssignmentService teachingAssignmentService;

    @Override
    public ResponseEntity<AssignmentResponse> teachingAssignment(
            @ApiParam(value = "")
            @Valid
            @RequestBody AssignmentRequest assignmentRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(AssignmentResponse.builder()
                .status(HttpStatus.OK.value())
                .message(teachingAssignmentService.createAssignment(assignmentRequest.getAssignment(), assignmentRequest.getCreateBy()))
                .build());
    }
}
