package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.bean.DataPage;
import edu.byu.cs.tweeter.server.dao.bean.Follows;

public interface IFollowDAO {
    void putItem(String follower_handle, String followee_handle, String follower_name,String followee_name);
    Follows getItem(String follower, String followee);
    boolean isItemInTable(String follower,String followee);
    void deleteItem(String follower,String followee);
    DataPage<Follows> getPageOfFollowers(String targetUserAlias, int pageSize, String lastUserAlias);
    DataPage<Follows>  getPageOfFollowees(String targetUserAlias, int pageSize, String lastUserAlias);
}
