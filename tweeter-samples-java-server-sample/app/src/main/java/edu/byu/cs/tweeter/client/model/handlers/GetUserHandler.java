package edu.byu.cs.tweeter.client.model.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.services.GetUserObserver;
import edu.byu.cs.tweeter.client.model.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends BackgroundTaskHandler<GetUserObserver> {

    public GetUserHandler(GetUserObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(GetUserObserver observer, Bundle data) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);

        observer.successfulGetUser(user);
    }
}
