package edu.byu.cs.tweeter.server.lambda.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.IAuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.IStatusDAO;
import edu.byu.cs.tweeter.server.dao.IUserDAO;
import edu.byu.cs.tweeter.server.dao.bean.DataPage;
import edu.byu.cs.tweeter.server.factory.DaoFactory;
import edu.byu.cs.tweeter.util.FakeData;

public class StatusService extends AuthenticatedService {

    public FeedResponse getFeed(FeedRequest request){
        if(request.getUserAlias() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        }else if(request.getLimit() <= 0){
            throw new RuntimeException("[Bad Request] needs to have a positive limit");
        }

        if(validateToken(request.getAuthToken().getToken())) {
            String timestamp_string = convertTimestampToString(request.getLastStatus().getTimestamp());

            Gson gson = new Gson();
            String urls = gson.toJson(request.getLastStatus().getUrls());
            String mentions = gson.toJson(request.getLastStatus().getMentions());

            edu.byu.cs.tweeter.server.dao.bean.Status status = new edu.byu.cs.tweeter.server.dao.bean.Status(request.getUserAlias(),
                    request.getLastStatus().getTimestamp(), urls, mentions,
                    request.getLastStatus().getPost(), timestamp_string, request.getLastStatus().getUser().getName());

            DataPage<edu.byu.cs.tweeter.server.dao.bean.Status> dataPage = feedDao.getPageOfStatus(request.getUserAlias(), request.getLimit(), status);


            return new FeedResponse(getList(dataPage), dataPage.isHasMorePages());
        }else{
            throw new RuntimeException("[Bad Request] autToken not valid");
        }
    }

    private List<Status> getList(DataPage<edu.byu.cs.tweeter.server.dao.bean.Status> dataPage){
        List<Status> statuses = new ArrayList<>();

        for(int i = 0; i < dataPage.getValues().size(); i++){

            String alias = dataPage.getValues().get(i).getAlias();
            edu.byu.cs.tweeter.server.dao.bean.User daoUser = userDAO.getItem(alias);
            User user = new User(daoUser.getFirstName(),daoUser.getLastName(),daoUser.getAlias(), daoUser.getImage());

            Gson gson = new Gson();
            List<String> urls = gson.fromJson(dataPage.getValues().get(i).getUrls(),
                    new TypeToken<List<String>>(){}.getType());
            List<String> mentions = gson.fromJson(dataPage.getValues().get(i).getMentions(),
                    new TypeToken<List<String>>(){}.getType());

            Status status = new Status(dataPage.getValues().get(i).getStatusText(),user,
                    dataPage.getValues().get(i).getTimestamp_num(), urls,mentions);
            statuses.add(status);
        }
        return statuses;
    }

    public static String convertTimestampToString(long timestamp) {
        Instant instant_timestamp = Instant.ofEpochMilli(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(instant_timestamp);
    }

    public StoryResponse getStory(StoryRequest request){
        if(request.getUserAlias() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        }else if(request.getLimit() <= 0){
            throw new RuntimeException("[Bad Request] needs to have a positive limit");
        }
        if(validateToken(request.getAuthToken().getToken())) {
            String timestamp_string = convertTimestampToString(request.getLastStatus().getTimestamp());

            Gson gson = new Gson();
            String urls = gson.toJson(request.getLastStatus().getUrls());
            String mentions = gson.toJson(request.getLastStatus().getMentions());

            edu.byu.cs.tweeter.server.dao.bean.Status status = new edu.byu.cs.tweeter.server.dao.bean.Status(request.getUserAlias(),
                    request.getLastStatus().getTimestamp(), urls, mentions,
                    request.getLastStatus().getPost(), timestamp_string, request.getLastStatus().getUser().getName());

            DataPage<edu.byu.cs.tweeter.server.dao.bean.Status> dataPage = storyDao.getPageOfStatus(request.getUserAlias(), request.getLimit(), status);

            return new StoryResponse(getList(dataPage), dataPage.isHasMorePages());
        }else{
            throw new RuntimeException("[Bad Request] autToken not valid");
        }
    }
}
