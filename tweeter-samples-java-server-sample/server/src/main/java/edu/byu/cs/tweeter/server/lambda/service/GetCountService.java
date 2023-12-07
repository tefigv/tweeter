package edu.byu.cs.tweeter.server.lambda.service;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.server.dao.bean.User;

public class GetCountService extends AuthenticatedService {

    public GetCountResponse getCount (GetCountRequest request){
        if(request.getTargetUser() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a target alias");
        }
        if( request.getAuthToken() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a token");
        }
        if(request.getFollowType() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follow type");
        }
        int count = 0;
        if(authTokenDAO.isTokenInTable(request.getAuthToken().getToken())) {
            User user = userDAO.getItem(request.getTargetUser().getAlias());

            if(request.getFollowType().equalsIgnoreCase("followers")) {
                count = user.getFollowers_count();
            }else if(request.getFollowType().equalsIgnoreCase("followees")){
                count = user.getFollowees_count();
            }
        }
        return new GetCountResponse(count);
    }

}
