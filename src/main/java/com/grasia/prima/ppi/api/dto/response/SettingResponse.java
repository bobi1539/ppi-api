package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MSetting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class SettingResponse extends BaseEntityResponse {
    private Long id;
    private String logo;
    private String banner;
    private String qrCode;
    private String instagram;
    private String tiktok;
    private String linkedin;
    private String youtube;
    private String supportAccountName;
    private String supportAccountNumber;
    private String supportShortCode;
    private String contactEmail;
    private String contactPhoneNumber;
    private Long periodActiveId;
    private Long eventGalleryId;

    public static SettingResponse toResponse(MSetting setting) {
        if (Objects.isNull(setting)) {
            return null;
        }
        SettingResponse response = builder()
                .id(setting.getId())
                .logo(setting.getLogo())
                .banner(setting.getBanner())
                .qrCode(setting.getQrCode())
                .instagram(setting.getInstagram())
                .tiktok(setting.getTiktok())
                .linkedin(setting.getLinkedin())
                .youtube(setting.getYoutube())
                .supportAccountName(setting.getSupportAccountName())
                .supportAccountNumber(setting.getSupportAccountNumber())
                .supportShortCode(setting.getSupportShortCode())
                .contactEmail(setting.getContactEmail())
                .contactPhoneNumber(setting.getContactPhoneNumber())
                .periodActiveId(setting.getPeriodActive().getId())
                .eventGalleryId(setting.getEventGallery().getId())
                .build();
        BaseEntityResponse.setBaseEntity(response, setting);
        return response;
    }
}
