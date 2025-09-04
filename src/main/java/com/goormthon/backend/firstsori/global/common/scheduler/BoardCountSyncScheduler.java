package com.goormthon.backend.firstsori.global.common.scheduler;

import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import com.goormthon.backend.firstsori.domain.board.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardCountSyncScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final BoardRepository boardRepository;

    /**
     * Redis의 보드 메시지 카운트와 DB를 동기화하는 스케줄러 메서드
     * 30초마다 실행됩니다.
     */
    @Scheduled(fixedDelay = 30000)
    public void syncBoardMessageCounts() {
//        log.info("[스케줄러 실행] Board 메시지 카운트 동기화 시작"); // 시작 로그

        List<Board> allBoards = boardRepository.findAll();

        for (Board board : allBoards) {
            String redisKey = "board:messageCount:" + board.getBoardId().toString();
            String countString = redisTemplate.opsForValue().get(redisKey);        // Redis에서 현재 카운트 값을 가져옴

            if (countString != null) {
                Integer redisCount = Integer.parseInt(countString);

                // DB의 기존 카운트에 Redis 카운트 값을 더함
                board.incrementMessageCount(redisCount);
                boardRepository.save(board);

                // Redis 카운트 초기화
                redisTemplate.delete(redisKey); //  redisTemplate.opsForValue().set(redisKey, "0");
//                log.info("Board ID: {}에 {}개의 메시지 카운트 동기화 완료.", board.getBoardId(), redisCount);
            }
        }
//        log.info("[스케줄러 종료] Board 메시지 카운트 동기화 완료.");

    }
}
