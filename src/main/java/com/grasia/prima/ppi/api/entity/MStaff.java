package com.grasia.prima.ppi.api.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "m_staff")
public class MStaff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "is_head")
    private Boolean isHead;

    @Column(name = "photo")
    private String photo;

    @Column(name = "quote")
    private String quote;

    @Column(name = "fun_fact")
    private String funFact;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "job_description", columnDefinition = "TEXT")
    private String jobDescription;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private MDivision division;

    public static final String FIELD_NAME = "name";
    public static final String FIELD_DIVISION = "division";
}
