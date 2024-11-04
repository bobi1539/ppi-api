package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MStudent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class StudentResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private String email;
    private String photo;
    private String major;
    private String education;
    private String graduation;
    private LocalDate birthDate;
    private SystemParameterListResponse gender;

    public static StudentResponse toResponse(MStudent student) {
        if (Objects.isNull(student)) {
            return null;
        }
        StudentResponse response = builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .photo(student.getPhoto())
                .major(student.getMajor())
                .education(student.getEducation())
                .graduation(student.getGraduation())
                .birthDate(student.getBirthDate())
                .gender(SystemParameterListResponse.toResponse(student.getGender()))
                .build();
        BaseEntityResponse.setBaseEntity(response, student);
        return response;
    }
}
