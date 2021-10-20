package com.lpl.boot.domain.repository;


import com.lpl.boot.model.es.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

//继承ElasticsearchRepository接口
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article,String> {

    //根据作者名称 搜索
    Page<Article> findByAuthorsName(String name, Pageable pageable);

//    //搜索title字段
//    Page<Article> findByTitleIsContaining(String word, Pageable pageable);

    Page<Article> findByTitle(String title,Pageable pageable);
}
