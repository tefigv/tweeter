package edu.byu.cs.tweeter.client.presentors;

import edu.byu.cs.tweeter.client.model.services.AuthenticateObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticatePresenter extends Presenter implements AuthenticateObserver {



    public AuthenticatePresenter(View view) {
        super(view);
    }



    @Override
    public void handleFailure(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void handleException(Exception exception) {
        view.showErrorMessage("Failed to access account because of exception: " +  exception.getMessage());
    }

    public interface AuthenticationView extends View  {

        void hideInfoMessage();

        void hideErrorMessage();

    }

    protected AuthenticationView authenticationView = (AuthenticationView) view;

    @Override
    public void successfulAuthentication(AuthToken authToken, User user) {
        authenticationView.hideErrorMessage();
        authenticationView.hideInfoMessage();
        view.showInfoMessage("Hello " + user.getName());
        view.openMainView(user);
    }
}
