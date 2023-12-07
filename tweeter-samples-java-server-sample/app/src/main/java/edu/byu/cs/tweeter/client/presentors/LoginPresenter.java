package edu.byu.cs.tweeter.client.presentors;

import edu.byu.cs.tweeter.client.model.services.AuthenticateObserver;
import edu.byu.cs.tweeter.client.model.services.UserService;

public class LoginPresenter extends AuthenticatePresenter implements AuthenticateObserver {

    public LoginPresenter(View view) {
        super(view);
    }

    public void login(String alias, String password) {

        if (validateLogin(alias, password)) {
            authenticationView.hideErrorMessage();

            authenticationView.showInfoMessage("Login In...");

            var userService = new UserService();
            userService.login(alias, password, this);
        }
    }

    public boolean validateLogin(String alias, String password) {
        if (alias.length() > 0 && alias.charAt(0) != '@') {
            view.showErrorMessage("Alias must begin with @.");
            return false;
        }
        if (alias.length() < 2) {
            view.showErrorMessage("Alias must contain 1 or more characters after the @.");
            return false;
        }
        if (password.length() == 0) {
            view.showErrorMessage("Password cannot be empty.");
            return false;
        }

        return true;
    }
}
