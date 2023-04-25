package com.itguigu.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author darren
 * @create 2023-04-20 11:38
 */
public class ESTest_Index {

    private RestHighLevelClient client;

    @Before
    public void connection(){
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        client = new RestHighLevelClient(RestClient.builder(httpHost));
    }

    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    public void create_index() throws IOException {

        // 创建索引 - 请求对象
        CreateIndexRequest user0 = new CreateIndexRequest("user0");

        // 发送请求，获取响应
        CreateIndexResponse response = client.indices().create(user0, RequestOptions.DEFAULT);

        boolean acknowledged = response.isAcknowledged();
        System.out.println("acknowledged:"+acknowledged);


    }

    /**
     * 查询索引
     */
    @Test
    public void select_index() throws IOException {
        GetIndexRequest user0 = new GetIndexRequest("user0");
        GetIndexResponse response = client.indices().get(user0, RequestOptions.DEFAULT);

        System.out.println("aliases:"+response.getAliases());
        System.out.println("mappings:"+response.getMappings());
        System.out.println("settings:"+response.getSettings());

    }

    @Test
    public void delete_index() throws IOException {
        DeleteIndexRequest user0 = new DeleteIndexRequest("user0");
        AcknowledgedResponse response = client.indices().delete(user0, RequestOptions.DEFAULT);
        System.out.println("删除结果："+response.isAcknowledged());
    }

    @After
    public void close_connection() throws IOException {
        client.close();
    }
}
