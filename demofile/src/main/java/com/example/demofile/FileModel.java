package com.example.demofile;

import javax.persistence.*;

@Entity
@Table(name = "fileserver")
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 200)
    private String fileName;

    @Column(name = "type")
    private String type;

    @Column(name = "data")
    @Lob
    private String data;

    public FileModel() {}

    public FileModel(String fileName, String type, String data) {
        this.fileName = fileName;
        this.type = type;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
