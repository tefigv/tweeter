package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.services.MainService;
import edu.byu.cs.tweeter.client.model.services.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

/**
 * Background task that posts a new status sent by a user.
 */
public class PostStatusTask extends AuthenticatedTask {

    private static final String LOG_TAG = "PostStatusTask";

    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private final Status status;

    public PostStatusTask(MainService mainService, AuthToken authToken, Status status, Handler messageHandler) {
        super(authToken, messageHandler);
        this.status = status;
    }

    @Override
    protected void runTask() {

        try{
            PostStatusRequest request = new PostStatusRequest(authToken,status);
            PostStatusResponse response = getServerFacade().postStatus(request, MainService.POST_STATUS_URL);
            if(response.isSuccess()){
                // Call sendSuccessMessage if successful
                sendSuccessMessage();
            }else{
                sendFailedMessage(response.getMessage());
            }
        }catch(IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG, "Failed to post", ex);
            sendExceptionMessage(ex);
        }


        // or call sendFailedMessage if not successful
        // sendFailedMessage()
    }

}
