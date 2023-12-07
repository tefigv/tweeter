package edu.byu.cs.tweeter.server.factory;

import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FeedDao;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.IAuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.IFollowDAO;
import edu.byu.cs.tweeter.server.dao.IImageDAO;
import edu.byu.cs.tweeter.server.dao.IStatusDAO;
import edu.byu.cs.tweeter.server.dao.IUserDAO;
import edu.byu.cs.tweeter.server.dao.ImageDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class DaoFactory {
    private static DaoFactory instance;

    public static DaoFactory getInstance() {
        return instance;
    }

    public static void setInstance(DaoFactory instance) {
        DaoFactory.instance = instance;
    }

    public IAuthTokenDAO AuthTokenDAO(){
        return new AuthTokenDAO();
    }
    public IFollowDAO FollowDAO(){
        return new FollowDAO();
    }
    public IImageDAO ImageDAO(){
        return new ImageDAO();
    }
    public IStatusDAO StoryDAO(){
        return new StatusDAO();
    }
    public IStatusDAO FeedDAO(){
        return new FeedDao();
    }
    public IUserDAO UserDAO(){
        return new UserDAO();
    }

}
