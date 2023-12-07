package edu.byu.cs.tweeter.client.model.services;

import java.util.List;

import edu.byu.cs.tweeter.client.model.backgroundTask.*;
import edu.byu.cs.tweeter.client.model.handlers.FollowHandle;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    public static final String FOLLOWING_URL = "/getfollowing";
    public static final String FOLLOWERS_URL = "/getfollowers";

    public void getFollowers(AuthToken authToken, User user, int limit, User lastFollower, GetFollowObserver observer) {
        GetFollowersTask followersTask = getGetFollowersTask(authToken,user,limit,lastFollower,observer);
        BackgroundTaskUtils.runTask(followersTask);
    }

    private GetFollowersTask getGetFollowersTask(AuthToken authToken, User user, int limit, User lastFollower, GetFollowObserver observer){
        return new GetFollowersTask(this, authToken,user,limit, lastFollower, new FollowHandle(observer));
    }

    public void getFollowing(AuthToken authToken, User user, int limit, User lastFollowee, GetFollowObserver observer) {
        GetFollowingTask followingTask = getGetFollowingTask(authToken,user,limit, lastFollowee,observer);
        BackgroundTaskUtils.runTask(followingTask);
    }

    private GetFollowingTask getGetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee, GetFollowObserver observer){
        return new GetFollowingTask(this, authToken, targetUser, limit, lastFollowee, new FollowHandle(observer));
    }

    public interface GetFollowObserver extends ServiceObserver {

        void successfulGetFollow(List<User> follow, boolean hasMorePages);

    }

}
