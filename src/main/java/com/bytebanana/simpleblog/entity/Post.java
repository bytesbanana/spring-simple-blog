package com.bytebanana.simpleblog.entity;

import java.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long postId;

    @NotBlank(message = "Post title is mandatory")
    private String title;

    @Column(length = 200)
    private String subTitle;

    @Lob
    private String content;

    private Instant createDate;

    @Column(columnDefinition = "boolean default true")
    private Boolean published;

    private Instant lastUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;


}
