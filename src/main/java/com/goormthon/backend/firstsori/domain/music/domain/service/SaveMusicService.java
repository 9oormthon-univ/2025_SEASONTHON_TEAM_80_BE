package com.goormthon.backend.firstsori.domain.music.domain.service;

import com.goormthon.backend.firstsori.domain.music.domain.entity.Music;
import com.goormthon.backend.firstsori.domain.music.domain.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SaveMusicService {

    private final MusicRepository musicRepository;
    private final MusicEventProducer musicEventProducer;

    public Music saveMusic(Music music) {
        Optional<Music> existsMusic = musicRepository.findBySongNameAndArtist(music.getSongName(), music.getArtist());
        if (existsMusic.isEmpty()) { // DB에 없으면 저장
            Music savedMusic = musicRepository.save(music);
            musicEventProducer.publishMusicEvent(savedMusic.getSongName());
            return savedMusic;

        } else { // 이미 있으면 저장 안하고, 이벤트만 발행
            musicEventProducer.publishMusicEvent(music.getSongName());
            return existsMusic.get();

        }
    }



}
