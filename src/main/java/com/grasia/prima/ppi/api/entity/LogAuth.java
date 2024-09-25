package com.grasia.prima.ppi.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "log_auth")
public class LogAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    @Column(name = "refresh_token_expiry")
    private LocalDate refreshTokenExpiry;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MUser user;

    @CreationTimestamp
    @Column(name = "created_at")
    protected Timestamp createdAt;
}
