package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.service.AttachService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/attach")
@RestController
public class AttachController {

    @Autowired
    private AttachService attachService;

    @ApiOperation(value = "Upload", notes = "Method for upload attach")
    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO dto = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Open File", notes = "Method for open png file")
    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }


    @ApiOperation(value = "Open File", notes = "Method for open any file")
    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general(fileName);
    }


    @ApiOperation(value = "Dowload File", notes = "Method for dowload file")
    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        return attachService.download(fileName);
    }

    @ApiOperation(value = "Delete File", notes = "Method for delete file")
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<?> delete(@PathVariable("fileName") String id) {
        String response = attachService.delete(id);
        return ResponseEntity.ok().body(response);
    }


}
