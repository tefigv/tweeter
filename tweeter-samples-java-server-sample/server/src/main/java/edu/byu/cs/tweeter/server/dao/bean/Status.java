package edu.byu.cs.tweeter.server.dao.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

public class Status {
 private String alias;
 private long timestamp_num;
 private String urls;
 private String mentions;
 private String statusText;
 private String  timestamp_string;
 private String name_name;

 private Status(){}

    public Status(String alias, long timestamp_num, String urls, String mentions, String statusText, String timestamp_string, String name_name) {
        this.alias = alias;
        this.timestamp_num = timestamp_num;
        this.urls = urls;
        this.mentions = mentions;
        this.statusText = statusText;
        this.timestamp_string = timestamp_string;
        this.name_name = name_name;
    }

    @DynamoDbPartitionKey
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @DynamoDbSortKey
    public long getTimestamp_num() {
        return timestamp_num;
    }

    public void setTimestamp_num(long timestamp_num) {
        this.timestamp_num = timestamp_num;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getTimestamp_string() {
        return timestamp_string;
    }

    public void setTimestamp_string(String timestamp_string) {
        this.timestamp_string = timestamp_string;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getMentions() {
        return mentions;
    }

    public void setMentions(String mentions) {
        this.mentions = mentions;
    }

    public String getName_name() {
        return name_name;
    }

    public void setName_name(String name_name) {
        this.name_name = name_name;
    }

    @Override
    public String toString(){
        return "Status{" +
                ", alias=" + alias +
                ", timestamp_num=" + timestamp_num +
                ", urls=" + urls +
                ", mentions=" + mentions +
                "statusText='" + statusText + '\'' +
                ", timestamp_string=" + timestamp_string +
                ", name_name=" + name_name +
                '}';

    }
}
