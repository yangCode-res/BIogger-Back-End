package com.example.biogger.entiy;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "blogposts")
@Data
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleID;
//
    @Column(nullable = false, length = 255)
    private String title;
//
    @Column( nullable = false)
    private Date creationtime;
//
    @Column(nullable = false)
    private Date lastupdatetime;
//
    @Column(length = 100)
    private String category;
//
    @Column(length = 100)
    private String subcategory;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String summary;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(length = 100)
    private String author;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int viewcount;

    private int commentcount;



    @Column(length = 255)
    private String coverimageurl;

    @Column(length = 255)
    private String seotags;

    @Column(length = 255)
    private String slug;

    private int wordcount;

    // Enum for status
    public enum Status {
        draft, published, archived
    }

    // Getters and Setters

    // Note: Here you should implement all the getter and setter methods for each field.
    // This example does not include them for brevity.
}
