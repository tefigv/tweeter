package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.Status;

public class FeedResponse extends PagedResponse {
    private List<Status> feed;

    public FeedResponse (String message){super(false, message, false);}
    public FeedResponse(List<Status> feed, boolean hasMorePages){
        super(true,hasMorePages);
        this.feed = feed;
    }

    public List<Status> getFeed() {
        return feed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedResponse response = (FeedResponse) o;
        return (Objects.equals(feed,response.feed) &&
                Objects.equals(this.getMessage(), response.getMessage()) &&
                this.isSuccess() == response.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(feed);
    }
}
