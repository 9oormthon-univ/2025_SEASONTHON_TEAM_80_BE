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
                .reverseRangeWithScores(POPULAR_MUSIC_KEY, 0, -1);   // ZSet에서 점수가 높은 순으로 모든 곡과 점수 조회

        if (topMusic == null || topMusic.isEmpty()) return Collections.emptyList();

        Map<String, MusicChartResponse> chartMap = new HashMap<>();
        for (ZSetOperations.TypedTuple<String> tuple : topMusic) {
            String songName = tuple.getValue();
            if (songName == null || songName.isBlank()) continue;

            List<Music> musicList = musicRepository.findBySongName(songName);  // DB에서 songName으로 음악 정보 리스트 조회
            if (!musicList.isEmpty()) {

                // 각 음악을 응답 객체로 변환 후 맵에 저장, 이미 있으면 점수(score)를 누적
                for (Music music : musicList) {
                    chartMap.compute(songName, (k, existing) -> {
                        if (existing == null) {
                            return MusicMapper.toMusicChartResponse(music, 1); // 1점씩 시작
                        } else {
                            existing.setScore(existing.getScore() + 1); // 1점씩 누적
                            return existing;
                        }
                    });
                }
            }
        }

        // 점수 내림차순으로 정렬 후 상위 topN 개만 리스트로 반환
        return chartMap.values().stream()
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .limit(topN)
                .toList();
    }


}



