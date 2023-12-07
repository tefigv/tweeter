package edu.byu.cs.tweeter.client.presentors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.services.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends StatusPresenter {


    public StoryPresenter(View view, User user){
        super(view,user);
    }

    @Override
    public void startService() {
        StatusService storyService = new StatusService();
        storyService.getStoryStatus(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, (Status) lastItem,this);
    }
}
