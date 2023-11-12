package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.webproject.model.condition.CertificateCondition;
import com.tdtu.webproject.repository.CertificateRepository;
import com.tdtu.webproject.utils.NumberUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;

    public List<TdtChungChi> findByLecturerId(String lecturerIds) {
        CertificateCondition condition = this.buildCertificateCondition(lecturerIds);
        return certificateRepository.findCertificate(condition);
    }

    private CertificateCondition buildCertificateCondition(String lecturerIds) {
        return CertificateCondition.builder()
                .lecturerIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .map(contact -> NumberUtil.toBigDeimal(contact).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                // Add conditions if needed
                .build();
    }
}
