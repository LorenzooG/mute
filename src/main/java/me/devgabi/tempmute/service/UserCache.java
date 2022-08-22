package me.devgabi.tempmute.service;

import me.devgabi.tempmute.entity.User;

import java.util.LinkedHashMap;
import java.util.Map;

public final class UserCache {

    private final Map<String, User> userCache = new LinkedHashMap<>();

    public User findByName(String username) {
        return userCache.get(username);
    }

    public User registerOrUpdate(String username, User user) {
        userCache.put(username, user);
        return user;
    }

    public void remove(String username) {
        userCache.remove(username);
    }
}
