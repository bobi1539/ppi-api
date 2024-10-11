package com.grasia.prima.ppi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class StaffResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private String position;
    private Boolean isHead;
    private String photo;
    private String quote;
    private String funFact;
    private String description;
    private String jobDescription;
    private DivisionResponse division;
}
