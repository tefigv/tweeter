package edu.byu.cs.tweeter.server.lambda.service;

import com.google.gson.Gson;

import edu.byu.cs.tweeter.server.dao.*;
import edu.byu.cs.tweeter.server.dao.bean.*;
import edu.byu.cs.tweeter.server.factory.DaoFactory;

public class AuthenticatedService extends Service {

    protected DaoFactory daoFactory = getFactoryInstance();
    protected IAuthTokenDAO authTokenDAO = daoFactory.AuthTokenDAO();
    protected IImageDAO imageDAO = daoFactory.ImageDAO();
    protected IFollowDAO followDAO = daoFactory.FollowDAO();
    protected IStatusDAO storyDao = daoFactory.StoryDAO();
    protected IStatusDAO feedDao = daoFactory.FeedDAO();
    protected IUserDAO userDAO = daoFactory.UserDAO();

    protected boolean validateToken(String token){
        return authTokenDAO.isTokenInTable(token);
    }

}
