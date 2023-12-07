package edu.byu.cs.tweeter.client.presentors;

import edu.byu.cs.tweeter.model.domain.User;

public class Presenter {

    public interface View{
        void showInfoMessage(String message);
        void showErrorMessage(String message);
        void openMainView(User user);
    }

    protected View view;

    public Presenter(View view) {
        this.view = view;
    }


}
