package com.grasia.prima.ppi.api.dto.search;

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
public class StaffSearchDto extends SearchDto {
    private Long periodId;
    private Long divisionId;
    private Boolean isHead;
}
