package com.goormthon.backend.firstsori.domain.message.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "메시지 저장 요청 DTO")
public record SaveMessageRequest(
        @Schema(description = "Board의 shared ID", example = "abcd1234")
        @NotBlank
        String sharedId,

        @Schema(description = "닉네임", example = "홍길동", maxLength = 30)
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 30, message = "닉네임은 30자 이하로 입력해주세요.")
        String senderName,

        @Schema(description = "내용", example = "새해 복 많이 많으세요:)", maxLength = 500)
        @NotBlank(message = "내용은 필수입니다.")
        @Size(max = 500, message = "내용은 500자 이하로 입력해주세요.")
        String content,

        @Schema(description = "노래 제목", example = "Shape of You", maxLength = 100)
        @NotBlank(message = "노래 제목은 필수입니다.")
        @Size(max = 100, message = "노래 제목은 100자 이하로 입력해주세요.")
        String songTitle,

        @Schema(description = "아티스트 이름", example = "Ed Sheeran", maxLength = 50)
        @NotBlank(message = "아티스트는 필수입니다.")
        @Size(max = 50, message = "아티스트 이름은 50자 이하로 입력해주세요.")
        String artist,

        @Schema(description = "앨범 이미지 URL", example = "https://albums.example.com/album1.jpg")
        @NotNull(message = "앨범 이미지 URL은 필수입니다.")
        String albumImageUrl,

        @Schema(description = "노래 URL", example = "https://songs.example.com/song1.mp3")
        @NotNull(message = "노래 URL은 필수입니다.")
        String songUrl
) {
}
