package com.devtadeupinheiro.dslist.repositories;

import com.devtadeupinheiro.dslist.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
