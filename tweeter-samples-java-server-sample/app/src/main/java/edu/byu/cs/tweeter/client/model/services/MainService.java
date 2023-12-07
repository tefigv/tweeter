package edu.byu.cs.tweeter.client.model.services;

import edu.byu.cs.tweeter.client.model.backgroundTask.*;
import edu.byu.cs.tweeter.client.model.handlers.*;
import edu.byu.cs.tweeter.model.domain.*;

public class MainService {

    public static final String FOLLOW_COUNT_URL = "/getfollowcount";
    public static final String POST_STATUS_URL = "/poststatus";
    public static final String IS_FOLLOWER = "/isfollower";
    public static final String FOLLOW = "/follow";

    public static final String UNFOLLOW = "/unfollow";
    public static final String LOGOUT = "/logout";


    public interface MainActivityObserver extends ServiceObserver {

        void successfulIsFollower(boolean isFollower);

        void successFollowButton(boolean followButtonUpdate);

        void enableFollowButton(boolean doEnable);

        void getCountSuccess(int count);

        void successMessage(String message,String request);

    }

    public void isFollower(AuthToken authToken, User follower, User followee, MainActivityObserver observer) {

        IsFollowerHandler isFollowerHandler = new IsFollowerHandler(observer);

       IsFollowerTask isFollowerTask = new IsFollowerTask(authToken, follower, followee, isFollowerHandler);
        BackgroundTaskUtils.runTask(isFollowerTask);
    }

    public void unfollow(AuthToken authToken, User follower, User selectedUser, MainActivityObserver observer) {

        FollowButtonHandler followButtonHandler = new FollowButtonHandler(observer,true);

        UnfollowTask unfollowTask = new UnfollowTask(authToken, follower,selectedUser, followButtonHandler);
        BackgroundTaskUtils.runTask(unfollowTask);
        observer.enableFollowButton(true);
    }


    public void followersCount(AuthToken authToken, User selectedUser, MainActivityObserver observer) {
        FollowCountHandler followCountHandler = new FollowCountHandler(observer);

        GetCountTask getCountTask = new GetCountTask(authToken,
                selectedUser,"followers" ,followCountHandler);
        BackgroundTaskUtils.runTask(getCountTask);
    }

    public void followingCount(AuthToken authToken, User selectedUser, MainActivityObserver observer) {
        FollowCountHandler followCountHandler = new FollowCountHandler(observer);

        GetCountTask getCountTask = new GetCountTask(authToken,
                selectedUser,"followees", followCountHandler);
        BackgroundTaskUtils.runTask(getCountTask);
    }

    public void follow(AuthToken authToken, User follower, User selectedUser, MainActivityObserver observer) {
        FollowButtonHandler followButtonHandler = new FollowButtonHandler(observer,false);

        FollowTask followTask = new FollowTask(authToken, follower,selectedUser, followButtonHandler);
        BackgroundTaskUtils.runTask(followTask);
        observer.enableFollowButton(true);
    }

    public void logout(AuthToken authToken, MainActivityObserver observer) {
        MessageHandler messageHandler = new MessageHandler(observer,"Successfully logged out");

        LogoutTask logoutTask = new LogoutTask(authToken, messageHandler);
        BackgroundTaskUtils.runTask(logoutTask);
    }


    public void status(AuthToken authToken, Status status, MainActivityObserver observer) {
        MessageHandler messageHandler = new MessageHandler(observer,"Successfully Posted!");

        PostStatusTask statusTask = new PostStatusTask(this,authToken, status, messageHandler);
        BackgroundTaskUtils.runTask(statusTask);

    }

}
