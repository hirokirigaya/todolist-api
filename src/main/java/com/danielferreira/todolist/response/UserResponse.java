package com.danielferreira.todolist.response;

import java.util.HashMap;
import java.util.Map;

public class UserResponse {
    public static Object generateResponse(String token, Object user) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("user", user);

        return map;
    }
}
