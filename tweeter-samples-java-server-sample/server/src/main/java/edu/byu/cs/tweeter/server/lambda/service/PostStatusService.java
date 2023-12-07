package edu.byu.cs.tweeter.server.lambda.service;

import com.google.gson.Gson;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class PostStatusService extends AuthenticatedService {

    public PostStatusResponse postStatus(PostStatusRequest request){
        if(request.getStatus() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a status object");
        }else if(request.getAuthToken() == null){
            throw new RuntimeException("[Bad Request] Request needs to have an token");
        }

        if(validateToken(request.getAuthToken().getToken())) {
            String timestamp_string = convertTimestampToString(request.getStatus().getTimestamp());

            Gson gson = new Gson();
            String urls = gson.toJson(request.getStatus().getUrls());
            String mentions = gson.toJson(request.getStatus().getMentions());

           storyDao.putItem(request.getStatus().getUser().getAlias(),
                   request.getStatus().getTimestamp(),urls, mentions,
                   request.getStatus().getPost(),timestamp_string, request.getStatus().getUser().getName());
        }
        return new PostStatusResponse();
    }

    public static String convertTimestampToString(long timestamp) {
        Instant instant_timestamp = Instant.ofEpochMilli(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(instant_timestamp);
    }
}
