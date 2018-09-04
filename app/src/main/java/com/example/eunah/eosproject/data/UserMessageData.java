package com.example.eunah.eosproject.data;

/**
 * Created by leeeunah on 2018. 3. 3..
 */

public class UserMessageData {
    private String date;
    private String destinationUserId;
    private String userId;
    private String myId;
    private String message;

    public UserMessageData(){}

    public UserMessageData(String date, String destinationUserId, String userId, String myId, String message){
        this.date = date;
        this.destinationUserId = destinationUserId;
        this.userId = userId;
        this.myId = myId;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String userId) {
        this.date = userId;
    }

    public String getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(String destinationUserId) {
        this.destinationUserId = destinationUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
