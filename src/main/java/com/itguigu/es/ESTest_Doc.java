package com.itguigu.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itguigu.es.pojo.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author darren
 * @create 2023-04-20 11:38
 */
public class ESTest_Doc {

    private RestHighLevelClient client;

    @Before
    public void connection(){
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        client = new RestHighLevelClient(RestClient.builder(httpHost));
    }

    /**
     * 创建文档
     * @throws IOException
     */
    @Test
    public void create_doc() throws IOException {
        IndexRequest request = new IndexRequest();
        request.index("user1").id("1001");

        User user = new User();
        user.setUser("张三");
        user.setAge(21);
        user.setSex("男");
        String userJson = new ObjectMapper().writeValueAsString(user);
        request.source(userJson, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println(response.getResult());
    }

    /**
     * 查询文档
     */
    @Test
    public void select_doc() throws IOException {
        GetRequest request = new GetRequest().index("user1").id("1001");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        System.out.println(response.getIndex());
        System.out.println(response.getType());
        System.out.println(response.getId());
        System.out.println(response.getSourceAsString());
    }

    /**
     * 修改文档
     */
    @Test
    public void update_doc() throws IOException {
        UpdateRequest request = new UpdateRequest();

        request.index("user1").id("1001");

        request.doc(XContentType.JSON,"sex","女");

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);

        System.out.println(response.getId());
        System.out.println(response.getIndex());
        System.out.println(response.getResult());
    }

    /**
     * 删除文档
     * @throws IOException
     */
    @Test
    public void delete_doc() throws IOException {
        DeleteRequest request = new DeleteRequest().index("user1").id("1001");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

    }

    /**
     * 批量添加
     * @throws IOException
     */
    @Test
    public void create_doc_add() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user1").id("1002")
                .source(XContentType.JSON, "name", "zhangsan"));

        request.add(new IndexRequest().index("user1").id("1003")
                .source(XContentType.JSON, "name", "lisi"));

        request.add(new IndexRequest().index("user1").id("1004")
                .source(XContentType.JSON, "name", "wangwu"));

        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);

        System.out.println(responses.getTook());
        System.out.println(responses.getItems());

    }

    @After
    public void close_connection() throws IOException {
        client.close();
    }
}
