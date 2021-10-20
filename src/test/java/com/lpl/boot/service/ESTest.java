package com.lpl.boot.service;

import com.alibaba.fastjson.JSON;
import com.lpl.boot.domain.repository.ArticleRepository;
import com.lpl.boot.model.es.Article;
import com.lpl.boot.model.es.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import java.util.Arrays;
import static java.util.Arrays.asList;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;


@SpringBootTest
public class ESTest {

    @Autowired
    private ArticleRepository articleRepository;
    //新增
    @Test
    public void save(){
        Article article = new Article("Spring Data Elasticsearch");
        article.setAuthors(asList(new Author("god"),new Author("John")));
        articleRepository.save(article);

        article = new Article("Spring Data Elasticsearch2");
        article.setAuthors(asList(new Author("god"),new Author("King")));
        articleRepository.save(article);

        article = new Article("Spring Data Elasticsearch3");
        article.setAuthors(asList(new Author("god"),new Author("Bill")));
        articleRepository.save(article);
    }
    @Test
    public void queryAuthorName()  {
        Page<Article> articles = articleRepository.findByAuthorsName("lpl", PageRequest.of(0,10));
        for (Article article : articles.getContent()) {
            System.out.println(article);
            for (Author author : article.getAuthors()) {
                System.out.println(author);
            }
        }
    }
    @Test
    public void update() {
        Page<Article> articles = articleRepository.findByTitle("Spring Data Elasticsearch",PageRequest.of(0,10));

        Article article = articles.getContent().get(0);
        System.out.println(article);
        System.out.println(article.getAuthors().get(0));
        Author author = new Author("lpl");
        article.setAuthors(Arrays.asList(author));
        articleRepository.save(article);
    }

    @Test
    public void delete(){
        Page<Article> articles = articleRepository.findByTitle("Spring Data Elasticsearch",PageRequest.of(0,10));
        Article article = articles.getContent().get(0);
        articleRepository.delete(article);
    }

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    //使用Template进行关键字查询
    //关于正则表达式可以参考https://www.runoob.com/java/java-regular-expressions.html
    //.*data.* 可以匹配ddata, dataa等
    @Test
    void queryTileContainByTemplate()  {
        Query query = new NativeSearchQueryBuilder().withFilter(regexpQuery("title",".*elasticsearch2.*")).build();
        SearchHits<Article> articles = elasticsearchRestTemplate.search(query, Article.class, IndexCoordinates.of("blog"));
        System.out.println(JSON.toJSONString(articles));
    }

}