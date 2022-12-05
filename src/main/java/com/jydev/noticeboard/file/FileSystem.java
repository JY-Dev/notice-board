package com.jydev.noticeboard.file;

import com.jydev.noticeboard.file.model.FileType;
import com.jydev.noticeboard.file.model.StoreFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class FileSystem {

    @Value("${file.dir}")
    private String fileDir;

    @Value("${url}")
    private String url;

    private String getFullFilePath(String filePath){
        return fileDir+filePath;
    }

    public StoreFile storeFile(MultipartFile file, FileType fileType) throws IOException {
        Resource resource = file.getResource();
        String uniqueFileName = getUniqueFileName(resource.getFilename());
        String filePath = getFullFilePath(uniqueFileName);
        file.transferTo(new File(filePath));
        return new StoreFile(resource.getFilename(),getFileProtocolUrl(uniqueFileName),getHttpProtocolUrl(uniqueFileName,fileType.getProtocol()));
    }

    public String getUniqueFileName(String fileName){
        return UUID.randomUUID()+"."+ getExt(fileName);
    }

    public String getExt(String fileName) {
        int extIndex = fileName.lastIndexOf(".");
        return fileName.substring(extIndex+1);
    }

    public String getFileProtocolUrl(String fileName){
        return "file:"+getFullFilePath(fileName);
    }

    public String getHttpProtocolUrl(String fileName,String fileType){
        return url+"file/"+fileType+"/"+fileName;
    }

    public Optional<MediaType> getImageMediaType(String fileName){
        String imageExtRegex = "([^s]+(.(?i)(jpe?g|png|gif|bmp))$)";
        if(!fileName.matches(imageExtRegex))
            return Optional.empty();
        else{
            MediaType mediaType = switch (getExt(fileName)) {
                case "png" -> MediaType.IMAGE_PNG;
                case "gif" -> MediaType.IMAGE_GIF;
                default -> MediaType.IMAGE_JPEG;
            };
            return Optional.of(mediaType);
        }
    }
}
