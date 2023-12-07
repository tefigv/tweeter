package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.bean.AuthToken;

public interface IAuthTokenDAO {
    void putItem(String token, long timestamp);
    AuthToken getItem(String token);
    void deleteItem(String token);
    boolean isTokenInTable(String token);
}
