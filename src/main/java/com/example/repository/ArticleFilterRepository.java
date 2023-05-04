package com.example.repository;

import com.example.dto.article.ArticleFilterDto;
import com.example.entity.ArticleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleFilterRepository {
    @Autowired
    private EntityManager entityManager;
    public List<ArticleEntity> filter(ArticleFilterDto filterDTO) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append("Select s From ArticleEntity as s where status = 'PUBLISHED' ");
        if (filterDTO.getTitle() != null) {
            builder.append(" and s.title = :title");
            params.put("title", filterDTO.getTitle());
        }
        if (filterDTO.getRegionId() != null) {
            builder.append(" and s.region_id = :region_id");
            params.put("region_id", filterDTO.getRegionId());
        }
        if (filterDTO.getCategoryId() != null) {
            builder.append(" and s.category_id = :category_id");
            params.put("category_id", filterDTO.getCategoryId());
        }
        if (filterDTO.getTypeId() != null) {
            builder.append(" and s.type_id = :type_id");
            params.put("type_id", filterDTO.getTypeId());
        }
        if (filterDTO.getModeratorId() != null) {
            builder.append(" and s.moderator_id = :moderator_id");
            params.put("moderator_id", filterDTO.getModeratorId());
        }
        if (filterDTO.getPublisherId() != null) {
            builder.append(" and s.publisher_id = :publisher_id");
            params.put("publisher_id", filterDTO.getPublisherId());
        }
        if (filterDTO.getModeratorId() != null) {
            builder.append(" and s.moderator_id = :moderator_id");
            params.put("moderator_id", filterDTO.getModeratorId());
        }
        if (filterDTO.getCreatedDateFrom() != null) {
            builder.append(" and s.createdDate >= :dateFrom ");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        } else if (filterDTO.getCreatedDateTo() != null) {
            builder.append(" and s.createdDate <= :dateTo ");
            params.put("dateTo", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MIN));
        }
        if (filterDTO.getPublishedDateFrom() != null) {
            builder.append(" and s.publishedDate >= :publishedDate ");
            params.put("publishedDate", LocalDateTime.of(filterDTO.getPublishedDateFrom(), LocalTime.MIN));
        } else if (filterDTO.getPublishedDateTo() != null) {
            builder.append(" and s.publishedDate <= :publishedDate ");
            params.put("publishedDate", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MIN));
        }
        Query query = this.entityManager.createNativeQuery(builder.toString());
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        List<ArticleEntity> articleEntityList = query.getResultList();
        return articleEntityList;
    }
}
