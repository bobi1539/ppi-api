package com.grasia.prima.ppi.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "m_menu")
public class MMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "route")
    private String route;

    @Column(name = "icon")
    private String icon;

    @Column(name = "sequence", unique = true)
    private Integer sequence;

    @OneToMany(mappedBy = MSubMenu.FIELD_MENU)
    @OrderBy(MSubMenu.FIELD_SEQUENCE)
    private List<MSubMenu> subMenus;

    public static final String FIELD_NAME = "name";
    public static final String FIELD_SEQUENCE = "sequence";
}
