package com.company.controller;

import com.company.dto.article.TypesDTO;
import com.company.dto.RegionDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.TypesService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/types")
@RestController
public class ArticleTypeController {
    @Autowired
    private TypesService typesService;

    // PUBLIC
    @ApiOperation(value = "Get Article", notes = "Method for get Article List")
    @GetMapping("/user/public")
//    public ResponseEntity<List<ArticleTypeDTO>> getArticleList(@RequestParam(value = "lang",defaultValue = "uz") LangEnum lang) {
    public ResponseEntity<List<TypesDTO>> getArticleList(@RequestHeader(value = "Accept-Language", defaultValue = "uz")
                                                         LangEnum lang) {
        List<TypesDTO> list = typesService.getList(lang);
        return ResponseEntity.ok().body(list);
    }

    // SECURED

    @ApiOperation(value = "Create Type", notes = "Method for Create Type")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody TypesDTO typesDto) {
        typesService.create(typesDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @ApiOperation(value = "Get List For admin", notes = "Method for getTypeListForAdmin")
    @GetMapping("/adm/getList")
    public ResponseEntity<List<TypesDTO>> getList(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<TypesDTO> list = typesService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }


    @ApiOperation(value = "Update Type", notes = "Method for update type For Admin")
    @PutMapping("/adm/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody RegionDTO dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @ApiOperation(value = "Delete Type", notes = "Method for delete type For admin")
    @DeleteMapping("/adm/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }


    @ApiOperation(value = "Pagination", notes = "Pagination")
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "5") int size) {
        PageImpl response = typesService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }


}
