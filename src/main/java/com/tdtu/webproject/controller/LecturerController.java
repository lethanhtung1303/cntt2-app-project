package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.LecturerService;
import generater.openapi.api.LecturerApi;
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

    @Override
    public ResponseEntity<LecturerDeleteResponse> deleteLecturer(
            @ApiParam(value = "")
            @Valid
            @RequestBody LecturerDeleteRequest lecturerDeleteRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(LecturerDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(lecturerService.deleteLecturer(lecturerDeleteRequest))
                .build());
    }

    @Override
    public ResponseEntity<LecturerUpdateResponse> updateLecturer(
            @ApiParam(value = "")
            @Valid
            @RequestBody LecturerUpdateRequest lecturerUpdateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(LecturerUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(lecturerService.updateLecturer(lecturerUpdateRequest.getLecturerId(),
                        lecturerUpdateRequest.getLecturerUpdate(), lecturerUpdateRequest.getUpdateBy()))
                .build());
    }

    @Override
    public ResponseEntity<LecturerCreateResponse> createLecturer(
            @ApiParam(value = "")
            @Valid
            @RequestBody LecturerCreateRequest lecturerCreateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(LecturerCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(lecturerService.createLecturer(lecturerCreateRequest.getLecturerCreate(),
                        lecturerCreateRequest.getCreateBy()))
                .build());
    }
}
