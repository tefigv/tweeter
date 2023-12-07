package edu.byu.cs.tweeter.server.dao;

import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.bean.DataPage;
import edu.byu.cs.tweeter.server.dao.bean.Status;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public abstract class IStatusDAO extends DynamoDAO {

    protected DynamoDbTable<Status> table;
    public abstract void putItem(String alias, long timestamp_num, String urls, String mentions, String status_text,String timestamp_string,String user_name);
    public abstract Status getItem(String alias, long timestamp_num);
    public abstract void deleteItem(String alias, long timestamp_item);

    public abstract Key getPagedKey(String alias);
    public DataPage<Status> getPageOfStatus (String targetAlias, int pageSize, Status lastStatus){

        Key key = getPagedKey(targetAlias);

        QueryEnhancedRequest.Builder requestBuiler = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key)).limit(pageSize);

        if(lastStatus != null){
            Map<String, AttributeValue> startKey = new HashMap<>();
            AttributeValue lastStatusAttr = AttributeValue.builder().s(lastStatus.getTimestamp_string()).build();
            startKey.put(targetAlias, lastStatusAttr);
        }

        QueryEnhancedRequest request = requestBuiler.build();
        DataPage<Status> result = new DataPage<>();

        PageIterable<Status> pages = table.query(request);

        pages.stream().limit(1).forEach(( Page<Status> page) ->{
            result.setHasMorePages(page.lastEvaluatedKey() != null);
            page.items().forEach(status -> result.getValues().add(status));
        });
        return result;
    }
}
