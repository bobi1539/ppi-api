package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MStaff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public static StaffResponse toResponse(MStaff staff) {
        if (Objects.isNull(staff)) {
            return null;
        }
        StaffResponse response = builder()
                .id(staff.getId())
                .name(staff.getName())
                .position(staff.getPosition())
                .isHead(staff.getIsHead())
                .photo(staff.getPhoto())
                .quote(staff.getQuote())
                .funFact(staff.getFunFact())
                .description(staff.getDescription())
                .jobDescription(staff.getJobDescription())
                .division(DivisionResponse.toResponse(staff.getDivision()))
                .build();
        BaseEntityResponse.setBaseEntity(response, staff);
        return response;
    }

    public static List<StaffResponse> toResponses(List<MStaff> staffs) {
        if (Objects.isNull(staffs)) {
            return Collections.emptyList();
        }
        return staffs.stream().map(StaffResponse::toResponse).toList();
    }
}
