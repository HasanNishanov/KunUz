package com.company.controller;

import com.company.dto.ArticleFilterDTO;
import com.company.dto.JwtDTO;
import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleRequestDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;



    @ApiOperation(value = "Create Article", notes = "Method for create article")
    @PostMapping("/adm/create")
    public ResponseEntity<ArticleDTO> create(@RequestBody @Valid  ArticleCreateDTO dto,
                                             HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ArticleDTO response = articleService.create(dto, profileId);
        return ResponseEntity.ok().body(response);
    }


    @ApiOperation(value = "Update article", notes = "Method for update article")
    @PutMapping("/adm/status/{id}")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") @Valid  String articleId,
                                             HttpServletRequest request) {
        Integer pId = HttpHeaderUtil.getId(request, ProfileRole.PUBLISHER);
        articleService.updateByStatus(articleId, pId);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "Get last 5abc", notes = "Method for getLast5ArticleByCategory")
    @GetMapping("user/category/{categoryKey}")
    public ResponseEntity<List<ArticleDTO>> getLast5ArticleByCategory(@PathVariable("categoryKey")  String categoryKey) {
//        List<ArticleDTO> response = articleService.getLast5ArticleByCategory(categoryKey);
        List<ArticleDTO> response = articleService.getLast5ArticleByCategory3(categoryKey);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Get last 5abt", notes = "Method for getLast5ArticleByType")
    @GetMapping("user/type/{typeKey}")
    public ResponseEntity<List<ArticleDTO>> getLast5ArticleByType(@PathVariable("typeKey")  String typeKey) {
        List<ArticleDTO> response = articleService.getLast5ArticleByType(typeKey);
        return ResponseEntity.ok().body(response);
    }


    @ApiOperation(value = "Get last 8ani", notes = "Method for getLat8ArticleNotIn")
    @PostMapping("/adm/last8")
    public ResponseEntity<List<ArticleDTO>> getLast8NoyIn(@RequestBody  ArticleRequestDTO dto) {
        List<ArticleDTO> response = articleService.getLat8ArticleNotIn(dto.getIdList());
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Get Published Artocile", notes = "Method for getPublishedArticleById")
    @GetMapping("/adm/last5/{id}")
    public ResponseEntity<ArticleDTO> getLast5ArticleByType(@PathVariable("id") String id,
                                                            @RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang) {
        ArticleDTO response = articleService.getPublishedArticleById(id, lang);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Filter for Attach", notes = "Method for filter attach")
    @PostMapping("/filter")
    public ResponseEntity<List<ArticleDTO>> filter(@RequestBody ArticleFilterDTO dto) {
        List<ArticleDTO> response = articleService.filter(dto);
        return ResponseEntity.ok().body(response);
    }

}
