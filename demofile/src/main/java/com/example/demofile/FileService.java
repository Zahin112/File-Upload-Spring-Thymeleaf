package com.example.demofile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public FileModel store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String encodedString = Base64.getEncoder().encodeToString(file.getBytes());
        FileModel FileDB = new FileModel(fileName, file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes()));

        return fileRepository.save(FileDB);
    }

    public FileModel getFile(Long id) {
        return fileRepository.findById(id).get();
    }

    public List<FileModel> getAllFiles() {
        return fileRepository.findAll();
    }
}
