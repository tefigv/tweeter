package edu.byu.cs.tweeter.client.model.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.services.MainService;
import edu.byu.cs.tweeter.client.model.services.ServiceObserver;

public class MessageHandler extends BackgroundTaskHandler<MainService.MainActivityObserver> {

    String message;

    public MessageHandler(MainService.MainActivityObserver observer, String message) {
        super(observer);
        this.message = message;
    }


    @Override
    protected void handleSuccessMessage(MainService.MainActivityObserver observer, Bundle data) {
        String request;

        if (message.equalsIgnoreCase("Successfully logged out")){
            request = "logout";
        }else{
            request = "status";
        }
        observer.successMessage(message, request);
    }
}
