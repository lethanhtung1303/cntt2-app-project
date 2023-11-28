package com.tdtu.webproject.model.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LecturerRequest {
    private String lecturerIds;
    private String deleteBy;
    private String emailTdtu;
}
