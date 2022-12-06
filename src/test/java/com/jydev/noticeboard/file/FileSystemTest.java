package com.jydev.noticeboard.file;

import com.jydev.noticeboard.file.util.FileDependency;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileSystemTest {
    private final FileSystem fileSystem = FileDependency.fileSystem;

    @Test
    public void getExtTest(){
        String ext = "jpg";
        String fileName = "image."+ext;
        String result = fileSystem.getExt(fileName);
        Assertions.assertThat(result).isEqualTo(ext);
    }
}
