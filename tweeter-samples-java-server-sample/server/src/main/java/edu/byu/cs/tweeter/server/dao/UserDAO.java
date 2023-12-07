package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.bean.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class UserDAO extends DynamoDAO implements IUserDAO {
    private static final String tableName = "user-table";

    DynamoDbTable<User> table;
    private Key getKey(String alias){
        table = enhancedClient.table(tableName, TableSchema.fromBean(User.class));
        return Key.builder().partitionValue(alias).build();
    }

    public void putItem(String firstName, String lastName, String alias, String password, int follower_count, int followee_count, String image){
        Key key = getKey(alias);
        User user = table.getItem(key);
        if(user != null){
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setFollowees_count(followee_count);
            user.setFollowers_count(follower_count);
            user.setPassword(password);
            user.setImage(image);
            table.updateItem(user);
        }else{
            User newUser = new User(firstName, lastName,alias,password,followee_count,follower_count,image);
            table.updateItem(newUser);
        }
    }

    public User getItem(String alias){
        Key key = getKey(alias);
        User user = table.getItem(key);
        return user;
    }

    public void deleteItem(String alias){
        Key key = getKey(alias);
        table.deleteItem(key);
    }

}
