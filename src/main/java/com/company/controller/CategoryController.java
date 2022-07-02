package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.dto.JwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // PUBLIC
    @ApiOperation(value = "List Category", notes = "Method for getList Category")
    @GetMapping("/user/getlist")
    public ResponseEntity<List<CategoryDTO>> getListCategory() {
        List<CategoryDTO> list = categoryService.getList();
        return ResponseEntity.ok().body(list);
    }

    // SECURED
    @ApiOperation(value = "Сreate", notes = "Method for getList Category")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDto) {
        categoryService.create(categoryDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @ApiOperation(value = "Category List", notes = "Method for get Category ListforAdmin")
    @GetMapping("/adm/getList")
    public ResponseEntity<List<CategoryDTO>> getList() {
        List<CategoryDTO> list = categoryService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }


    @ApiOperation(value = "Category List", notes = "Method for get Category List")
    @PutMapping("/adm/update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody CategoryDTO dto) {
        categoryService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @ApiOperation(value = "Delete Category", notes = "Method for Delete Category")
    @DeleteMapping("/adm/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }


}
