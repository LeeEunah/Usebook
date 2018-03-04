package com.example.eunah.eosproject.data;

import java.util.Date;
import java.util.UUID;

/**
 * Created by leeeunah on 2017. 10. 12..
 */

public class BookData{
    private String id;
    private String dTitle;
    private String dWriter;
    private String dPublish;
    private String dDate;
    private String dPrice;
    private String dExpectationPrice;
    private String area;
    private String extraExplanation;
    private String currentDate;
    private String coverImage;
    private String insideImage;

    public BookData(){}

    public BookData(String id, String dTitle, String dWriter, String dPublish,
                    String dDate, String dPrice, String dExpectationPrice, String area,
                    String extraExplanation, String currentDate, String coverImage, String insideImage){
        this.id = id;
        this.dTitle = dTitle;
        this.dWriter = dWriter;
        this.dPublish = dPublish;
        this.dDate = dDate;
        this.dPrice = dPrice;
        this.dExpectationPrice = dExpectationPrice;
        this.area = area;
        this.extraExplanation = extraExplanation;
        this.currentDate = currentDate;
        this.coverImage = coverImage;
        this.insideImage = insideImage;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getdTitle() {
        return dTitle;
    }

    public void setdTitle(String dTitle) {
        this.dTitle = dTitle;
    }

    public String getdWriter() {
        return dWriter;
    }

    public void setdWriter(String dWriter) {
        this.dWriter = dWriter;
    }

    public String getdPublish() {
        return dPublish;
    }

    public void setdPublish(String dPublish) {
        this.dPublish = dPublish;
    }

    public String getdPrice() {
        return dPrice;
    }

    public void setdPrice(String dPrice) {
        this.dPrice = dPrice;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getdExpectationPrice() {
        return dExpectationPrice;
    }

    public void setdExpectationPrice(String dExpectationPrice) {
        this.dExpectationPrice = dExpectationPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExtraExplanation() {
        return extraExplanation;
    }

    public void setExtraExplanation(String extraExplanation) {
        this.extraExplanation = extraExplanation;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getdCoverImage() {
        return coverImage;
    }

    public void setdCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getdInsideImage() {
        return insideImage;
    }

    public void setdInsideImage(String insideImage) {
        this.insideImage = insideImage;
    }
}
