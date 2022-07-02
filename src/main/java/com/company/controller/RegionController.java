package com.company.controller;

import com.company.dto.RegionDTO;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.HttpHeaderUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/region")
@RestController
public class RegionController {
    @Autowired
    private RegionService regionService;

    // PUBLIC
    @ApiOperation(value = "Get ListRegion", notes = "Method for getList Region")
    @GetMapping("/user/getListRegion")
    public ResponseEntity<List<RegionDTO>> getListRegion() {
        List<RegionDTO> list = regionService.getList();
        return ResponseEntity.ok().body(list);
    }

    // SECURE
    @ApiOperation(value = "Create Region", notes = "Method for create Region")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody RegionDTO regionDto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.create(regionDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @ApiOperation(value = "Get listRegionAdmin", notes = "Method for getList Region for Admin")
    @GetMapping("/adm/getList")
    public ResponseEntity<List<RegionDTO>> getlist(HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<RegionDTO> list = regionService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Update Region", notes = "Method for Update Region")
    @PutMapping("/adm/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody RegionDTO dto,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @ApiOperation(value = "Delete Region", notes = "Method for Delete Region")
    @DeleteMapping("/adm/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }


}
