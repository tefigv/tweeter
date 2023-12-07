package edu.byu.cs.tweeter.client.model.services;

import edu.byu.cs.tweeter.client.model.backgroundTask.*;
import edu.byu.cs.tweeter.client.model.handlers.AuthenticateHandler;
import edu.byu.cs.tweeter.client.model.handlers.GetUserHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UserService {

    public static final String LOGIN_URL = "/login";
    public static final String REGISTER_LOGIN = "/register";
    public static final String GET_USER = "/getuser";


    public void login(String username, String password, AuthenticateObserver observer) {

        //AuthenticateHandler authenticateHandler = new AuthenticateHandler(observer);
        LoginTask loginTask = getLoginTask(username, password, observer);
        BackgroundTaskUtils.runTask(loginTask);
    }

    LoginTask getLoginTask(String username, String password, AuthenticateObserver observer) {
        return new LoginTask(this, username, password, new AuthenticateHandler(observer));
    }

    public void register(String firstname, String lastname, String alias, String password, String image, AuthenticateObserver observer) {

        AuthenticateHandler authenticateHandler = new AuthenticateHandler(observer);
        // Send register request.
        RegisterTask registerTask = new RegisterTask( this, firstname, lastname,
                alias, password, image, authenticateHandler);

        BackgroundTaskUtils.runTask(registerTask);

    }
    public void getUser(AuthToken authToken, String alias, GetUserObserver observer) {

        GetUserHandler getUserHandler = new GetUserHandler(observer);

        GetUserTask getUserTask = new GetUserTask(authToken,
                alias, getUserHandler);
        BackgroundTaskUtils.runTask(getUserTask);
    }
}
