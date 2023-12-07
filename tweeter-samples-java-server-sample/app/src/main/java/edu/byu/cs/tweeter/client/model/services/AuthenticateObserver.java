package edu.byu.cs.tweeter.client.model.services;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthenticateObserver extends ServiceObserver {

    void successfulAuthentication(AuthToken authToken, User user);

}
