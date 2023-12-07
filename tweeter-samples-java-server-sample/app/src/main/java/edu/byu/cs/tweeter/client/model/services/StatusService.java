package edu.byu.cs.tweeter.client.model.services;

import java.util.List;

import edu.byu.cs.tweeter.client.model.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.model.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.handlers.StatusHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {

    public static final String FEED_URL = "/getfeed";
    public static final String STORY_URL = "/getstory";

    public void getFeedStatus(AuthToken authToken, User user, int pageSize, Status lastStatus, GetStatusObserver observer) {
        GetFeedTask getFeedTask = new GetFeedTask(authToken,
                user, pageSize, lastStatus, new StatusHandler(observer));
        BackgroundTaskUtils.runTask(getFeedTask);
    }

    public void getStoryStatus(AuthToken authToken, User user, int pageSize, Status lastStatus, GetStatusObserver observer) {
        GetStoryTask getStoryTask = new GetStoryTask(this,authToken,
                user, pageSize, lastStatus, new StatusHandler(observer));
        BackgroundTaskUtils.runTask(getStoryTask);
    }

    public interface GetStatusObserver extends ServiceObserver {
        void successfulGetStatus(List<Status> status, boolean hasMorePages);
    }
}

