package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.services.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FolloweeRequest;
import edu.byu.cs.tweeter.model.net.response.FolloweeResponse;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask {

    private static final String LOG_TAG = "GetFollowingTask)";

    public GetFollowingTask(FollowService followService, AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler) {
        super(followService,authToken, targetUser, limit, lastFollowee, messageHandler);
    }

    @Override
    public void runTask() throws IOException {
        try {
            String targetUserAlias = targetUser == null ? null : targetUser.getAlias();
            String lastFolloweeAlias = lastItem == null ? null : lastItem.getAlias();

            FolloweeRequest request = new FolloweeRequest(authToken, targetUserAlias, limit, lastFolloweeAlias);
            FolloweeResponse response = getServerFacade().getFollowees(request, FollowService.FOLLOWING_URL);

            if (response.isSuccess()) {
                items = response.getFollowees();
                this.hasMorePages = response.getHasMorePages();
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get followees", ex);
            sendExceptionMessage(ex);
        }
    }

}
