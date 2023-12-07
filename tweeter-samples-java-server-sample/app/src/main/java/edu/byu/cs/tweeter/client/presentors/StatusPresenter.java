package edu.byu.cs.tweeter.client.presentors;

import java.util.List;

import edu.byu.cs.tweeter.client.model.services.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class StatusPresenter  extends PagedPresenter<Status>  implements StatusService.GetStatusObserver{
    public StatusPresenter(View view, User user) {
        super(view, user);
    }

    @Override
    public void successfulGetUser(User user) {
        view.openMainView(user);
    }

    @Override
    public void handleFailure(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void handleException(Exception exception) {
        view.showErrorMessage("Failed to get item because of exception: " + exception.getMessage());
    }

    @Override
    public void successfulGetStatus(List<Status> status, boolean hasMorePages) {
        isLoading = false;
        lastItem = (Status)(status.size() > 0  ? status.get(status.size()-1) : null);
        this.hasMorePages = hasMorePages;
        pagedView.endingLoad();
        pagedView.addItems(status);
    }

    @Override
    public abstract void startService();
}
