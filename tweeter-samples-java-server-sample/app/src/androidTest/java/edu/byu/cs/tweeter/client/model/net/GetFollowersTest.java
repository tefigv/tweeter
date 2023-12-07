package edu.byu.cs.tweeter.client.model.net;

import net.bytebuddy.utility.dispatcher.JavaDispatcher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class GetFollowersTest {

    static ServerFacade serverFacade;
    FollowerRequest request;
    FollowerResponse response;
    String urlPath;
    static AuthToken authToken;

    @BeforeAll
    static void beforeAll(){
        serverFacade = new ServerFacade();
        authToken = FakeData.getInstance().getAuthToken();
    }

    @Test
    void getFollowersSuccess() throws IOException, TweeterRemoteException {
        urlPath = "/getfollowers";
        request = new FollowerRequest(authToken, "@allen", 5, null);
        List<User> followers = new ArrayList<>();
        followers.add(FakeData.getInstance().getFakeUsers().get(0));
        followers.add(FakeData.getInstance().getFakeUsers().get(1));
        followers.add(FakeData.getInstance().getFakeUsers().get(2));
        followers.add(FakeData.getInstance().getFakeUsers().get(3));
        followers.add(FakeData.getInstance().getFakeUsers().get(4));

        response = serverFacade.getFollowers(request,urlPath);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(followers, response.getFollowers());
        Assertions.assertTrue(response.getHasMorePages());
    }

    @Test
    void getFollowersFail() throws IOException, TweeterRemoteException {
        urlPath = "/getfollowers";
        request = new FollowerRequest(null, null, 0, null);
        response = serverFacade.getFollowers(request,urlPath);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertFalse(response.getHasMorePages());

    }

    @Test
    void getFollowersException(){
        String exception = "";
        urlPath = "/nopath";
        request = new FollowerRequest(authToken, "@allen", 5, null);
        try{
            response = serverFacade.getFollowers(request,urlPath);
        }catch (Exception ex){
            exception = ex.getMessage();
            Assertions.assertEquals("An unknown error occurred. Response code = 403", exception);
        }

    }

}
