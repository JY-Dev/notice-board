package com.jydev.noticeboard.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;

public class FileSystemTest {
    private final FileSystem fileSystem = new FileSystem();

    @Test
    public void getExtTest(){
        String ext = "jpg";
        String fileName = "image."+ext;
        String result = fileSystem.getExt(fileName);
        Assertions.assertThat(result).isEqualTo(ext);
    }

    @Test
    public void getImageMediaTypeTest(){
        String imageFileName= "image.png";
        Optional<MediaType> mediaType = fileSystem.getImageMediaType(imageFileName);
        Assertions.assertThat(mediaType.orElse(null)).isEqualTo(MediaType.IMAGE_PNG);
    }

    @Test
    public void getImageMediaTypeWhenInputAnotherFileTypeTest(){
        String imageFileName= "image";
        Optional<MediaType> mediaType = fileSystem.getImageMediaType(imageFileName);
        Assertions.assertThat(mediaType.orElse(null)).isNull();
    }
}
