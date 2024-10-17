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
public class SearchDto {
    private String search;
    private Boolean isDeleted;
    private int page = 0;
    private int size = 10;
}
