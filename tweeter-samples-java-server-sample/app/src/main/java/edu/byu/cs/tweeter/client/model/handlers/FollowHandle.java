package edu.byu.cs.tweeter.client.model.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.backgroundTask.PagedUserTask;
import edu.byu.cs.tweeter.client.model.services.FollowService;
import edu.byu.cs.tweeter.client.model.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowHandle extends BackgroundTaskHandler<FollowService.GetFollowObserver> {

    public FollowHandle(FollowService.GetFollowObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.GetFollowObserver observer, Bundle data) {
        List<User> follow = (List<User>) data.getSerializable(PagedUserTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedUserTask.MORE_PAGES_KEY);
        observer.successfulGetFollow(follow, hasMorePages);
    }
}
