package com.goormthon.backend.firstsori.domain.music.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

// 음악 메타데이터 DTO
@Schema(description = "음악 메타데이터 응답")
public record SongData(

        @Schema(description = "곡 고유 ID")
        String songId,

        @Schema(description = "곡 제목")
        String songTitle,

        @Schema(description = "아티스트명 (여러 명일 경우 콤마 구분)")
        String artist,

        @Schema(description = "커버 이미지 URL (앨범 이미지)")
        String coverImage,

        @Schema(description = "곡 스트리밍 URL (Spotify 등 외부 링크)")
        String streamingUrl,

        @Schema(description = "미리 듣기 URL (30초 미리듣기 등)")
        String prestreamingUrl
) {
}
