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
import java.util.UUID;

@Component
public class FileSystem {

    @Value("${file.dir}")
    protected String fileDir;

    @Value("${url}")
    protected String url;

    private String getFullFilePath(String filePath){
        return fileDir+filePath;
    }

    public StoreFile storeFile(MultipartFile file, FileType fileType) throws IOException {
        Resource resource = file.getResource();
        String uniqueFileName = getUniqueFileName(resource.getFilename());
        String filePath = getFullFilePath(uniqueFileName);
        file.transferTo(new File(filePath));
        return new StoreFile(resource.getFilename(),getFileProtocolUrl(uniqueFileName),getHttpProtocolUrl(uniqueFileName,fileType));
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

    public String getHttpProtocolUrl(String fileName,FileType fileType){
        return url+"file/"+fileType.getProtocol()+"/"+fileName;
    }


}
