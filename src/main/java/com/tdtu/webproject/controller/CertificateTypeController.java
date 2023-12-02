package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.CertificateTypeService;
import generater.openapi.api.CertificateTypeApi;
import generater.openapi.model.CertificateTypeResponse;
import generater.openapi.model.CertificateTypeResponseResults;
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
public class CertificateTypeController implements CertificateTypeApi {
    private final CertificateTypeService certificateTypeService;

    @Override
    public ResponseEntity<CertificateTypeResponse> certificateType(
            @Pattern(regexp = "^(\\d{1,19},)*\\d{1,19}$")
            @ApiParam(value = "")
            @Validated
            @RequestParam(value = "typeIds", required = false) String typeIds) {
        return ResponseEntity.ok(CertificateTypeResponse.builder()
                .status(HttpStatus.OK.value())
                .results(CertificateTypeResponseResults.builder()
                        .certificateType(certificateTypeService.findAll())
                        .build())
                .build());
    }
}
