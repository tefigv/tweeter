package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.bean.AuthToken;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AuthTokenDAO extends DynamoDAO implements IAuthTokenDAO {

    private static final String tableName = "authtoken-table";

    private DynamoDbTable<AuthToken> table;
    private Key getKey(String token){
        table = enhancedClient.table(tableName, TableSchema.fromBean(AuthToken.class));
        Key key = Key.builder().partitionValue(token)
                .build();
        return key;
    }


    public void putItem(String token, long timestamp){
        Key key = getKey(token);
        AuthToken authToken = table.getItem(key);
        if(authToken != null){
            authToken.setToken(token);
            authToken.setTimestamp(timestamp);
            table.updateItem(authToken);
        }else{
            AuthToken newAuthToken = new AuthToken(timestamp,token);
            table.putItem(newAuthToken);
        }
    }

    public AuthToken getItem(String token){
        Key key = getKey(token);
        AuthToken authToken = table.getItem(key);

        return authToken;
    }

    public boolean isTokenInTable(String token){
        Key key = getKey(token);
        AuthToken authToken = table.getItem(key);

        return authToken != null;
    }

    public void deleteItem(String token){
        Key key = getKey(token);
        table.deleteItem(key);
    }

}
