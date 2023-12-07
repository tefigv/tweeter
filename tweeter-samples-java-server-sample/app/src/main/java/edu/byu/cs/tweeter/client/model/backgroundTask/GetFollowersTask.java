package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.services.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerResponse;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask {

    private static final String LOG_TAG = "GetFollowersTask)";
    public GetFollowersTask(FollowService followService,AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(followService,authToken, targetUser, limit, lastFollower, messageHandler);
    }

    @Override
    public void runTask() throws IOException {
        try {
            assert targetUser != null;
            String targetUserAlias = targetUser.getAlias();
            String lastFollowerAlias = lastItem == null ? null : lastItem.getAlias();

            FollowerRequest request = new FollowerRequest(authToken, targetUserAlias, limit, lastFollowerAlias);
            FollowerResponse response = getServerFacade().getFollowers(request, FollowService.FOLLOWERS_URL);

            if (response.isSuccess()) {
                items = response.getFollowers();
                this.hasMorePages = response.getHasMorePages();
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get followers", ex);
            sendExceptionMessage(ex);
        }
    }

}
