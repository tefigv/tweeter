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
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask {

    private static String LOG_TAG = "GedStoryTask";

    public GetStoryTask(StatusService statusService,AuthToken authToken, User targetUser, int limit, Status lastStatus,
                        Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    public void runTask() throws IOException {
        try{
            String targetUserAlias = targetUser == null ? null : targetUser.getAlias();

            StoryRequest request = new StoryRequest(authToken,targetUserAlias,limit,lastItem);
            StoryResponse response = getServerFacade().getStory(request, StatusService.STORY_URL);

            if(response.isSuccess()){
                items = response.getStory();
                this.hasMorePages = response.getHasMorePages();
                sendSuccessMessage();
            }else{
                sendFailedMessage(response.getMessage());
            }
        }catch (IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG, "Failed to get story",ex);
            sendExceptionMessage(ex);
        }

    }

}
