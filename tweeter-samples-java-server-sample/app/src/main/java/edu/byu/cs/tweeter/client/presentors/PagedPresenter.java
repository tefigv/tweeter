package edu.byu.cs.tweeter.client.presentors;

import java.util.List;

import edu.byu.cs.tweeter.client.model.services.GetUserObserver;
import edu.byu.cs.tweeter.client.model.services.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends Presenter implements GetUserObserver {
    public static final int PAGE_SIZE = 10;

    public interface PagedView<T> extends  View{
        void startingLoad();
        void endingLoad();

        void addItems(List<T> items);
    }

    protected T lastItem;

    protected PagedView pagedView = (PagedView) view;

    protected final User user;
    protected boolean hasMorePages;
    protected boolean isLoading = false;

    public PagedPresenter(View view,User user) {
        super(view);
        this.user = user;
    }

    public void getUser(AuthToken authToken, String alias) {
        UserService userService = new UserService();
        userService.getUser(authToken, alias, this);
        view.showInfoMessage("Getting user's profile...");
    }

    /**
     * Causes the Adapter to display a loading footer and make a request to get more following
     * data.
     */
    public void loadMoreItems() {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            pagedView.startingLoad();

            startService();

        }
    }

    public T getLastItem() {
        return lastItem;
    }

    public void setLastItem(T lastItem) {
        this.lastItem = lastItem;
    }

    public abstract void startService();

    public boolean isLoading() {
        return isLoading;
    }
    public boolean isHasMorePages() {
        return hasMorePages;
    }
}
