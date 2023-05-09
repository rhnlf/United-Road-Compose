package com.rhnlf.unitedroadcompose.data

import com.rhnlf.unitedroadcompose.model.Player
import com.rhnlf.unitedroadcompose.model.PlayersData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PlayerRepository {

    private val players = PlayersData.players

    fun getPlayers(): Flow<List<Player>> {
        return flowOf(players)
    }

    fun searchPlayers(query: String): Flow<List<Player>> {
        return flowOf(players.filter {
            it.name.contains(query, ignoreCase = true)
        })
    }

    fun getPlayerById(playerId: Int): Player {
        return players.first {
            it.id == playerId
        }
    }

    companion object {
        @Volatile
        private var instance: PlayerRepository? = null

        fun getInstance(): PlayerRepository = instance ?: synchronized(this) {
            PlayerRepository().apply {
                instance = this
            }
        }
    }
}