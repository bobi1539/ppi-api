package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class StudentRequest {

    @NotNull(message = Constant.STUDENT_NAME_REQUIRED)
    @NotEmpty(message = Constant.STUDENT_NAME_REQUIRED)
    private String name;

    @NotNull(message = Constant.STUDENT_NICKNAME_REQUIRED)
    @NotEmpty(message = Constant.STUDENT_NICKNAME_REQUIRED)
    private String nickname;

    @NotNull(message = Constant.STUDENT_EMAIL_REQUIRED)
    @NotEmpty(message = Constant.STUDENT_EMAIL_REQUIRED)
    private String email;

    @NotNull(message = Constant.STUDENT_MAJOR_REQUIRED)
    @NotEmpty(message = Constant.STUDENT_MAJOR_REQUIRED)
    private String major;

    @NotNull(message = Constant.STUDENT_GRADUATION_REQUIRED)
    private String expectedGraduationYear;

    @NotNull(message = Constant.BIRTH_DATE_REQUIRED)
    private LocalDate birthDate;

    @NotNull(message = Constant.GENDER_REQUIRED)
    private Long genderId;

    @NotNull(message = Constant.STUDENT_EDUCATION_REQUIRED)
    private Long educationId;

    private FileUploadRequest photo;
}
