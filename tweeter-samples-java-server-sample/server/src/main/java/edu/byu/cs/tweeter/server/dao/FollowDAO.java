package edu.byu.cs.tweeter.server.dao;

import java.util.HashMap;
import java.util.Map;


import edu.byu.cs.tweeter.server.dao.bean.DataPage;
import edu.byu.cs.tweeter.server.dao.bean.Follows;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowDAO extends DynamoDAO implements IFollowDAO{

    private static final String TableName = "follows";
    public static final String IndexName = "follows_index";
    private static final String Follower = "follower_handle";
    private static final String Followee = "followee_handle";

    private static boolean isNonEmptyString(String follower) {
        return (follower != null && !follower.isEmpty());
    }
    DynamoDbTable<Follows> table;
    private Key getKey(String follower_handle, String followee_handle){
        table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
        return Key.builder()
                .partitionValue(follower_handle).sortValue(followee_handle)
                .build();
    }

    public void putItem(String follower_handle, String followee_handle, String follower_name, String followee_name){
        Key key = getKey(follower_handle,followee_handle);
        Follows follows = table.getItem(key);
        if(follows != null){
            follows.setFollower_name(follower_name);
            follows.setFollowee_name(followee_name);
            table.updateItem(follows);
        }else{
            Follows newFollows = new Follows(follower_handle,followee_handle,follower_name,followee_name);
            table.putItem(newFollows);
        }


    }
    public Follows getItem(String follower,String followee){
        Key key = getKey(follower,followee);
        Follows follows = table.getItem(key);
        return follows;
    }

    public boolean isItemInTable(String follower,String followee){
        Key key = getKey(follower,followee);
        Follows follows = table.getItem(key);
        return follows != null;
    }

    public void deleteItem(String follower,String followee) {
        Key key = getKey(follower,followee);
        table.deleteItem(key);
    }


    public DataPage<Follows> getPageOfFollowers(String targetUserAlias, int pageSize, String lastUserAlias) {

        table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
        Key key = Key.builder().partitionValue(targetUserAlias).build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize).scanIndexForward(true);

        if(isNonEmptyString(lastUserAlias)) {
            // Build up the Exclusive Start Key (telling DynamoDB where you left off reading items)
            Map<String, AttributeValue> startKey = new HashMap<>();

            AttributeValue lastUserAttr = AttributeValue.builder().s(targetUserAlias).build();
            AttributeValue FolloweeAttr = AttributeValue.builder().s(lastUserAlias).build();

            startKey.put(Follower, lastUserAttr);
            startKey.put(Followee, FolloweeAttr);

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();
        DataPage<Follows> result = new DataPage<>();
        PageIterable<Follows> pages = table.query(request);

        pages.stream().limit(1).forEach((Page<Follows> page) -> {
                    result.setHasMorePages(page.lastEvaluatedKey() != null);
                    page.items().forEach(follows -> result.getValues().add(follows));
                });

        return result;
    }


    public DataPage<Follows>  getPageOfFollowees(String targetUserAlias, int pageSize, String lastUserAlias) {
        DynamoDbIndex<Follows> index = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class)).index(IndexName);
        Key key = Key.builder()
                .partitionValue(targetUserAlias)
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize);

        if(isNonEmptyString(lastUserAlias)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(Followee, AttributeValue.builder().s(targetUserAlias).build());
            startKey.put(Follower, AttributeValue.builder().s(lastUserAlias).build());

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();

        DataPage<Follows> result = new DataPage<>();

        SdkIterable<Page<Follows>> sdkIterable = index.query(request);
        PageIterable<Follows> pages = PageIterable.create(sdkIterable);
        pages.stream()
                .limit(1)
                .forEach((Page<Follows> page) -> {
                    result.setHasMorePages(page.lastEvaluatedKey() != null);
                    page.items().forEach(follows -> result.getValues().add(follows));
                });

        return result;
    }

}
