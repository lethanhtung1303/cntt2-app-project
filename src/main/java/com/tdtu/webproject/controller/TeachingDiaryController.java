package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.TeachingDiaryService;
import generater.openapi.api.TeachingDiaryApi;
import generater.openapi.model.TeachingDiaryResponse;
import generater.openapi.model.TeachingDiaryResponseResults;
import generater.openapi.model.TeachingDiaryUpdateRequest;
import generater.openapi.model.TeachingDiaryUpdateResponse;
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
public class TeachingDiaryController implements TeachingDiaryApi {

    private final TeachingDiaryService teachingDiaryService;

    @Override
    public ResponseEntity<TeachingDiaryResponse> teachingDiary(
            @NotNull @Pattern(regexp = "^(\\d{1,19},)*\\d{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "historyId", required = true) String historyId) {
        return ResponseEntity.ok(TeachingDiaryResponse.builder()
                .status(HttpStatus.OK.value())
                .results(TeachingDiaryResponseResults.builder()
                        .teachingDiary(teachingDiaryService.getTeachingDiary(historyId))
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<TeachingDiaryUpdateResponse> updateTeachingDiary(
            @ApiParam(value = "")
            @Valid
            @RequestBody TeachingDiaryUpdateRequest teachingDiaryUpdateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(TeachingDiaryUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(teachingDiaryService.updateTeachingDiary(
                        teachingDiaryUpdateRequest.getHistoryId(),
                        teachingDiaryUpdateRequest.getTeachingDiaryUpdate(),
                        teachingDiaryUpdateRequest.getUpdateBy()))
                .build());
    }
}
