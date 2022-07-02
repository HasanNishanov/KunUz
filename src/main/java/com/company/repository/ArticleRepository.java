package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.enums.ArticleStatus;
import com.company.mapper.ArticleShortInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, String> {

    @Modifying
    @Transactional
    @Query("update ArticleEntity a set a.status =:status, a.publishDate =:time, a.publisher.id=:pid where a.id=:articleId")
    void changeStatusToPublish(@Param("articleId") String articleId, @Param("pid") Integer pId,
                               @Param("status") ArticleStatus status, @Param("time") LocalDateTime time);

    @Modifying
    @Transactional
    @Query("update ArticleEntity a set a.status =:status where a.id=:articleId")
    void changeStatusNotPublish(@Param("articleId") String articleId, @Param("status") ArticleStatus status);


    List<ArticleEntity> findTop5ByCategoryAndStatusAndVisibleTrueOrderByCreatedDateDesc(CategoryEntity category,
                                                                                        ArticleStatus status);

    List<ArticleEntity> findTop5ByCategoryAndStatusAndVisibleOrderByCreatedDateDesc(CategoryEntity category,
                                                                                    ArticleStatus status, Boolean visible);


    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleEntity  as art where art.category.key =:categoryKey and art.status =:status " +
            " and art.visible = true " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast5ByCategory(@Param("categoryKey") String categoryKey,
                                            @Param("status") ArticleStatus status, Pageable pageable);

    @Query(value = "select art.* " +
            "   from article as art " +
            "   inner join category as cat on art.category_id = cat.id " +
            " where cat.key=:key and art.status='PUBLISHED' and art.visible=true  " +
            " order by art.publish_date limit 5 ",
            nativeQuery = true)
    List<ArticleEntity> findTop5ByArticleByCategory(@Param("key") String key);

    @Query(value = "select  art.id as id ,art.title as title, art.description as description,art.publish_date as publishDate" +
            "   from article as art " +
            "   inner join category as cat on art.category_id = cat.id " +
            " where cat.key=:key and art.status='PUBLISHED' and art.visible=true  " +
            " order by art.publish_date limit 5 ",
            nativeQuery = true)
    List<ArticleShortInfo> findTop5ByArticleByCategory2(@Param("key") String key);

    @Query(value = "SELECT art.* " +
            " FROM article as art " +
            " inner join article_type as a on a.article_id = art.id " +
            " inner join types as t on t.id = a.types_id " +
            " where  t.key =:key  " +
            " order by art.publish_date " +
            " limit 5 ",
            nativeQuery = true)
    List<ArticleEntity> findTop5ByArticleNative(@Param("key") String key);

    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTypeEntity as a " +
            " inner join a.article as art " +
            " inner join a.types as t " +
            " Where t.key =:typeKey and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast5ByType(@Param("typeKey") String typeKey, Pageable pageable);


    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleEntity as art " +
            " Where art.visible = true and art.status = 'PUBLISHED' and art.id not in (:idList) " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast8NotIn(@Param("idList") List<String> idList, Pageable pageable);

    // 10
    @Query(value = "select  art.id as id ,art.title as title, art.description as description,art.publish_date as publishDate" +
            "   from article as art " +
            " where art.status='PUBLISHED' and art.visible=true  " +
            " order by art.view_count desc limit 4 ",
            nativeQuery = true)
    List<ArticleShortInfo> findMost4ViewedArticleList();

    // 9
    @Query(value = "SELECT  art.id as id ,art.title as title, art.description as description,art.publish_date as publishDate " +
            " FROM article as art " +
            " inner join article_type as a on a.article_id = art.id " +
            " inner join types as t on t.id = a.types_id " +
            " where  t.key =:key and art.visible = true and art.status = 'PUBLISHED' and art.id not in (:id) " +
            " order by art.publish_date " +
            " limit 5 ",
            nativeQuery = true)
    List<ArticleShortInfo> getLast4ArticleByType(@Param("key") String key, @Param("id") String id);

    @Query("FROM ArticleEntity art where art.visible = true and art.status = 'PUBLISHED' and art.id =?1")
    Optional<ArticleEntity> getPublishedById(String id);

}
