package edu.byu.cs.tweeter.client.model.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.backgroundTask.GetCountTask;
import edu.byu.cs.tweeter.client.model.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.services.MainService;

public class FollowCountHandler extends BackgroundTaskHandler<MainService.MainActivityObserver> {

    public FollowCountHandler(MainService.MainActivityObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(MainService.MainActivityObserver observer, Bundle data) {
        int count = data.getInt(GetCountTask.COUNT_KEY);
        observer.getCountSuccess(count);
    }
}
