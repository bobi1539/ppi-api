package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.response.FileResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FileControllerTest {

    @InjectMocks
    private FileController fileController;

    @Mock
    private FileService fileService;

    private final FileRequest fileRequest = ObjectDummy.getFileRequest();
    private final FileResponse fileResponse = ObjectDummy.getFileResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDownload() {
        when(fileService.downloadFile(any())).thenReturn(fileResponse);

        ResponseEntity<InputStreamResource> response = fileController.download(fileRequest.getDirectoryName(), fileRequest.getFileName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}