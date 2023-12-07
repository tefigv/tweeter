package edu.byu.cs.tweeter.server.lambda.service;

import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.*;
import edu.byu.cs.tweeter.server.dao.bean.User;

public class FollowCheckerService extends AuthenticatedService{


    public IsFollowerResponse isFollower(IsFollowerRequest request){
        if(request.getFollower() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower");
        }
        if(request.getFollowee() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a followee");
        }
        if(validateToken(request.getAuthToken().getToken())) {
            boolean isFollower = followDAO.isItemInTable(request.getFollower().getAlias(),
                    request.getFollowee().getAlias());
            return new IsFollowerResponse(isFollower);
        }else{
            throw new RuntimeException("Invalid auth token");
        }
    }

    public FollowResponse follow(FollowRequest request){
        if(request.getFollowee() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a followee");
        }
        if(request.getFollower() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower");
        }
        if(validateToken(request.getAuthToken().getToken())) {

            followDAO.putItem(request.getFollower().getAlias(),request.getFollowee().getAlias(),
                    request.getFollower().getName(), request.getFollowee().getName());
            increaseFollowees(request.getFollower().getAlias());
            increaseFollowers(request.getFollowee().getAlias());

            return new FollowResponse();
        }else{
            throw new RuntimeException("[Bad Request] invalid auth token");
        }
    }

    public UnfollowResponse unfollow(UnfollowRequest request){
        if(request.getFollowee() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower");
        }
        if(request.getFollower() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower");
        }

        if(validateToken(request.getAuthToken().getToken())) {
            followDAO.deleteItem(request.getFollower().getAlias(),request.getFollowee().getAlias());

            decreaseFollowees(request.getFollower().getAlias());
            decreaseFollowers(request.getFollowee().getAlias());

            return new UnfollowResponse();
        }else{
            throw new RuntimeException("Invalid auth token");
        }
    }

    private void increaseFollowees(String alias){
        User user = userDAO.getItem(alias);
        userDAO.putItem(user.getFirstName(), user.getLastName(), user.getAlias(), user.getPassword(),
                user.getFollowers_count(), user.getFollowees_count()+1, user.getImage());
    }

    private void increaseFollowers(String alias){
        User user = userDAO.getItem(alias);
        userDAO.putItem(user.getFirstName(), user.getLastName(), user.getAlias(), user.getPassword(),
                user.getFollowers_count()+1, user.getFollowees_count(),user.getImage());
    }

    private void decreaseFollowees(String alias){
        User user = userDAO.getItem(alias);
        userDAO.putItem(user.getFirstName(), user.getLastName(), user.getAlias(), user.getPassword(),
                user.getFollowers_count()+1, user.getFollowees_count(),user.getImage());
    }

    private void decreaseFollowers(String alias){
        User user = userDAO.getItem(alias);
        userDAO.putItem(user.getFirstName(), user.getLastName(), user.getAlias(), user.getPassword(),
                user.getFollowers_count(), user.getFollowees_count()-1,user.getImage());
    }
}
