package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SettingRequest;
import com.grasia.prima.ppi.api.dto.response.SettingResponse;
import com.grasia.prima.ppi.api.entity.MSetting;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.StringHelper;
import com.grasia.prima.ppi.api.repository.SettingRepository;
import com.grasia.prima.ppi.api.service.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SettingServiceImpl extends AbstractCrudService implements SettingService {

    private final SettingRepository settingRepository;
    private final PeriodService periodService;
    private final EventService eventService;
    private final FileService fileService;
    private static final String DIRECTORY_NAME = "setting";

    @Override
    public SettingResponse findById(Long id) {
        return toResponse(getById(id));
    }

    @Transactional
    @Override
    public SettingResponse create(SettingRequest request, HeaderRequest header) {
        validateRequest(request);
        MSetting setting = MSetting.builder().build();
        setCreatedBy(setting, header);
        setUpdatedBy(setting, header);
        setSetting(setting, request);
        saveFileSetting(setting, request);
        return toResponse(settingRepository.save(setting));
    }

    @Transactional
    @Override
    public SettingResponse update(Long id, SettingRequest request, HeaderRequest header) {
        validateRequest(request);
        MSetting setting = getById(id);
        setUpdatedBy(setting, header);
        setSetting(setting, request);
        updateFileSetting(setting, request);
        return toResponse(settingRepository.save(setting));
    }

    @Override
    public MSetting getById(Long id) {
        return settingRepository.findById(id).orElseThrow(getNotFoundException());
    }

    private void validateRequest(SettingRequest request) {
        if (StringHelper.isEmpty(request.getLogo().getFileName())) {
            throw new BusinessException(GlobalMessage.LOGO_REQUIRED);
        }
        if (StringHelper.isEmpty(request.getBanner().getFileName())) {
            throw new BusinessException(GlobalMessage.BANNER_REQUIRED);
        }
        if (StringHelper.isEmpty(request.getQrCode().getFileName())) {
            throw new BusinessException(GlobalMessage.QR_CODE_REQUIRED);
        }
    }

    private void setSetting(MSetting setting, SettingRequest request) {
        setting.setInstagram(request.getInstagram());
        setting.setTiktok(request.getTiktok());
        setting.setLinkedin(request.getLinkedin());
        setting.setYoutube(request.getYoutube());
        setting.setSupportAccountName(request.getSupportAccountName());
        setting.setSupportAccountNumber(request.getSupportAccountNumber());
        setting.setSupportShortCode(request.getSupportShortCode());
        setting.setContactEmail(request.getContactEmail());
        setting.setContactPhoneNumber(request.getContactPhoneNumber());
        setting.setPeriodActive(periodService.getPeriodById(request.getPeriodActiveId()));
        setting.setEventGallery(eventService.getEventById(request.getEventGalleryId()));
    }

    private void saveFileSetting(MSetting setting, SettingRequest request) {
        setting.setLogo(saveFile(request.getLogo().getFileName(), request.getLogo().getFileBase64()));
        setting.setBanner(saveFile(request.getBanner().getFileName(), request.getBanner().getFileBase64()));
        setting.setQrCode(saveFile(request.getQrCode().getFileName(), request.getQrCode().getFileBase64()));
    }

    private void updateFileSetting(MSetting setting, SettingRequest request) {
        updateLogo(setting, request);
        updateBanner(setting, request);
        updateQrCode(setting, request);
    }

    private void updateLogo(MSetting setting, SettingRequest request) {
        if (!setting.getLogo().equals(request.getLogo().getFileName())) {
            deleteFile(setting.getLogo());
            setting.setLogo(saveFile(request.getLogo().getFileName(), request.getLogo().getFileBase64()));
        }
    }

    private void updateBanner(MSetting setting, SettingRequest request) {
        if (!setting.getBanner().equals(request.getBanner().getFileName())) {
            deleteFile(setting.getBanner());
            setting.setBanner(saveFile(request.getBanner().getFileName(), request.getBanner().getFileBase64()));
        }
    }

    private void updateQrCode(MSetting setting, SettingRequest request) {
        if (!setting.getQrCode().equals(request.getQrCode().getFileName())) {
            deleteFile(setting.getQrCode());
            setting.setQrCode(saveFile(request.getQrCode().getFileName(), request.getQrCode().getFileBase64()));
        }
    }

    private String saveFile(String fileName, String base64String) {
        Base64ToFileDto dto = Base64ToFileDto.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .base64String(base64String)
                .build();
        return fileService.saveFileFromBase64(dto);
    }

    private void deleteFile(String fileName) {
        FileRequest fileRequest = FileRequest.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .build();
        fileService.deleteFile(fileRequest);
    }

    private SettingResponse toResponse(MSetting setting) {
        return SettingResponse.toResponse(setting);
    }
}
