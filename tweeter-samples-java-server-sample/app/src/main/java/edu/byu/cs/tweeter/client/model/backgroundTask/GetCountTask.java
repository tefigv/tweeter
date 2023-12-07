package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.services.MainService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;

public class GetCountTask extends AuthenticatedTask {

    private static final String LOG_TAG = "GetCountTask";

    public static final String COUNT_KEY = "count";

    /**
     * The user whose count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private final User targetUser;

    private int count;
    private String followType;

    public GetCountTask(AuthToken authToken, User targetUser, String followType, Handler messageHandler) {
        super(authToken, messageHandler);
        this.targetUser = targetUser;
        this.followType = followType;
    }

    protected User getTargetUser() {
        return targetUser;
    }

    @Override
    protected void runTask() throws IOException {
        //count = runCountTask();

        try{
            GetCountRequest request = new GetCountRequest(authToken, targetUser,followType);
            GetCountResponse response = getServerFacade().getFollowCount(request, MainService.FOLLOW_COUNT_URL);

            if(response.isSuccess()){
                this.count = response.getCount();
                // Call sendSuccessMessage if successful
                sendSuccessMessage();
            }else{
                sendFailedMessage(response.getMessage());
            }
        }catch (IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG, "Failed to get count",ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putInt(COUNT_KEY, count);
    }
}
