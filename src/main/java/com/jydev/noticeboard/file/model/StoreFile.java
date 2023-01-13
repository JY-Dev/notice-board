package com.jydev.noticeboard.file.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StoreFile {
    private String fileName;
    private String fileProtocolPath;
    private String httpProtocolPath;
}
