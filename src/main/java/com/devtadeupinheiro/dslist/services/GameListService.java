package com.devtadeupinheiro.dslist.services;

import com.devtadeupinheiro.dslist.dto.GameDTO;
import com.devtadeupinheiro.dslist.dto.GameListDTO;
import com.devtadeupinheiro.dslist.dto.GameMinDTO;
import com.devtadeupinheiro.dslist.entities.Game;
import com.devtadeupinheiro.dslist.entities.GameList;
import com.devtadeupinheiro.dslist.projections.GameMinProjection;
import com.devtadeupinheiro.dslist.repositories.GameListRepository;
import com.devtadeupinheiro.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(x -> new GameListDTO(x)).toList(); //Funciona porque na classe tem um construtor que recebe o GameList(result) como argumento
    }

    @Transactional
    public void move (Long listId, int sourceIndex, int destinationIndex) {
        List<GameMinProjection> list = gameRepository.searchByList(listId);

        GameMinProjection obj = list.remove(sourceIndex);
        list.add(destinationIndex, obj);

        //Se o source for menor que o destination, então a variável será source, senão será destination
        int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
        int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;

        for (int i = min; i <= max; i++) {
            gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
        }


    }

}
