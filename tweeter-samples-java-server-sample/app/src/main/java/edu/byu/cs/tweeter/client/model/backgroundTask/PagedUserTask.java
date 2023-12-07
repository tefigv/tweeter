package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.services.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FolloweeRequest;
import edu.byu.cs.tweeter.model.net.response.FolloweeResponse;

public abstract class PagedUserTask extends PagedTask<User> {
    FollowService followService;
    protected PagedUserTask(FollowService followService,AuthToken authToken, User targetUser, int limit, User lastItem, Handler messageHandler) {
        super(authToken, targetUser, limit, lastItem, messageHandler);
        this.followService = followService;
    }

    @Override
    protected final List<User> getUsersForItems(List<User> items) {
        return items;
    }
}
