package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.server.lambda.service.GetCountService;

public class GetFollowCountHandler implements RequestHandler<GetCountRequest, GetCountResponse> {
    @Override
    public GetCountResponse handleRequest(GetCountRequest input, Context context) {
        GetCountService service = new GetCountService();
        return service.getCount(input);
    }
}
