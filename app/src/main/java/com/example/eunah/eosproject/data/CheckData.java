package com.example.eunah.eosproject.data;

/**
 * Created by leeeunah on 2017. 11. 27..
 */

public class CheckData {
    private boolean underlinePencil;
    private boolean underlinePen;
    private boolean writePencil;
    private boolean writePen;
    private boolean coverClean;
    private boolean noName;
    private boolean noPageColor;
    private boolean noPageRuin;
    private boolean direct;
    private boolean delivery;
    private boolean soldOrNot;

    public CheckData(){}

    public CheckData(boolean underlinePencil, boolean underlinePen, boolean writePencil, boolean writePen,
                     boolean coverClean, boolean noName, boolean noPageColor, boolean noPageRuin,
                     boolean direct, boolean delivery, boolean soldOrNot){
        this.underlinePencil = underlinePencil;
        this.underlinePen = underlinePen;
        this.writePencil = writePencil;
        this.writePen = writePen;
        this.coverClean = coverClean;
        this.noName = noName;
        this.noPageColor = noPageColor;
        this.noPageRuin = noPageRuin;
        this.direct = direct;
        this.delivery = delivery;
        this.soldOrNot = soldOrNot;
    }

    public boolean isUnderlinePencil() {
        return underlinePencil;
    }

    public void setUnderlinePencil(boolean underlinePencil) {
        this.underlinePencil = underlinePencil;
    }

    public boolean isUnderlinePen() {
        return underlinePen;
    }

    public void setUnderlinePen(boolean underlinePen) {
        this.underlinePen = underlinePen;
    }

    public boolean isWritePencil() {
        return writePencil;
    }

    public void setWritePencil(boolean writePencil) {
        this.writePencil = writePencil;
    }

    public boolean isWritePen() {
        return writePen;
    }

    public void setWritePen(boolean writePen) {
        this.writePen = writePen;
    }

    public boolean isCoverClean() {
        return coverClean;
    }

    public void setCoverClean(boolean coverClean) {
        this.coverClean = coverClean;
    }

    public boolean isNoName() {
        return noName;
    }

    public void setNoName(boolean noName) {
        this.noName = noName;
    }

    public boolean isNoPageColor() {
        return noPageColor;
    }

    public void setNoPageColor(boolean noPageColor) {
        this.noPageColor = noPageColor;
    }

    public boolean isNoPageRuin() {
        return noPageRuin;
    }

    public void setNoPageRuin(boolean noPageRuin) {
        this.noPageRuin = noPageRuin;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isSoldOrNot() {
        return soldOrNot;
    }

    public void setSoldOrNot(boolean soldOrNot) {
        this.soldOrNot = soldOrNot;
    }
}
