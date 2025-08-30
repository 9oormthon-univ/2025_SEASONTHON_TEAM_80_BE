package com.goormthon.backend.firstsori.domain.music.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "music")
@AllArgsConstructor
@SuperBuilder
public class Music {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "music_id", nullable = false)
    private UUID musicId;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String songUrl;

    @Column(nullable = true)
    private String imageUrl; // S3 이미지 URL


}
