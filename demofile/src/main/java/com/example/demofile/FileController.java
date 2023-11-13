package com.example.demofile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {

    @Autowired
    private FileService storageService;

    @GetMapping("/home")
    public String home(){

        return "home";
    }

    @PostMapping("/upload")
    public ResponseEntity<FileModel> uploadFile(@RequestParam("file") MultipartFile file) {
//        String message = "";
        try {
            storageService.store(file);

//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new FileModel());
        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileModel());
        }
    }

    @PostMapping("/uploadFiles")
    public ResponseEntity<List<FileModel>> uploadFiles(@RequestParam("files") MultipartFile[] files) {

//        Arrays.asList(files)
//                .stream()
//                .forEach(file -> {
//                    try {
//                        storageService.store(file);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//        final FileModel[] model = {new FileModel()};
        List<FileModel> models = new ArrayList<>();
        try {
            Arrays.asList(files)
                    .stream()
                    .forEach(file -> {
                        try {
//                           model[0] =  storageService.store(file);
                           models.add(storageService.store(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(models);
//            return ResponseEntity.status(HttpStatus.OK).body(model[0]);
        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(models);
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(model[0]);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileModel>> getListFiles() {
        List<FileModel> files = storageService.getAllFiles();
        /*.map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new FileModel(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
*/

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        FileModel fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
                .body(Base64.getDecoder().decode(fileDB.getData()));
    }
}
