package com.unifydream.starwarsblasterstournament.data.repository

import com.unifydream.starwarsblasterstournament.data.model.Match
import com.unifydream.starwarsblasterstournament.data.model.Player

interface TournamentRepository {
    suspend fun getPlayers(): List<Player>
    suspend fun getMatches(): List<Match>
}