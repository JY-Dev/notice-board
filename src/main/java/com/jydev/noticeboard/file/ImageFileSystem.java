package com.jydev.noticeboard.file;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ImageFileSystem extends FileSystem{
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
