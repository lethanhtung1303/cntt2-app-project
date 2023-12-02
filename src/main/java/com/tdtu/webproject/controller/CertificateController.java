package com.tdtu.webproject.controller;

import com.tdtu.webproject.service.CertificateService;
import generater.openapi.api.CertificateApi;
import generater.openapi.model.CertificateCreateRequest;
import generater.openapi.model.CertificateCreateResponse;
import generater.openapi.model.CertificateDeleteRequest;
import generater.openapi.model.CertificateDeleteResponse;
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
public class CertificateController implements CertificateApi {

    private final CertificateService certificateService;

    @Override
    public ResponseEntity<CertificateDeleteResponse> deleteCertificate(
            @ApiParam(value = "")
            @Valid
            @RequestBody CertificateDeleteRequest certificateDeleteRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(CertificateDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(certificateService.deleteCertificate(certificateDeleteRequest))
                .build());
    }

    @Override
    public ResponseEntity<CertificateCreateResponse> createCertificate(
            @ApiParam(value = "")
            @Valid
            @RequestBody CertificateCreateRequest certificateCreateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(CertificateCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(certificateService.createCertificate(certificateCreateRequest.getLecturerId(),
                        certificateCreateRequest.getCertificateCreate(), certificateCreateRequest.getCreateBy()))
                .build());
    }
}
