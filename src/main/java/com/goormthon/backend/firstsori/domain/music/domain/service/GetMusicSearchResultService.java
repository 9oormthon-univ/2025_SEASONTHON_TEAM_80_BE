package com.goormthon.backend.firstsori.domain.music.domain.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.backend.firstsori.domain.music.application.dto.response.SongData;
import com.goormthon.backend.firstsori.domain.music.application.mapper.SongDataMapper;
import com.goormthon.backend.firstsori.global.common.response.CustomException;
import com.goormthon.backend.firstsori.global.spotify.application.dto.response.SpotifySearchApiResponse;
import com.goormthon.backend.firstsori.global.spotify.domain.client.SpotifyApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.goormthon.backend.firstsori.domain.music.domain.util.PrefixUtil.*;
import static com.goormthon.backend.firstsori.global.common.exception.ErrorCode.REDIS_SERIALIZATION_ERROR;
import static com.goormthon.backend.firstsori.global.common.exception.ErrorCode.SPOTIFY_API_CALL_FAILED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GetMusicSearchResultService {

    private final RedisTemplate<String, String> redisTemplate;
    private final SpotifyApiClient spotifyApiClient;

    private static final int POPULAR_THRESHOLD = 10;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *  키워드로 spotify 검색 및 캐싱 작업 메서드
     *
     *  키워드 검색횟수 TTL (countKey) :  7일
     *  키워드 검색결과 TTL (searchKey) : 기본 1시간 캐싱
     *  10회 이상 검색된 키워드의 검색결과 TTL (trackKey) : 24시간
    */
    public List<SongData> getSearchResult(String keyword) {
        String searchKey = SEARCH_PREFIX + keyword.toLowerCase();
        String countKey = COUNT_PREFIX + keyword.toLowerCase();

        // 1. 캐시 조회
        List<SongData> cachedResult = getCachedSearchResult(searchKey, countKey);
        if (cachedResult != null) return cachedResult;

        // 2. Spotify API 호출
        SpotifySearchApiResponse response = getSpotifySearchApiResponse(keyword);

        List<SongData> musicList = response.getTracks().getItems().stream()
                .map(mapToSongData())
                .collect(Collectors.toList());

        // 3. 검색 횟수 증가
        Long count = incrementSearchCount(countKey);

        // 4. 검색 결과 캐싱 (TTL은 해당 키워드 최근 검색 횟수에 따라 다르게 설정)
        cacheSearchResult(count, searchKey, musicList);

        return musicList;
    }

    // 검색 결과 캐시 조회
    private List<SongData> getCachedSearchResult(String searchKey, String countKey) {
        String cached = redisTemplate.opsForValue().get(searchKey);
        if (cached != null) {
            incrementSearchCount(countKey);
            try {
                return objectMapper.readValue(cached, new TypeReference<List<SongData>>() {});
            } catch (JsonProcessingException e) {
                throw new CustomException(REDIS_SERIALIZATION_ERROR);
            }
        }
        return null;
    }

    // Response 내 Item을 SongData로 변환
    private Function<SpotifySearchApiResponse.Item, SongData> mapToSongData() {
        return item -> {
            SongData songMetaData = SongDataMapper.toSongData(item);

            // 곡 단위 캐싱
            cacheTrack(item, songMetaData);
            return songMetaData;
        };
    }

    // 곡 단위 캐싱
    private void cacheTrack(SpotifySearchApiResponse.Item item, SongData songMetaData) {
        String trackKey = TRACK_PREFIX + item.getId();
        try {
            redisTemplate.opsForValue().set(trackKey,
                    objectMapper.writeValueAsString(songMetaData),
                    Duration.ofHours(24)); // 24h TTL
        } catch (JsonProcessingException e) {
            throw new CustomException(REDIS_SERIALIZATION_ERROR);
        }
    }

    // 검색 횟수 증가
    private Long incrementSearchCount(String countKey) {
        Long count = redisTemplate.opsForValue().increment(countKey);
        redisTemplate.expire(countKey, Duration.ofDays(7));
        return count;
    }

    // 검색 결과 캐싱
    private void cacheSearchResult(Long count, String searchKey, List<SongData> musicList) {
        int ttl = (count != null && count >= POPULAR_THRESHOLD) ? 86400 : 3600;
        try {
            redisTemplate.opsForValue().set(searchKey,
                    objectMapper.writeValueAsString(musicList),
                    Duration.ofSeconds(ttl));
        } catch (JsonProcessingException e) {
            throw new CustomException(REDIS_SERIALIZATION_ERROR);
        }
    }

    // spotify api 호출
    private SpotifySearchApiResponse getSpotifySearchApiResponse(String keyword) {
        SpotifySearchApiResponse response;
        try {
            response = spotifyApiClient.searchMusicRaw(keyword);
        } catch (Exception e) {
            throw new CustomException(SPOTIFY_API_CALL_FAILED);
        }
        return response;
    }

}
