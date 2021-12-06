package pl.krzesniak.gymapp.controllers;

import liquibase.pro.packaged.M;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
public class Blob {

    @Value("azure-blob://gym-app/amarantus_gotowany.jpg")
    private Resource blobFile;

    @Value("classpath:/ananas_kurczak.jpg")
    private Resource resource;
    @GetMapping("/blob")
    public ResponseEntity<Object> xd() throws IOException {
        System.out.println("XD");
        return new ResponseEntity<>(blobFile, HttpStatus.OK);
    }

    @GetMapping("/readBlobFile")
    public String readBlobFile() throws IOException {
        File file = blobFile.getFile();
        return StreamUtils.copyToString(
                this.blobFile.getInputStream(),
                Charset.defaultCharset());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        File fileObj = convertMultiPartFileToFile(file);
        byte[] bytes = file.getBytes();
        String folder = "C:\\test\\";
        Path path = Paths.get(folder + file.getOriginalFilename());
        Files.write(path, bytes);
        return new ResponseEntity<>("XD", HttpStatus.OK);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {

        }
        return convertedFile;
    }
}
