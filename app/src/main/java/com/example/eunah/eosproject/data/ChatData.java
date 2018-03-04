package com.example.eunah.eosproject.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leeeunah on 2018. 3. 4..
 */

public class ChatData {
    public Map<String, Object> users = new HashMap<>();
    public Map<String, Object> comments = new HashMap<>();

    public static class Comment{
        public String userId;
        public String message;
    }
}
