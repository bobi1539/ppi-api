package com.grasia.prima.ppi.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "m_student")
public class MStudent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "photo")
    private String photo;

    @Column(name = "major")
    private String major;

    @Column(name = "expected_graduation_year")
    private Year expectedGraduationYear;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private MSystemParameterList gender;

    @ManyToOne
    @JoinColumn(name = "education_id")
    private MSystemParameterList education;

    public static final String FIELD_NAME = "name";
}
