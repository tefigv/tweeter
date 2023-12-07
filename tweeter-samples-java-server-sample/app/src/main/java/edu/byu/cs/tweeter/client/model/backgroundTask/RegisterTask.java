package edu.byu.cs.tweeter.client.model.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.services.UserService;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask {

    /**
     * The user's first name.
     */
    private final String firstName;

    /**
     * The user's last name.
     */
    private final String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private final String image;
    private static final String LOG_TAG = "LoginTask";

    public RegisterTask(UserService userService,String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {
        super(messageHandler, username, password);

        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected final void runTask() throws IOException {
        try{
            RegisterRequest request = new RegisterRequest(firstName, lastName, image, username, password);
            RegisterResponse response = getServerFacade().register(request, UserService.REGISTER_LOGIN);
            if (response.isSuccess()) {
                this.authenticatedUser = response.getUser();
                this.authToken = response.getAuthToken();
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }
}
