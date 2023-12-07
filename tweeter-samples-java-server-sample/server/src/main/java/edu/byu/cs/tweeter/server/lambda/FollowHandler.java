package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.server.lambda.service.FollowCheckerService;

public class FollowHandler implements RequestHandler<FollowRequest, FollowResponse> {

    @Override
    public FollowResponse handleRequest(FollowRequest input, Context context) {
        FollowCheckerService service = new FollowCheckerService();
        return service.follow(input);
    }
}
