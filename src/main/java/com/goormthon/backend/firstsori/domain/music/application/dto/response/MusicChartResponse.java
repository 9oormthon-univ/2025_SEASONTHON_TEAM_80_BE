package com.goormthon.backend.firstsori.domain.music.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@Schema(name = "MusicChartResponse", description = "음악 인기 차트 응답 DTO")
public class MusicChartResponse {

    @Schema(description = "음악 고유 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID musicId;

    @Schema(description = "노래 제목", example = "Shape of You")
    private String songName;

    @Schema(description = "가수 이름", example = "Ed Sheeran")
    private String artist;

    @Schema(description = "앨범 이미지 URL", example = "https://example.com/album/image.jpg")
    private String albumImageUrl;

    @Schema(description = "노래 URL", example = "https://example.com/song/url")
    private String songUrl;

    @Schema(description = "인기 점수", example = "15.5")
    private double score;
}
