package edu.byu.cs.tweeter.client.presentors;

import java.util.List;

import edu.byu.cs.tweeter.client.model.services.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class FollowPresenter extends PagedPresenter<User> implements FollowService.GetFollowObserver {

    //User lastUser = (User) lastItem;

    public FollowPresenter(View view, User user) {
        super(view, user);
    }

    @Override
    public void successfulGetUser(User user) {
        view.openMainView(user);
    }


    @Override
    public void successfulGetFollow(List<User> follow, boolean hasMorePages) {
        isLoading = false;
        lastItem = (follow.size() > 0 ? follow.get(follow.size()-1) : null);
        this.hasMorePages = hasMorePages;
        pagedView.endingLoad();
        pagedView.addItems(follow);
    }


    @Override
    public void handleFailure(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void handleException(Exception exception) {
        view.showErrorMessage("Failed to get user's profile because of exception: " + exception.getMessage());
    }

    @Override
    public abstract void startService();
}
