package com.example.dto.article;

import com.example.entity.RegionEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ArticleInfoDto {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private RegionEntity region;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Integer likeCount;
}
