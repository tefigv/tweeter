package edu.byu.cs.tweeter.client.presentors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.services.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends StatusPresenter {


    public FeedPresenter(View view, User user){
        super(view,user);
    }


    @Override
    public void startService() {
        StatusService feedService = new StatusService();
        feedService.getFeedStatus(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, (Status) lastItem,this);
    }

}
