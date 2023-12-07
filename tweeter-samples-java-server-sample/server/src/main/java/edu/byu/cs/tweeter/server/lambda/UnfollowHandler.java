package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.lambda.service.FollowCheckerService;

public class UnfollowHandler implements RequestHandler<UnfollowRequest, UnfollowResponse> {
    @Override
    public UnfollowResponse handleRequest(UnfollowRequest input, Context context) {
        FollowCheckerService service = new FollowCheckerService();
        return service.unfollow(input);
    }
}
