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
@Table(name = "m_sub_menu")
public class MSubMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "route")
    private String route;

    @Column(name = "sequence", unique = true)
    private Integer sequence;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MMenu menu;

    public static final String FIELD_NAME = "name";
    public static final String FIELD_SEQUENCE = "sequence";
    public static final String FIELD_MENU = "menu";
}
