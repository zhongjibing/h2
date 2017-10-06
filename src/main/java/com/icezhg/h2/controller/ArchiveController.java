package com.icezhg.h2.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.icezhg.h2.model.OriginArchive;
import com.icezhg.h2.repository.OriginArchiveRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/archive")
public class ArchiveController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveController.class);

    @Autowired
    private OriginArchiveRepository repository;

    @GetMapping("/list")
    public List<OriginArchive> listAll() {
        return repository.findAll();
    }

    @GetMapping("/save")
    public OriginArchive add(OriginArchive archive) {
        return save(archive);
    }

    @PostMapping("/save")
    public OriginArchive save(OriginArchive archive) {
        return repository.save(archive);
    }

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            LOGGER.info("{} upload file is empty.", new Date());
            return;
        }

        OriginArchive archive = new OriginArchive();
        String filename = file.getOriginalFilename();
        archive.setArchiveName(filename);
        archive.setExpandedName(filename.substring(filename.indexOf(".")));

        String context = null;
        try (InputStream is = file.getInputStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {
            zos.putNextEntry(new ZipEntry("0"));
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            context = Base64.encodeBase64String(baos.toByteArray());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        archive.setContext(context);
        repository.save(archive);
    }
}
