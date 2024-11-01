package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MDivision;
import com.grasia.prima.ppi.api.entity.MStaff;
import lombok.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StaffDivisionResponse {
    private Long id;
    private String name;
    private List<StaffResponse> heads;
    private List<StaffResponse> teams;

    public static StaffDivisionResponse toResponse(MDivision division, List<MStaff> heads, List<MStaff> teams) {
        if (Objects.isNull(division)) {
            return null;
        }
        return builder()
                .id(division.getId())
                .name(division.getName())
                .heads(StaffResponse.toResponses(heads))
                .teams(StaffResponse.toResponses(teams))
                .build();
    }
}
