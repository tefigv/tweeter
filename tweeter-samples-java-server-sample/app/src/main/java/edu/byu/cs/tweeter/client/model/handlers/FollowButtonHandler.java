package edu.byu.cs.tweeter.client.model.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.services.MainService;

public class FollowButtonHandler extends BackgroundTaskHandler<MainService.MainActivityObserver> {

    boolean followOrUnfollow;
    public FollowButtonHandler(MainService.MainActivityObserver observer, boolean followOrUnfollow) {
        super(observer);
        this.followOrUnfollow = followOrUnfollow;
    }

    @Override
    protected void handleSuccessMessage(MainService.MainActivityObserver observer, Bundle data) {

        observer.successFollowButton(followOrUnfollow);
    }
}
