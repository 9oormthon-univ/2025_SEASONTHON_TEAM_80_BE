package com.goormthon.backend.firstsori.domain.music.domain.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicEventConsumer {

    private static final String MUSIC_EVENT_STREAM = "chart:music_event_stream";
    private static final String POPULAR_MUSIC_KEY = "chart:popular_music_sorted_set";

    private static final String GROUP_NAME = "music_event_group"; // 메세지를 처리하는 소비자 그룹 이름
    private static final String CONSUMER_NAME = "consumer_1"; // 그룹 내 comsumer를 구분하는 id (추후에 확장할 예정)
    private final StringRedisTemplate redisTemplate;

    @PostConstruct
    public void initGroup() {
        try {
            redisTemplate.opsForStream().createGroup(MUSIC_EVENT_STREAM, GROUP_NAME);
        } catch (Exception e) {
            // 그룹이 이미 존재하면 예외 발생할 수 있어 무시 가능
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void consume() {
        List<MapRecord<String, Object, Object>> messages = redisTemplate.opsForStream().read(
                Consumer.from(GROUP_NAME, CONSUMER_NAME),
                org.springframework.data.redis.connection.stream.StreamReadOptions.empty().count(10).block(Duration.ofSeconds(1)),
                StreamOffset.create(MUSIC_EVENT_STREAM, org.springframework.data.redis.connection.stream.ReadOffset.lastConsumed())
        );

        if (messages == null || messages.isEmpty()) return;

        for (MapRecord<String, Object, Object> message : messages) {
            try {
                String songName = (String) message.getValue().get("songName");

                redisTemplate.opsForZSet().incrementScore(POPULAR_MUSIC_KEY, songName, 1);  // ZSet에 musicId 점수 증

                redisTemplate.opsForStream().acknowledge(MUSIC_EVENT_STREAM, GROUP_NAME, message.getId());  // 스트림 메시지 ack

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
