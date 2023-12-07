package edu.byu.cs.tweeter.model.net.response;

public class GetCountResponse extends Response{
    int count;

    public GetCountResponse(String message){super(false,message);}

    public GetCountResponse(int count){
        super(true);
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
