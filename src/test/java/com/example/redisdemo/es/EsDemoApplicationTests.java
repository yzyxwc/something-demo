package com.example.redisdemo.es;

import com.example.redisdemo.RedisDemoApplication;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisDemoApplication.class)
public class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * @Description:创建索引，会根据Item类的@Document注解信息来创建
     * @Author: https://blog.csdn.net/chen_2890
     * @Date: 2018/9/29 0:51
     */
    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(Item.class);
    }
    public void test(){
//        SearchResponse searchResponse = client.prepareSearch("test1").setTypes("user1")
//                .setQuery(QueryBuilders.fuzzyQuery("name", "yb"))
//                .get();
        QueryBuilder term = QueryBuilders
                .multiMatchQuery("ceshi","question")
                .operator(MatchQueryBuilder.Operator.OR)
                .minimumShouldMatch("80%");
    }
}

