package com.knits.enterprise.controller.common;

import com.knits.enterprise.dto.data.common.BinaryDataDto;
import com.knits.enterprise.service.common.FileUploaderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class FileUploaderController {

    @Autowired
    private FileUploaderService service;

    @PostMapping(value = "/upload/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BinaryDataDto> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(service.upload(file));
    }

}
