package com.lorenzoog.tempmute.datasource;

import com.lorenzoog.tempmute.entity.User;
import com.lorenzoog.tempmute.service.UserCache;
import dev.king.universal.api.JdbcProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public final class UserDao {

    private static final String FIND_USER_BY_NAME_STATEMENT = "SELECT * FROM users WHERE username = ? LIMIT 1";
    private static final String UPDATE_USER_MUTED_PROPERTY_BY_NAME_STATEMENT = "REPLACE INTO users(username, muted) value (?, ?)";
    private static final String CREATE_TABLE_IF_NOT_EXISTS_STATEMENT = "CREATE TABLE IF NOT EXISTS users(username VARCHAR(32) NOT NULL PRIMARY KEY, muted BOOLEAN DEFAULT false)";

    private final JdbcProvider connection;

    @Getter
    private final UserCache userCache = new UserCache();

    public void createTableIfNotExists() {
        connection.update(CREATE_TABLE_IF_NOT_EXISTS_STATEMENT);
    }

    public User findByName(String username) {
        User byName = userCache.findByName(username);

        if(byName != null) return byName;

        return connection.query(FIND_USER_BY_NAME_STATEMENT, set -> {
            User user = new User(username, set.getBoolean("muted"));

            return userCache.registerOrUpdate(username, user);
        }, username).orElse(null);
    }

    public void updateUserEntity(String username, boolean condition) {
        connection.update(UPDATE_USER_MUTED_PROPERTY_BY_NAME_STATEMENT, username, condition);

        User cachedUser = userCache.findByName(username);

        if(cachedUser != null) {
            cachedUser.setMuted(condition);
        }
    }
}
