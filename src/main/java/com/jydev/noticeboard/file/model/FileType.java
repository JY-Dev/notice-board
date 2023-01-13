package com.jydev.noticeboard.file.model;

public enum FileType {
    IMAGE("image");
    private final String protocol;
    FileType(String protocol){
        this.protocol = protocol;
    }

    public String getProtocol(){
        return protocol;
    }
}
