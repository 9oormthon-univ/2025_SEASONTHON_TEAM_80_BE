package com.goormthon.backend.firstsori.domain.music.domain.repository;

import com.goormthon.backend.firstsori.domain.music.domain.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findBySongName(String songName);

    Optional<Music> findBySongNameAndArtist(String songName, String artist);
}
