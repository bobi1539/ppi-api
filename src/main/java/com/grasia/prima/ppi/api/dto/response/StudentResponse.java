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
    private String nickname;
    private String email;
    private String photo;
    private String major;
    private String expectedGraduationYear;
    private LocalDate birthDate;
    private SystemParameterListResponse gender;
    private SystemParameterListResponse education;

    public static StudentResponse toResponse(MStudent student) {
        if (Objects.isNull(student)) {
            return null;
        }
        StudentResponse response = builder()
                .id(student.getId())
                .name(student.getName())
                .nickname(student.getNickname())
                .email(student.getEmail())
                .photo(student.getPhoto())
                .major(student.getMajor())
                .expectedGraduationYear(student.getExpectedGraduationYear().toString())
                .birthDate(student.getBirthDate())
                .gender(SystemParameterListResponse.toResponse(student.getGender()))
                .education(SystemParameterListResponse.toResponse(student.getEducation()))
                .build();
        BaseEntityResponse.setBaseEntity(response, student);
        return response;
    }
}
