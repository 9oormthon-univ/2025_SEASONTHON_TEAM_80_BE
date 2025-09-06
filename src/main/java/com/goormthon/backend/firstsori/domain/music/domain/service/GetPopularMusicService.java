package com.goormthon.backend.firstsori.domain.music.domain.service;

import com.goormthon.backend.firstsori.domain.music.application.dto.response.MusicChartResponse;
import com.goormthon.backend.firstsori.domain.music.application.mapper.MusicMapper;
import com.goormthon.backend.firstsori.domain.music.domain.entity.Music;
import com.goormthon.backend.firstsori.domain.music.domain.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GetPopularMusicService {


    private static final String POPULAR_MUSIC_KEY = "chart:popular_music_sorted_set";

    private final StringRedisTemplate redisTemplate;
    private final MusicRepository musicRepository;

    /**
     * 인기 차트 상위 N개 곡 조회
     */
    public List<MusicChartResponse> getPopularMusic(int topN) {
        Set<ZSetOperations.TypedTuple<String>> topMusic = redisTemplate.opsForZSet()
                .reverseRangeWithScores(POPULAR_MUSIC_KEY, 0, topN - 1);  // 상위 topN개만 조회

        if (topMusic == null || topMusic.isEmpty()) return Collections.emptyList();

        Map<String, MusicChartResponse> chartMap = new HashMap<>();
        for (ZSetOperations.TypedTuple<String> tuple : topMusic) {
            String songName = tuple.getValue();
            Double score = tuple.getScore();

            if (songName == null || songName.isBlank() || score == null) continue;

            List<Music> musicList = musicRepository.findBySongName(songName);
            if (!musicList.isEmpty()) {
                for (Music music : musicList) {
                    chartMap.compute(songName, (k, existing) -> {
                        if (existing == null) {
                            return MusicMapper.toMusicChartResponse(music, score); // Redis 점수로 초기화
                        } else {
                            existing.setScore(existing.getScore() + score); // 누적 점수 업데이트 (필요 시)
                            return existing;
                        }
                    });
                }
            }
        }


        return chartMap.values().stream()
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore())) // 점수 내림차순 정렬
                .limit(topN)
                .toList();
    }


}



