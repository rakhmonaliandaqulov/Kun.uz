package com.example.dto.article;

import com.example.dto.TagDto;
import com.example.dto.articleType.ArticleTypeDto;
import com.example.dto.attach.AttachDto;
import com.example.dto.category.CategoryDto;
import com.example.dto.region.RegionDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFullInfoDto {
    private String id;
    private String title;
    private String description;
    private String content;
    private  Integer sharedCount;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Integer likeCount;
    private List<TagDto> tagList;
    private AttachDto attach;
    private RegionDto region;
    private CategoryDto category;
    private ArticleTypeDto type;
}
