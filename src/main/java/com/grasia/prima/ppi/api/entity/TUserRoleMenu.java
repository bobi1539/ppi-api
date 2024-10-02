package com.grasia.prima.ppi.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "t_user_role_menu")
public class TUserRoleMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private MUserRole userRole;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MMenu menu;

    @CreationTimestamp
    @Column(name = "created_at")
    protected Timestamp createdAt;

    public static final String FIELD_USER_ROLE = "userRole";
}
