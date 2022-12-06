package com.jydev.noticeboard.file.util;

import com.jydev.noticeboard.file.FileSystem;
import com.jydev.noticeboard.file.ImageFileSystem;

public class FileDependency {
    public final static FileSystem fileSystem = new FileSystem();
    public final static ImageFileSystem imageFileSystem = new ImageFileSystem();
}
