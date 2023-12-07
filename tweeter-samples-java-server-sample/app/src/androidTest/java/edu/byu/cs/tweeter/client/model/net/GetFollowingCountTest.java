package edu.byu.cs.tweeter.client.model.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class GetFollowingCountTest {
    static ServerFacade serverFacade;
    GetCountRequest request;
    GetCountResponse response;
    String urlPath;
    static AuthToken authToken;
    static User user;

    @BeforeAll
    static void beforeAll(){
        serverFacade = new ServerFacade();
        authToken = FakeData.getInstance().getAuthToken();
        user = FakeData.getInstance().getFirstUser();
    }
    @Test
    void getFollowerCountSuccess() throws IOException, TweeterRemoteException {
        urlPath = "/getfollowcount";
        request = new GetCountRequest(authToken,user,"followee");

        response = serverFacade.getFollowCount(request,urlPath);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(20, response.getCount());
    }

    @Test
    void getFollowerCountFail() throws IOException, TweeterRemoteException {
        urlPath = "/getfollowcount";
        request = new GetCountRequest(null,null,null);

        response = serverFacade.getFollowCount(request,urlPath);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void getFollowerCountException(){
        String exception = "";
        urlPath = "/nopath";
        request = new GetCountRequest(authToken,user,"follower");

        try{
            response = serverFacade.getFollowCount(request,urlPath);
        }catch (Exception ex){
            exception = ex.getMessage();
            Assertions.assertEquals("An unknown error occurred. Response code = 403", exception);
        }
    }

}
