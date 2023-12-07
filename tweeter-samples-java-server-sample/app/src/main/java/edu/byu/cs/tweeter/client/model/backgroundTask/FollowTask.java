package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.services.MainService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AuthenticatedTask {

    public static final String LOG_TAG = "FollowTask";
    /**
     * The user that is being followed.
     */
    private final User followee;
    private final User follower;

    public FollowTask(AuthToken authToken,User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.followee = followee;
        this.follower = follower;
    }

    @Override
    protected void runTask() throws IOException {
        try{
            FollowRequest request = new FollowRequest(authToken,follower,followee);
            FollowResponse response = getServerFacade().follow(request, MainService.FOLLOW);
            if(response.isSuccess()){
                sendSuccessMessage();
            }else{
                sendFailedMessage(response.getMessage());
            }
        }catch (IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG,"Failed to follow",  ex);
            sendExceptionMessage(ex);
        }
    }

}
