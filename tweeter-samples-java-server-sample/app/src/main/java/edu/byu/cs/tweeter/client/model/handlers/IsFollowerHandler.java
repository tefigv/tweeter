package edu.byu.cs.tweeter.client.model.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.services.MainService;

public class IsFollowerHandler extends BackgroundTaskHandler<MainService.MainActivityObserver> {

    public IsFollowerHandler(MainService.MainActivityObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(MainService.MainActivityObserver observer, Bundle data) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);

        observer.successfulIsFollower(isFollower);
    }
}
