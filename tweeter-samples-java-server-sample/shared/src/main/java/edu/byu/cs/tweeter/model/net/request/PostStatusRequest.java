package edu.byu.cs.tweeter.model.net.request;

import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusRequest {
    AuthToken authToken;
    Status status;

    private PostStatusRequest(){}

    public PostStatusRequest(AuthToken authToken, Status status) {
        this.authToken = authToken;
        this.status = status;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostStatusRequest that = (PostStatusRequest) o;
        return authToken.equals(that.authToken) && status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, status);
    }
}
