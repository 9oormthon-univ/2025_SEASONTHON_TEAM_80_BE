package com.goormthon.backend.firstsori.domain.music.domain.repository;

import com.goormthon.backend.firstsori.domain.music.domain.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

}
