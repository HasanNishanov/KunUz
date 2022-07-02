package com.company.service;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.exps.ItemNotFoundException;
import com.company.repository.AttachRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
public class AttachService {
    @Value("${attach.folder}")
    private String attachFolder;
    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private AttachRepository attachRepository;

    public AttachDTO saveToSystem(MultipartFile file) {
        log.info("Request for saveToSystem {}");
        try {
            // zari.jpg
            String pathFolder = getYmDString(); // 2022/06/20
            String uuid = UUID.randomUUID().toString(); //  asdas-dasdas-dasdasd-adadsd
            String extension = getExtension(file.getOriginalFilename()); // jpg

            String fileName = uuid + "." + extension; //  asdas-dasdas-dasdasd-adadsd.jpg


            File folder = new File(attachFolder + pathFolder); // attaches/2022/06/20
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            // attaches/2022/06/20/asdas-dasdasd-asdas0asdas.jpg
            Path path = Paths.get(attachFolder + pathFolder + "/" + fileName);
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setId(uuid);
            entity.setExtension(extension);
            entity.setOriginalName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            entity.setPath(pathFolder);
            attachRepository.save(entity);

            AttachDTO dto = new AttachDTO();
            dto.setUrl(serverUrl + "attach/open/" + entity.getId());
            // http://localhost:8081/attach/open/8bd17c91-c1ac-494c-800d-dffd61307ef5
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] loadImage(String id) {
        log.info("Request for loadImage {}");
        byte[] imageInByte;
        String path = getFileFullPath(get(id));

        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File(path));
        } catch (Exception e) {
            return new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(originalImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageInByte;
    }

    public byte[] open_general(String id) {
        log.info("Request for open_general {}");
        byte[] data;
        try {
            // fileName -> zari.jpg
            String path = getFileFullPath(get(id));
            Path file = Paths.get(path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public ResponseEntity<Resource> download(String id) {
        log.info("Request for download {}");
        try {
            AttachEntity entity = get(id);
            String path = getFileFullPath(entity);
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String delete(String id) {
        log.info("Request for delete {}");
        Optional<AttachEntity> byId = attachRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Mazgimisan bunaqa attach yo'q");
        }
        AttachEntity entity = byId.get();
        String path = getFileFullPath(entity);

        try {
            Files.delete(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        attachRepository.deleteById(entity.getId());
        return "successfully deleted";
    }


    public String getExtension(String fileName) {
        log.info("Request for getExtension {}");// mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {
        log.info("Request for getYmDString {}");
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }


    public String getTmDasUrlLink(String folder) {
        log.info("Request for getTmDasUrlLink {}");//  2022/06/20
        String[] arr = folder.split("/");
        return arr[0] + "_" + arr[1] + "_" + arr[2];
    }


    public String getFolderPathFromUrl(String url) {
        log.info("Request for getFolderPathFromUrl {}");// 2022_6_20_f978a682-a357-4eaf-ac18-ec9482a4e58b.jpg
        String[] arr = url.split("_");
        return arr[0] + "/" + arr[1] + "/" + arr[2] + "/" + arr[3];
        // 2022/06/20/f978a682-a357-4eaf-ac18-ec9482a4e58b.jpg
    }

    private AttachEntity get(String id) {
        log.info("Request for get {}");
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach Not Found");
        });
    }

    private String getFileFullPath(AttachEntity entity) {
        log.info("Request for getFileFullPath {}");
        return attachFolder + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension();
    }


}
