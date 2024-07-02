package com.example.storemio.repository;

import com.example.storemio.entità.Carrelloarticolo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarrelloArticoloRepository extends JpaRepository<Carrelloarticolo, Integer> {

    Optional<Carrelloarticolo> findByCarrelloIdAndArticoloSportivoId(Integer carrelloID, Integer articoloId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM carrelli_articoli WHERE ctid IN( SELECT ctid FROM carrelli_articoli WHERE (carrello_id = ?1 AND articolo_id = ?2) LIMIT 1)", nativeQuery = true)
    void elimina(Integer carrelloID, Integer articoloID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM carrelli_articoli WHERE carrello_id = ?1", nativeQuery = true)
    void svuotaCarrello(Integer carrelloID);

    List<Carrelloarticolo> findAllByCarrelloId(Integer carrelloID);
}
