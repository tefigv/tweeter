package edu.byu.cs.tweeter.client.presentors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.services.MainService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends Presenter implements MainService.MainActivityObserver {

    public interface MainView extends View {
        void followerButtonColors(boolean isFollower);
        void unfollowOrFollowUpdate(boolean followButtonUpdate);
        void enableFollow(boolean enable);
        void updateFollowingCount(int count);
        void updateFollowersCount(int count);
        void logout();
        void post(String message);
    }

    MainView mainView = (MainView) view;
    MainService mainService = new MainService();
    User user;

    public MainPresenter(View view, User user){
        super(view);
        this.user = user;
    }

    public void isFollower(AuthToken authToken, User follower, User followee){
        mainService.isFollower(authToken,follower,followee,this);

    }

    public void unfollow(AuthToken authToken, User follower, User selectedUser){
        mainService.unfollow(authToken,follower,selectedUser,this);
        view.showInfoMessage("Removing " + selectedUser.getName() + "...");
    }

    public void followersCount(AuthToken authToken, User selectedUser){
        mainService.followersCount(authToken,selectedUser,this);
    }

    public void followingCount(AuthToken authToken, User selectedUser){
        mainService.followingCount(authToken,selectedUser,this);
    }

    public void follow(AuthToken authToken, User follower, User selectedUser){
        mainService.follow(authToken, follower,selectedUser,this);
        view.showInfoMessage("Adding " + selectedUser.getName() + "...");
    }

    public void logout(AuthToken authToken){
        mainService.logout(authToken,this);
        view.showInfoMessage("Logging Out...");
    }

    public void postStatus(User user,String post,AuthToken authToken){
        view.showInfoMessage("Posting Status...");
        try {
            Instant instant = Instant.now();

            Status newStatus = new Status(post, user, instant.toEpochMilli(), parseURLs(post), parseMentions(post));

            MainService mainService1 = this.returnMainService();
            mainService1.status(authToken,newStatus,this);
        } catch (Exception ex) {
            view.showErrorMessage("Failed to post the status because of exception: " + ex.getMessage());
        }

    }

    protected MainService returnMainService(){
        return new MainService();
    }



    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }
    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    @Override
    public void successfulIsFollower(boolean isFollower) {
        mainView.followerButtonColors(isFollower);
    }

    @Override
    public void successFollowButton(boolean followButtonUpdate) {
            mainView.unfollowOrFollowUpdate(followButtonUpdate);
    }

    @Override
    public void enableFollowButton(boolean doEnable) {
        mainView.enableFollow(doEnable);
    }

    @Override
    public void getCountSuccess(int count) {
        mainView.updateFollowersCount(count);
        mainView.updateFollowingCount(count);
    }


    @Override
    public void successMessage(String message, String request) {
        if(request.equalsIgnoreCase("logout")){
            mainView.logout();
        }else{
            mainView.post(message);
        }
    }
    @Override
    public void handleFailure(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void handleException(Exception exception) {
        view.showErrorMessage("Fail to get item because of exception: "+ exception.getMessage());
    }
}
