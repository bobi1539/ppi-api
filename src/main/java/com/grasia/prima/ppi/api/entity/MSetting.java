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
@Table(name = "m_setting")
public class MSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "logo")
    private String logo;

    @Column(name = "banner")
    private String banner;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "tiktok")
    private String tiktok;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "youtube")
    private String youtube;

    @Column(name = "support_account_name")
    private String supportAccountName;

    @Column(name = "support_account_number")
    private String supportAccountNumber;

    @Column(name = "support_short_code")
    private String supportShortCode;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone_number")
    private String contactPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "period_id")
    private MPeriod periodActive;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private MEvent eventGallery;
}
