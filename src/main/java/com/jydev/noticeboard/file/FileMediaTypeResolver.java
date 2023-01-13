package com.jydev.noticeboard.file;

import com.jydev.noticeboard.file.model.FileType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FileMediaTypeResolver {
    public Optional<MediaType> resolve(FileType fileType,String fileName){
        return switch (fileType){
            case IMAGE -> getImageMediaType(fileName);
            default -> Optional.of(MediaType.ALL);
        };
    }

    public Optional<MediaType> getImageMediaType(String fileName){
        String imageExtRegex = "([^s]+(.(?i)(jpe?g|png|gif|bmp))$)";
        if(!fileName.matches(imageExtRegex))
            return Optional.empty();
        else{
            MediaType mediaType = switch (FileSystem.getExt(fileName)) {
                case "png" -> MediaType.IMAGE_PNG;
                case "gif" -> MediaType.IMAGE_GIF;
                default -> MediaType.IMAGE_JPEG;
            };
            return Optional.of(mediaType);
        }
    }
}
