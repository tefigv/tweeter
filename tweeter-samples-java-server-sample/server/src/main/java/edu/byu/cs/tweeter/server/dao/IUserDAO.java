package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.bean.User;

public interface IUserDAO {
    void putItem(String firstName, String lastName, String alias, String password,int follower_count, int followee_count, String image);
    User getItem(String alias);
    void deleteItem(String alias);
}
