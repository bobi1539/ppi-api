package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SettingRequest {

    @NotNull(message = Constant.LOGO_REQUIRED)
    private FileUploadRequest logo;

    @NotNull(message = Constant.BANNER_REQUIRED)
    private FileUploadRequest banner;

    @NotNull(message = Constant.QR_CODE_REQUIRED)
    private FileUploadRequest qrCode;

    @NotNull(message = Constant.INSTAGRAM_REQUIRED)
    @NotEmpty(message = Constant.INSTAGRAM_REQUIRED)
    private String instagram;

    @NotNull(message = Constant.TIKTOK_REQUIRED)
    @NotEmpty(message = Constant.TIKTOK_REQUIRED)
    private String tiktok;

    @NotNull(message = Constant.LINKEDIN_CODE_REQUIRED)
    @NotEmpty(message = Constant.LINKEDIN_CODE_REQUIRED)
    private String linkedin;

    @NotNull(message = Constant.YOUTUBE_CODE_REQUIRED)
    @NotEmpty(message = Constant.YOUTUBE_CODE_REQUIRED)
    private String youtube;

    @NotNull(message = Constant.ACCOUNT_NAME_REQUIRED)
    @NotEmpty(message = Constant.ACCOUNT_NAME_REQUIRED)
    private String supportAccountName;

    @NotNull(message = Constant.ACCOUNT_NUMBER_REQUIRED)
    @NotEmpty(message = Constant.ACCOUNT_NUMBER_REQUIRED)
    private String supportAccountNumber;

    @NotNull(message = Constant.SHORT_CODE_REQUIRED)
    @NotEmpty(message = Constant.SHORT_CODE_REQUIRED)
    private String supportShortCode;

    @NotNull(message = Constant.CONTACT_EMAIL_REQUIRED)
    @NotEmpty(message = Constant.CONTACT_EMAIL_REQUIRED)
    private String contactEmail;

    @NotNull(message = Constant.CONTACT_PHONE_NUMBER_REQUIRED)
    @NotEmpty(message = Constant.CONTACT_PHONE_NUMBER_REQUIRED)
    private String contactPhoneNumber;

    @NotNull(message = Constant.PERIOD_ID_REQUIRED)
    private Long periodActiveId;

    @NotNull(message = Constant.EVENT_ID_REQUIRED)
    private Long eventGalleryId;
}
