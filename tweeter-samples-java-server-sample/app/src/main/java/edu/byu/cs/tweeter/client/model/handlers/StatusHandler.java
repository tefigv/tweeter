package edu.byu.cs.tweeter.client.model.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.backgroundTask.PagedStatusTask;
import edu.byu.cs.tweeter.client.model.services.StatusService;
import edu.byu.cs.tweeter.client.model.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.model.domain.Status;

public class StatusHandler extends BackgroundTaskHandler<StatusService.GetStatusObserver> {

    public StatusHandler(StatusService.GetStatusObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(StatusService.GetStatusObserver observer, Bundle data) {
        List<Status> statuses = (List<Status>) data.getSerializable(PagedStatusTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedStatusTask.MORE_PAGES_KEY);

        observer.successfulGetStatus(statuses, hasMorePages);
    }
}
