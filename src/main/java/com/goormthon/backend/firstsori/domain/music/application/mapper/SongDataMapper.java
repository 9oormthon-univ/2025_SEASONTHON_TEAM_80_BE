package com.goormthon.backend.firstsori.domain.music.application.mapper;

import com.goormthon.backend.firstsori.domain.music.application.dto.response.SongData;
import com.goormthon.backend.firstsori.global.spotify.application.dto.response.SpotifySearchApiResponse;

import java.util.stream.Collectors;

public class SongDataMapper {

    public static SongData toSongData(SpotifySearchApiResponse.Item item) {
        return new SongData(
                item.getId(),
                item.getName(),
                item.getArtists().stream()
                        .map(SpotifySearchApiResponse.Artist::getName)
                        .collect(Collectors.joining(", ")),
                item.getAlbum().getImages().get(0).getUrl(),
                item.getExternal_urls().getSpotify(),
                item.getPreview_url()
        );
    }
}