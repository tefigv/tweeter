package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.services.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask {

    private static String LOG_TAG = "GedFeedTask";

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    public void runTask() throws IOException {

        try {
            String targetUserAlias = targetUser == null ? null : targetUser.getAlias();

            FeedRequest request = new FeedRequest(authToken, targetUserAlias, limit, lastItem);
            FeedResponse response = getServerFacade().getFeed(request, StatusService.FEED_URL);

            if (response.isSuccess()) {
                items = response.getFeed();
                this.hasMorePages = response.getHasMorePages();
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        }catch (IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG, "Failed to get feed",ex);
            sendExceptionMessage(ex);
        }

    }


}
