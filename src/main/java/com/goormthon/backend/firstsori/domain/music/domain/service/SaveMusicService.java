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

    public void saveMusic(Music music) {
        musicRepository.save(music);
    }
}
