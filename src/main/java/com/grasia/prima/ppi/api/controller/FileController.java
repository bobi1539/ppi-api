package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.response.FileResponse;
import com.grasia.prima.ppi.api.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoint.FILE)
@AllArgsConstructor
public class FileController extends BaseController {

    private final FileService fileService;

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam String directoryName, @RequestParam String fileName) {
        FileRequest fileRequest = FileRequest.builder()
                .directoryName(directoryName)
                .fileName(fileName)
                .build();
        FileResponse response = fileService.downloadFile(fileRequest);
        return buildResourceResponse(response);
    }
}
