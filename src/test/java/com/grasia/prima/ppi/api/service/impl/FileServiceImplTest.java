package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.config.AppConfig;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.response.FileResponse;
import com.grasia.prima.ppi.api.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private AppConfig appConfig;

    private final String pathFileTest = "target/file-test/";
    private final String directoryName = "test";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createDirectoryTest();
        when(appConfig.getPathFile()).thenReturn(pathFileTest);
    }

    private void createDirectoryTest() {
        File directoryTest = new File(pathFileTest);
        if (!directoryTest.exists() && !directoryTest.mkdir()) {
            throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
        }
    }

    private FileRequest getFileRequest() {
        return FileRequest.builder()
                .directoryName(directoryName)
                .fileName("test.png")
                .fileBytes("aGVsbG8=".getBytes())
                .build();
    }

    private Base64ToFileDto getBase64ToFileDto(String fileName) {
        return Base64ToFileDto.builder()
                .directoryName(directoryName)
                .fileName(fileName)
                .base64String("aGVsbG8=")
                .build();
    }

    @Test
    void testSaveFileFromBase64_Success() {
        Base64ToFileDto base64ToFileDto = getBase64ToFileDto("test.png");
        String fileName = fileService.saveFileFromBase64(base64ToFileDto);
        assertNotNull(fileName);
    }

    @Test
    void testSaveFileFromBase64_FileNotAllowed() {
        Base64ToFileDto base64ToFileDto = getBase64ToFileDto("test.execution");
        BusinessException e = assertThrows(BusinessException.class, () -> fileService.saveFileFromBase64(base64ToFileDto));
        assertEquals(GlobalMessage.FILE_NOT_ALLOWED.status, e.getStatus());
        assertEquals(GlobalMessage.FILE_NOT_ALLOWED.message, e.getMessage());
    }

    @Test
    void testSaveFileFromBase64_FileSizeTooLarge() {
        try (MockedStatic<Base64> mockBase64 = mockStatic(Base64.class)) {
            Base64.Decoder decoder = mock(Base64.Decoder.class);
            when(decoder.decode(anyString())).thenReturn(new byte[20_000_000]);
            mockBase64.when(Base64::getDecoder).thenReturn(decoder);

            Base64ToFileDto base64ToFileDto = getBase64ToFileDto("test.png");
            BusinessException e = assertThrows(BusinessException.class, () -> fileService.saveFileFromBase64(base64ToFileDto));
            assertEquals(GlobalMessage.MAX_FILE_SIZE_IS_10_MB.status, e.getStatus());
            assertEquals(GlobalMessage.MAX_FILE_SIZE_IS_10_MB.message, e.getMessage());
        }
    }

    @Test
    void testSaveFileFromBase64_WriteFileFailed() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.write(any(Path.class), any(byte[].class))).thenThrow(new IOException("Failed"));

            Base64ToFileDto base64ToFileDto = getBase64ToFileDto("test.png");
            BusinessException e = assertThrows(BusinessException.class, () -> fileService.saveFileFromBase64(base64ToFileDto));
            assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.status, e.getStatus());
            assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getMessage());
        }
    }

    @Test
    void testSaveFileFromBytes_Success() {
        String fileName = fileService.saveFileFromBytes(getFileRequest());
        assertNotNull(fileName);
    }

    @Test
    void testDownloadFile_Success() {
        String fileName = fileService.saveFileFromBytes(getFileRequest());
        FileRequest fileRequest = FileRequest.builder()
                .directoryName(directoryName)
                .fileName(fileName)
                .build();
        FileResponse fileResponse = fileService.downloadFile(fileRequest);
        assertNotNull(fileResponse);
        assertEquals(MediaType.IMAGE_PNG, fileResponse.getMediaType());
        assertEquals(fileName, fileResponse.getFileName());
    }

    @Test
    void testDownloadFile_FileDoesntExist() {
        BusinessException e = assertThrows(BusinessException.class, () -> fileService.downloadFile(getFileRequest()));
        assertEquals(GlobalMessage.FILE_DOES_NOT_EXIST.status, e.getStatus());
        assertEquals(GlobalMessage.FILE_DOES_NOT_EXIST.message, e.getMessage());
    }

    @Test
    void testDeleteFile_Success() {
        String fileName = fileService.saveFileFromBytes(getFileRequest());
        FileRequest fileRequest = FileRequest.builder()
                .directoryName(directoryName)
                .fileName(fileName)
                .build();
        assertDoesNotThrow(() -> fileService.deleteFile(fileRequest));
    }

    @Test
    void testDeleteFile_Failed() {
        BusinessException e = assertThrows(BusinessException.class, () -> fileService.deleteFile(getFileRequest()));
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.status, e.getStatus());
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getMessage());
    }
}