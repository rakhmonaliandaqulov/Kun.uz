package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.article.ArticleRequestDto;
import com.example.dto.article.ArticleShortInfoDto;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleRequestDto> create(@RequestBody @Valid ArticleRequestDto dto,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ArticleRequestDto> update(@RequestBody ArticleRequestDto dto,
                                                    @RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") String articleId) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, articleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam String status,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id, jwt.getId()));
    }

    @GetMapping("/type/{id}/five")
    public ResponseEntity<List<ArticleShortInfoDto>> get5ByTypeId(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast5ByTypeId2(id));
    }


    @GetMapping("/type/{id}/three")
    public ResponseEntity<List<ArticleShortInfoDto>> get3ByTypeId(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast3ByTypeId(id));
    }

   /* @GetMapping("get-last8")
    public ResponseEntity<List<ArticleShortInfoDto>> get3ByTypeId2(@RequestBody ArticleListDto listDTO) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast8WithoutList(listDTO.getList()));
    }*/

    /*@GetMapping("/get-id-lang")
    public ResponseEntity<?> getByIdAndLang(@RequestParam("id") String id, @RequestParam("language") String language) {
        return ResponseEntity.ok(articleService.getByIdAndLanguage(id, language));
    }*/

    @GetMapping("/get-last4")
    public ResponseEntity<List<ArticleShortInfoDto>> getLast4Except(@RequestParam("id") String id){
        return ResponseEntity.ok((articleService.getLast4ExceptGivenId(id)));
    }

    @GetMapping("/get-last4view")
    public ResponseEntity<List<ArticleShortInfoDto>> getLast4MostView(){
        return ResponseEntity.ok((articleService.getLast4MostView()));
    }

    @GetMapping("/get5by-type-region")
    public ResponseEntity<List<ArticleShortInfoDto>> get5ByTypeAndRegion(@RequestParam("type") Integer typeId, @RequestParam("region") Integer regionId){
        return ResponseEntity.ok((articleService.get5ByTypeAndRegion(typeId, regionId)));
    }

    @GetMapping("/get5by-category")
    public ResponseEntity<List<ArticleShortInfoDto>> get5ByCategory(@RequestParam("category") Integer categoryId){
        return ResponseEntity.ok(articleService.get5ByCategory(categoryId));
    }

    @GetMapping(value = "/paging")
    public ResponseEntity<Page<ArticleShortInfoDto>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "30") int size,
                                                            @RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(articleService.getArticleByRegionIdPaging(page, size, id));
    }

    @GetMapping(value = "/paging-category")
    public ResponseEntity<Page<ArticleShortInfoDto>> pagingWithCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                        @RequestParam(value = "size", defaultValue = "30") int size,
                                                                        @RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(articleService.getArticleByCategoryIdPaging(page, size, id));
    }

    @GetMapping("/view-count")
    public ResponseEntity<Integer> increaseViewCount(@RequestParam("id") String articleId){
        return ResponseEntity.ok(articleService.increaseViewCount(articleId));
    }

}
