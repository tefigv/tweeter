package edu.byu.cs.tweeter.client.presentors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.services.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends FollowPresenter {

    public FollowersPresenter(View view, User user) {
        super(view, user);

    }

    public void startService(){
        var followerService = new FollowService();
        followerService.getFollowers(Cache.getInstance().getCurrUserAuthToken(), user,PAGE_SIZE,(User) lastItem,this);
    }
}
