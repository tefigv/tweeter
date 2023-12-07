package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.lambda.service.StatusService;

public class GetStoryHandler implements RequestHandler<StoryRequest, StoryResponse> {


    @Override
    public StoryResponse handleRequest(StoryRequest input, Context context) {
        StatusService service = new StatusService();
        return service.getStory(input);
    }
}