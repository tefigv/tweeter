package edu.byu.cs.tweeter.server.lambda.service;


import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.*;
import edu.byu.cs.tweeter.server.dao.bean.*;
/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends AuthenticatedService{

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link } to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FolloweeResponse getFollowees(FolloweeRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }

         if(authTokenDAO.isTokenInTable(request.getAuthToken().getToken())) {

             DataPage<Follows> dataPage = followDAO.getPageOfFollowees(request.getFollowerAlias(), request.getLimit(), request.getLastFolloweeAlias());

             return new FolloweeResponse(getList(dataPage), dataPage.isHasMorePages());
         }else{
             throw new RuntimeException("[Bad Request] invalid auth token");
         }
    }
    public FollowerResponse getFollowers (FollowerRequest request){
        if(request.getFollowerAlias() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        }else
            if (request.getLimit() <= 0){
            throw new RuntimeException("[Bad Request]Request needs to have a positive limit");
        }

        if(validateToken(request.getAuthToken().getToken())) {
            DataPage<Follows> dataPage = followDAO.getPageOfFollowers(request.getFollowerAlias(), request.getLimit(), request.getLastFollowerAlias());

            return new FollowerResponse(getList(dataPage), dataPage.isHasMorePages());
        }else{
            throw new RuntimeException("[Bad Request] invalid auth token");
        }
    }

    private List<User> getList(DataPage<Follows> dataPage){
        List<User> follow = new ArrayList<>();
        for(int i = 0; i < dataPage.getValues().size(); i++){
            String alias = dataPage.getValues().get(i).getFollowee_handle();


            edu.byu.cs.tweeter.server.dao.bean.User daoUser = userDAO.getItem(alias);
            User user = new User(daoUser.getFirstName(),daoUser.getLastName(), daoUser.getAlias(),
                    daoUser.getImage());
            follow.add(user);
        }
        return follow;
    }

}
