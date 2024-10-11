package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.config.AppConfig;
import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.response.FileResponse;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.StringHelper;
import com.grasia.prima.ppi.api.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final AppConfig appConfig;
    private String directoryName;
    private String fileName;
    private String fileExtension;
    private byte[] fileBytes;
    private MediaType mediaType;
    private static final Map<String, MediaType> ALLOW_EXTENSIONS;
    private static final long MAX_FILE_SIZE = 10L * 1_024L * 1_024L;

    static {
        ALLOW_EXTENSIONS = Map.of(
                "pdf", MediaType.APPLICATION_PDF,
                "doc", MediaType.parseMediaType("application/msword"),
                "docm", MediaType.parseMediaType("application/vnd.ms-word.document.macroEnabled.12"),
                "docx", MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
                "jpg", MediaType.IMAGE_JPEG,
                "jpeg", MediaType.IMAGE_JPEG,
                "png", MediaType.IMAGE_PNG,
                "xlsx", MediaType.APPLICATION_OCTET_STREAM,
                "xls", MediaType.APPLICATION_OCTET_STREAM
        );
    }

    public FileServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public String saveFileFromBase64(Base64ToFileDto base64ToFileDto) {
        this.directoryName = base64ToFileDto.getDirectoryName();
        this.fileName = base64ToFileDto.getFileName();

        setFileExtension();
        validateFileExtension();
        setFileBytesFromBase64(base64ToFileDto.getBase64String());

        return writeFile();
    }

    @Override
    public String saveFileFromBytes(FileRequest fileRequest) {
        this.directoryName = fileRequest.getDirectoryName();
        this.fileName = fileRequest.getFileName();
        this.fileBytes = fileRequest.getFileBytes();

        setFileExtension();
        validateFileExtension();

        return writeFile();
    }

    @Override
    public FileResponse downloadFile(FileRequest fileRequest) {
        this.directoryName = fileRequest.getDirectoryName();
        this.fileName = fileRequest.getFileName();

        setFileExtension();
        validateFileExtension();
        setFileBytesFromFileName();

        return FileResponse.builder()
                .fileBytes(fileBytes)
                .fileName(fileName)
                .mediaType(mediaType)
                .build();
    }

    @Override
    public void deleteFile(FileRequest fileRequest) {
        String fullPath = appConfig.getPathFile() + fileRequest.getDirectoryName() + fileRequest.getFileName();
        Path path = Paths.get(fullPath);
        if (!Files.exists(path)) {
            throw new BusinessException(GlobalMessage.FILE_DOES_NOT_EXIST);
        }

        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error(Constant.ERROR, e.getMessage());
            throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
        }
    }

    private void setFileExtension() {
        String[] fileNames = fileName.split(Constant.REGEX_DOT);
        this.fileExtension = fileNames[fileNames.length - 1];
    }

    private void validateFileExtension() {
        MediaType mediaTypeExt = ALLOW_EXTENSIONS.get(fileExtension);
        if (Objects.isNull(mediaTypeExt)) {
            throw new BusinessException(GlobalMessage.FILE_NOT_ALLOWED);
        }
        this.mediaType = mediaTypeExt;
    }

    private void setFileBytesFromBase64(String base64String) {
        byte[] bytes = Base64.getDecoder().decode(base64String);
        if (bytes.length > MAX_FILE_SIZE) {
            throw new BusinessException(GlobalMessage.MAX_FILE_SIZE_IS_10_MB);
        }
        this.fileBytes = bytes;
    }

    private String writeFile() {
        String newFileName = getNewFileName();
        String directoryPath = appConfig.getPathFile() + directoryName;
        String fullPath = directoryPath + newFileName;
        log.info("file path : {}", fullPath);

        try {
            createDirectory(directoryPath);
            Files.write(Paths.get(fullPath), fileBytes);
            return newFileName;
        } catch (IOException e) {
            log.error(Constant.ERROR, e);
            throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
        }
    }

    private void createDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    private String getNewFileName() {
        String newFileName = String.format(
                "PPI_Warwick_%s_%s_%s", StringHelper.random(), System.currentTimeMillis(), fileName
        );
        return newFileName.replace(" ", "_");
    }

    private void setFileBytesFromFileName() {
        try {
            String fullPath = appConfig.getPathFile() + directoryName + fileName;
            log.info("file path : {}", fullPath);

            this.fileBytes = Files.readAllBytes(Paths.get(fullPath));
        } catch (IOException e) {
            throw new BusinessException(GlobalMessage.FILE_DOES_NOT_EXIST);
        }
    }
}
