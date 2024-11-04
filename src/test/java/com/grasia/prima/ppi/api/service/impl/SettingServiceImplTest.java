package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.FileUploadRequest;
import com.grasia.prima.ppi.api.dto.request.SettingRequest;
import com.grasia.prima.ppi.api.dto.response.SettingResponse;
import com.grasia.prima.ppi.api.entity.MEvent;
import com.grasia.prima.ppi.api.entity.MPeriod;
import com.grasia.prima.ppi.api.entity.MSetting;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.SettingRepository;
import com.grasia.prima.ppi.api.service.EventService;
import com.grasia.prima.ppi.api.service.FileService;
import com.grasia.prima.ppi.api.service.PeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SettingServiceImplTest extends ServiceTest {

    @InjectMocks
    private SettingServiceImpl settingService;

    @Mock
    private SettingRepository settingRepository;

    @Mock
    private PeriodService periodService;

    @Mock
    private EventService eventService;

    @Mock
    private FileService fileService;

    private final MSetting setting = ObjectDummy.getSetting();
    private final SettingRequest settingRequest = ObjectDummy.getSettingRequest();
    private final MPeriod period = ObjectDummy.getPeriod();
    private final MEvent event = ObjectDummy.getEvent();
    private final String fileName = "file.png";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        when(settingRepository.findById(id)).thenReturn(Optional.of(setting));

        SettingResponse response = settingService.findById(id);
        assertEquals(setting.getId(), response.getId());
        assertEquals(setting.getLogo(), response.getLogo());

        verify(settingRepository).findById(id);
    }

    @Test
    void testCreate_Success() {
        when(periodService.getPeriodById(id)).thenReturn(period);
        when(eventService.getEventById(id)).thenReturn(event);
        when(fileService.saveFileFromBase64(any())).thenReturn(fileName);
        when(settingRepository.save(any())).thenReturn(setting);

        SettingResponse response = settingService.create(settingRequest, header);
        assertEquals(setting.getId(), response.getId());
        assertEquals(setting.getLogo(), response.getLogo());

        verify(periodService).getPeriodById(id);
        verify(eventService).getEventById(id);
        verify(fileService, times(3)).saveFileFromBase64(any());
        verify(settingRepository).save(any());
    }

    @Test
    void testCreate_LogoIsEmpty() {
        settingRequest.setLogo(getFileUploadRequest(null));

        BusinessException e = assertThrows(BusinessException.class, () -> settingService.create(settingRequest, header));
        assertEquals(GlobalMessage.LOGO_REQUIRED.status, e.getStatus());
        assertEquals(GlobalMessage.LOGO_REQUIRED.message, e.getMessage());
    }

    @Test
    void testCreate_BannerIsEmpty() {
        settingRequest.setBanner(getFileUploadRequest(null));

        BusinessException e = assertThrows(BusinessException.class, () -> settingService.create(settingRequest, header));
        assertEquals(GlobalMessage.BANNER_REQUIRED.status, e.getStatus());
        assertEquals(GlobalMessage.BANNER_REQUIRED.message, e.getMessage());
    }

    @Test
    void testCreate_QrCodeIsEmpty() {
        settingRequest.setQrCode(getFileUploadRequest(null));

        BusinessException e = assertThrows(BusinessException.class, () -> settingService.create(settingRequest, header));
        assertEquals(GlobalMessage.QR_CODE_REQUIRED.status, e.getStatus());
        assertEquals(GlobalMessage.QR_CODE_REQUIRED.message, e.getMessage());
    }

    private FileUploadRequest getFileUploadRequest(String fileName) {
        FileUploadRequest fileUploadRequest = ObjectDummy.getFileUploadRequest();
        fileUploadRequest.setFileName(fileName);
        return fileUploadRequest;
    }

    @Test
    void testUpdate_FileSettingNotUpdated() {
        when(settingRepository.findById(id)).thenReturn(Optional.of(setting));
        when(periodService.getPeriodById(id)).thenReturn(period);
        when(eventService.getEventById(id)).thenReturn(event);
        when(settingRepository.save(any())).thenReturn(setting);

        SettingResponse response = settingService.update(id, settingRequest, header);
        assertEquals(setting.getId(), response.getId());
        assertEquals(setting.getLogo(), response.getLogo());

        verify(settingRepository).findById(id);
        verify(periodService).getPeriodById(id);
        verify(eventService).getEventById(id);
        verify(settingRepository).save(any());
    }

    @Test
    void testUpdate_FileSettingUpdated() {
        FileUploadRequest fileUploadRequest = getFileUploadRequest("different-file.png");
        settingRequest.setLogo(fileUploadRequest);
        settingRequest.setBanner(fileUploadRequest);
        settingRequest.setQrCode(fileUploadRequest);

        when(settingRepository.findById(id)).thenReturn(Optional.of(setting));
        when(periodService.getPeriodById(id)).thenReturn(period);
        when(eventService.getEventById(id)).thenReturn(event);
        when(fileService.saveFileFromBase64(any())).thenReturn(fileName);
        when(settingRepository.save(any())).thenReturn(setting);

        SettingResponse response = settingService.update(id, settingRequest, header);
        assertEquals(setting.getId(), response.getId());
        assertEquals(setting.getLogo(), response.getLogo());

        verify(settingRepository).findById(id);
        verify(periodService).getPeriodById(id);
        verify(eventService).getEventById(id);
        verify(fileService, times(3)).saveFileFromBase64(any());
        verify(fileService, times(3)).deleteFile(any());
        verify(settingRepository).save(any());
    }
}