package com.example.eunah.eosproject.data;

/**
 * Created by leeeunah on 2018. 2. 10..
 */

public class FileNameData {
    private String coverFileName;
    private String insideFileName;

    public FileNameData(){}

    public FileNameData(String coverFileName, String insideFileName){
        this.coverFileName = coverFileName;
        this.insideFileName = insideFileName;
    }

    public String getCoverFileName() {
        return coverFileName;
    }

    public void setCoverFileName(String coverFileName) {
        this.coverFileName = coverFileName;
    }

    public String getInsideFileName() {
        return insideFileName;
    }

    public void setInsideFileName(String insideFileName) {
        this.insideFileName = insideFileName;
    }
}
