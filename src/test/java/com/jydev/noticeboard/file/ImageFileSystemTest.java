package com.jydev.noticeboard.file;

import com.jydev.noticeboard.file.util.FileDependency;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;

public class ImageFileSystemTest {
    private final ImageFileSystem fileSystem = FileDependency.imageFileSystem;
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
