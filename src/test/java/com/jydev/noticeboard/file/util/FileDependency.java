package com.jydev.noticeboard.file.util;

import com.jydev.noticeboard.file.FileMediaTypeResolver;
import com.jydev.noticeboard.file.FileSystem;

public class FileDependency {
    public final static FileSystem fileSystem = new FileSystem(new FileMediaTypeResolver());
}
