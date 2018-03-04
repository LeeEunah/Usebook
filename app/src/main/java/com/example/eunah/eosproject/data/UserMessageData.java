package com.example.eunah.eosproject.data;

/**
 * Created by leeeunah on 2018. 3. 3..
 */

public class UserMessageData {
    private String userId;
    private String destinationUserId;
    private String message;

    public UserMessageData(){}

    public UserMessageData(String userId, String destinationUserId, String message){
        this.userId = userId;
        this.destinationUserId = destinationUserId;
        this.message = message;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(String destinationUserId) {
        this.destinationUserId = destinationUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
