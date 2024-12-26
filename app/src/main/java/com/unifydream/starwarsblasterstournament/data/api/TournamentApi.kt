package com.unifydream.starwarsblasterstournament.data.api

import com.unifydream.starwarsblasterstournament.data.model.Match
import com.unifydream.starwarsblasterstournament.data.model.Player
import retrofit2.http.GET

interface TournamentApi {
    @GET("b/IKQQ")
    suspend fun getPlayers(): List<Player>

    @GET("b/JNYL")
    suspend fun getMatches(): List<Match>
}