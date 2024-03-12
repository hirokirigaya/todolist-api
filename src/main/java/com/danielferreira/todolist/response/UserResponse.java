package com.danielferreira.todolist.response;

import java.util.HashMap;
import java.util.Map;

public class UserResponse {
    public static Object generateResponse(String token, Object user) {
        Map<String, Object> map = new HashMap<String, Object>();
        long expiration = 6 * 60 * 60;
        map.put("token", token);
        map.put("expires_in", expiration);
        map.put("user", user);

        return map;
    }
}
