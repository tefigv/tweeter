package edu.byu.cs.tweeter.server.lambda.service;

import edu.byu.cs.tweeter.server.factory.DaoFactory;

public class Service {
    protected static DaoFactory getFactoryInstance(){
       DaoFactory.setInstance(new DaoFactory());
       return DaoFactory.getInstance();
    }


}
