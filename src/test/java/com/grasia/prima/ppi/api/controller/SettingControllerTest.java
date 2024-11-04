package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.SettingRequest;
import com.grasia.prima.ppi.api.dto.response.SettingResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.SettingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SettingControllerTest extends ControllerTest {

    @InjectMocks
    private SettingController controller;

    @Mock
    private SettingService service;

    private final SettingRequest settingRequest = ObjectDummy.getSettingRequest();
    private final SettingResponse settingResponse = ObjectDummy.getSettingResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(settingResponse);

        SettingResponse response = controller.findById(id);
        assertEquals(settingResponse.getId(), response.getId());
        assertEquals(settingResponse.getLogo(), response.getLogo());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(settingResponse);

        SettingResponse response = controller.create(settingRequest, header);
        assertEquals(settingResponse.getId(), response.getId());
        assertEquals(settingResponse.getLogo(), response.getLogo());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(settingResponse);

        SettingResponse response = controller.update(id, settingRequest, header);
        assertEquals(settingResponse.getId(), response.getId());
        assertEquals(settingResponse.getLogo(), response.getLogo());

        verify(service).update(any(), any(), any());
    }
}