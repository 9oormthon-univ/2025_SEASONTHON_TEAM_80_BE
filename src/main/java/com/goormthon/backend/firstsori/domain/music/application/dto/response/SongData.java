package com.goormthon.backend.firstsori.domain.music.application.dto.response;

public record SongData(
        String songId,
        String songTitle,
        String artist,
        String coverImage,
        String streamingUrl,
        String prestreamingUrl
) {
}
