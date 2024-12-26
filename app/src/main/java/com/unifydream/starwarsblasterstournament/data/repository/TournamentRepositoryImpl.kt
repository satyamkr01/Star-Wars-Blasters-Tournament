package com.unifydream.starwarsblasterstournament.data.repository

import com.unifydream.starwarsblasterstournament.data.api.TournamentApi
import com.unifydream.starwarsblasterstournament.data.model.Match
import com.unifydream.starwarsblasterstournament.data.model.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TournamentRepositoryImpl @Inject constructor(
    private val apiService: TournamentApi
) : TournamentRepository {

    override suspend fun getPlayers(): List<Player> {
        return apiService.getPlayers()
    }

    override suspend fun getMatches(): List<Match> {
        return apiService.getMatches()
    }
}
