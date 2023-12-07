package edu.byu.cs.tweeter.client.presentors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.services.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends FollowPresenter{



    public FollowingPresenter(View view, User user) {
        super(view,user);
    }

    @Override
    public void startService() {
        var followService = new FollowService();
        followService.getFollowing(Cache.getInstance().getCurrUserAuthToken(), user,PAGE_SIZE,(User) lastItem,this);
    }

}
