package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.response.FileResponse;

public interface FileService {

    String saveFileFromBase64(Base64ToFileDto base64ToFileDto);

    String saveFileFromBytes(FileRequest fileRequest);

    FileResponse downloadFile(FileRequest fileRequest);

    void deleteFile(FileRequest fileRequest);
}
