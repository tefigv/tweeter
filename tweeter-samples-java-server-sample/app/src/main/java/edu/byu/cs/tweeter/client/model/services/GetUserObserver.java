package edu.byu.cs.tweeter.client.model.services;

import edu.byu.cs.tweeter.model.domain.User;

public interface GetUserObserver extends ServiceObserver {

    void successfulGetUser(User user);
}
