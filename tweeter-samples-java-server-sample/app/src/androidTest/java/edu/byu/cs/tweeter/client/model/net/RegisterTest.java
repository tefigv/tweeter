package edu.byu.cs.tweeter.client.model.net;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class RegisterTest {
    static ServerFacade serverFacade;
    RegisterRequest request;
    String urlPath;

    RegisterResponse response;

    @BeforeAll
    static void beforeAll() {
        serverFacade = new ServerFacade();
    }

    @Test
    void registerSuccess() {
        urlPath = "/register";
        request = new RegisterRequest("Sonia", "Snow", "image", "@sonia", "soniasnow");
        User user = new User("Allen", "Anderson","@allen", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        try {
            response = serverFacade.register(request, urlPath);
        }catch (IOException | TweeterRemoteException ex){
            System.out.println(ex.getMessage());
        }
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(user,response.getUser());
    }

    @Test
    void registerFail() throws IOException, TweeterRemoteException {
        urlPath = "/register";
        request = new RegisterRequest("Allen", "Anderson", "image",null, "hello");
        String message = "[Bad Request] Missing a username";

        response = serverFacade.register(request, urlPath);


        Assertions.assertFalse(response.isSuccess());
        //Assertions.assertEquals(message, response.getMessage());
    }

    @Test
    void registerException(){
        String exception = "";
        urlPath = "/nopath";
        request = new RegisterRequest("Sonia", "Snow", "image", "@sonia", "soniasnow");
        try{
            response = serverFacade.register(request, urlPath);
        }catch (Exception ex){
            exception = ex.getMessage();
            //Assertions.assertFalse(response.isSuccess());
            Assertions.assertEquals("An unknown error occurred. Response code = 403", exception);
        }
    }






}
