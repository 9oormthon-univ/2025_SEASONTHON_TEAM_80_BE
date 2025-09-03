package com.goormthon.backend.firstsori.domain.music.application.mapper;

import com.goormthon.backend.firstsori.domain.music.domain.entity.Music;

public class MusicMapper {

    public static Music toMusicEntity(String songTitle, String artist, String albumImageUrl, String songUrl) {
        return Music.builder()
                .songName(songTitle)
                .artist(artist)
                .albumImageUrl(albumImageUrl)
                .songUrl(songUrl)
                .build();
    }
}
