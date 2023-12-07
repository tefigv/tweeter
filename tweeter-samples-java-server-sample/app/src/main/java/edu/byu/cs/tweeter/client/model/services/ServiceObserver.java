package edu.byu.cs.tweeter.client.model.services;

public interface ServiceObserver {
    void handleFailure(String message);
    void handleException(Exception exception);
}
