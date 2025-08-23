package com.devtadeupinheiro.dslist.services;

import com.devtadeupinheiro.dslist.dto.GameDTO;
import com.devtadeupinheiro.dslist.dto.GameMinDTO;
import com.devtadeupinheiro.dslist.entities.Game;
import com.devtadeupinheiro.dslist.projections.GameMinProjection;
import com.devtadeupinheiro.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true) //Transactional garante uma transação acid. O readOnly = true melhora performance pois garante que não haverá escrita no banco
    public GameDTO findById(Long id){
        Game result = gameRepository.findById(id).get(); //Fazer tratamento de exceção para casa o id não exista
        return new GameDTO(result);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
        List<Game> result = gameRepository.findAll();
        return result.stream().map(x -> new GameMinDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long listId) {
        List<GameMinProjection> result = gameRepository.searchByList(listId);
        return result.stream().map(x -> new GameMinDTO(x)).toList();
    }

}
