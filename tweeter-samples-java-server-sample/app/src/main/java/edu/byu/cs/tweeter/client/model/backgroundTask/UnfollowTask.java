package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.services.MainService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends AuthenticatedTask {

    public static final String LOG_TAG = "UnfollowTask";

    /**
     * The user that is being followed.
     */
    private final User followee;
    private final User follower;


    public UnfollowTask(AuthToken authToken,User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.followee = followee;
        this.follower = follower;
    }

    @Override
    protected void runTask() {
        // We could do this from the presenter, without a task and handler, but we will
        // eventually access the database from here when we aren't using dummy data.
        try{
            UnfollowRequest request = new UnfollowRequest(authToken, follower,followee);
            UnfollowResponse response = getServerFacade().unfollow(request, MainService.UNFOLLOW);
            if(response.isSuccess()){
                sendSuccessMessage();
            }else{
                sendFailedMessage(response.getMessage());
            }
        }catch (IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG,"Failed to unfollow",  ex);
            sendExceptionMessage(ex);
        }

        // Call sendSuccessMessage if successful
        sendSuccessMessage();
        // or call sendFailedMessage if not successful
        // sendFailedMessage()
    }


}
