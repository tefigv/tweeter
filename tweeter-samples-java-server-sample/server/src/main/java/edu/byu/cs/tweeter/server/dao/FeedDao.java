package edu.byu.cs.tweeter.server.dao;

import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.bean.DataPage;
import edu.byu.cs.tweeter.server.dao.bean.Status;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
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

public class FeedDao extends IStatusDAO {

    private static final String tableName = "feed-table";

    private Key getKey(String alias, long timestamp_num) {
        table = enhancedClient.table(tableName, TableSchema.fromBean(Status.class));
        Key key = Key.builder().partitionValue(alias).sortValue(timestamp_num).build();
        return key;
    }

    public void putItem(String alias, long timestamp_num, String urls, String mentions,
                        String status_text, String timestamp_string, String user_name) {

        Key key = getKey(alias, timestamp_num);

        Status status = table.getItem(key);

        status.setAlias(alias);
        status.setTimestamp_num(timestamp_num);
        status.setUrls(urls);
        status.setMentions(mentions);
        status.setStatusText(status_text);
        status.setTimestamp_string(timestamp_string);
        status.setName_name(user_name);
        table.putItem(status);
    }

    public Status getItem(String alias, long timestamp_num) {
        Key key = getKey(alias, timestamp_num);
        Status status = table.getItem(key);
        return status;
    }

    public void deleteItem(String alias, long timestamp_num) {
        Key key = getKey(alias, timestamp_num);
        table.deleteItem(key);
    }

    public Key getPagedKey(String alias) {
        table = enhancedClient.table(tableName, TableSchema.fromBean(Status.class));
        Key key = Key.builder().partitionValue(alias).build();
        return key;
    }
}
