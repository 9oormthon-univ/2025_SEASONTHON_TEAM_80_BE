package com.goormthon.backend.firstsori.domain.music.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MusicEventProducer {

    private static final String MUSIC_EVENT_STREAM = "chart:music_event_stream";
    private final StringRedisTemplate redisTemplate;

    /**
     * 곡 선택 시 이벤트 발행
     */
    public void publishMusicEvent(String songName) {
        Map<String, String> event = Map.of(
                "songName", String.valueOf(songName),
                "timestamp", String.valueOf(System.currentTimeMillis())
        );
        redisTemplate.opsForStream().add(MUSIC_EVENT_STREAM, event);
    }

}
