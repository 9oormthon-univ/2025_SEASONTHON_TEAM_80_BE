package com.goormthon.backend.firstsori.domain.music.application.mapper;

import com.goormthon.backend.firstsori.domain.music.application.dto.response.MusicChartResponse;
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

    public static MusicChartResponse toMusicChartResponse(Music music,double score) {
        return MusicChartResponse.builder()
                .musicId(music.getMusicId())
                .songName(music.getSongName())
                .artist(music.getArtist())
                .albumImageUrl(music.getAlbumImageUrl())
                .songUrl(music.getSongUrl())
                .score(score)
                .build();
    }
}
