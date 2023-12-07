package edu.byu.cs.tweeter.server.dao.bean;

import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class AuthToken {

    private long timestamp;
    private String token;

    public AuthToken(long timestamp, String token) {
        this.timestamp = timestamp;
        this.token = token;
    }

    private AuthToken(){};

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    @DynamoDbPartitionKey
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return "AuthToken{"+
                "token='" + token + '\'' +
                ", timestamp='" + timestamp + '}';
    }


}
