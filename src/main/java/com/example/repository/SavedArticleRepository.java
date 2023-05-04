package com.example.repository;

import com.example.entity.SavedArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {
    @Query("select new SavedArticleEntity (id ,new ArticleEntity(article.id,article.title,article.description,article.attachId,article.publishedDate))  from SavedArticleEntity where profileId=?1")
    List<SavedArticleEntity> getList(Integer userId);

    @Query("from SavedArticleEntity where profileId=?1 and article.id=?2")
    SavedArticleEntity getByUserIdAndArticleId(int profileId,String articleId);
}
