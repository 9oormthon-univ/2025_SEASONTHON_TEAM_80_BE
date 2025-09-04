package com.goormthon.backend.firstsori.domain.music.domain.service;

import com.goormthon.backend.firstsori.domain.music.domain.entity.Music;
import com.goormthon.backend.firstsori.domain.music.domain.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SaveMusicService {

    private final MusicRepository musicRepository;
    private final MusicEventProducer musicEventProducer;

    public void saveMusic(Music music) {
        // DB에 음악 저장
        Music savedMusic = musicRepository.save(music);
        // 인기 차트 이벤트 발행
        musicEventProducer.publishMusicEvent(savedMusic.getSongName());
    }
}
