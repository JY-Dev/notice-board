package com.jydev.noticeboard.file;

import com.jydev.noticeboard.file.model.FileType;
import com.jydev.noticeboard.file.model.StoreFile;
import com.jydev.noticeboard.file.model.response.StoreFileResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

    private final ImageFileSystem fileSystem;

    @PostMapping("/image")
    public StoreFileResponse storeFile(@RequestBody MultipartFile file, HttpServletRequest request) throws IOException {
        StoreFile storeFile = fileSystem.storeFile(file, FileType.IMAGE);
        return new StoreFileResponse(storeFile.getHttpProtocolPath());
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws MalformedURLException {
        String fileProtocolUrl = fileSystem.getFileProtocolUrl(fileName);
        UrlResource urlResource = new UrlResource(fileProtocolUrl);
        MediaType mediaType = fileSystem.getImageMediaType(fileName).orElse(MediaType.ALL);
        return ResponseEntity.ok().
                contentType(mediaType).
                body(urlResource);
    }
}
