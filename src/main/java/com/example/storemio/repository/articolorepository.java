package com.example.storemio.repository;

import com.example.storemio.entità.ArticoloSportivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface articolorepository extends JpaRepository<ArticoloSportivo, Integer> {

    List<ArticoloSportivo> findAllByOrderByNomeArticolo();
}