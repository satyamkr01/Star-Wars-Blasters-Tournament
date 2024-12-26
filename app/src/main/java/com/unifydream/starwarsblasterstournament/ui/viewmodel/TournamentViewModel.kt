package com.unifydream.starwarsblasterstournament.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifydream.starwarsblasterstournament.data.model.Match
import com.unifydream.starwarsblasterstournament.data.model.Player
import com.unifydream.starwarsblasterstournament.data.model.PointsTableEntry
import com.unifydream.starwarsblasterstournament.data.repository.TournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TournamentViewModel @Inject constructor(
    private val repository: TournamentRepository
) : ViewModel() {

    private val _pointsTable = MutableLiveData<List<PointsTableEntry>>()
    val pointsTable: LiveData<List<PointsTableEntry>> get() = _pointsTable

    private val _playerMatches = MutableLiveData<List<Match>>()
    val playerMatches: LiveData<List<Match>> get() = _playerMatches

    fun loadPointsTable() {
        viewModelScope.launch {
            val players = repository.getPlayers()
            val matches = repository.getMatches()
            val pointsTable = calculatePointsTable(players, matches)
            _pointsTable.postValue(pointsTable)
        }
    }

    fun loadPlayerMatches(playerId: String) {
        viewModelScope.launch {
            val matches = repository.getMatches()
            val playerMatches = matches.filter { it.player1Id == playerId || it.player2Id == playerId }
            _playerMatches.postValue(playerMatches.sortedByDescending { it.player1Score + it.player2Score })
        }
    }

    private fun calculatePointsTable(players: List<Player>, matches: List<Match>): List<PointsTableEntry> {
        val pointsMap = mutableMapOf<String, Int>()
        val scoresMap = mutableMapOf<String, Int>()

        matches.forEach { match ->
            pointsMap[match.player1Id] = pointsMap.getOrDefault(match.player1Id, 0)
            pointsMap[match.player2Id] = pointsMap.getOrDefault(match.player2Id, 0)
            scoresMap[match.player1Id] = scoresMap.getOrDefault(match.player1Id, 0) + match.player1Score
            scoresMap[match.player2Id] = scoresMap.getOrDefault(match.player2Id, 0) + match.player2Score

            when {
                match.player1Score > match.player2Score -> {
                    pointsMap[match.player1Id] = pointsMap[match.player1Id]!! + 3
                }
                match.player1Score < match.player2Score -> {
                    pointsMap[match.player2Id] = pointsMap[match.player2Id]!! + 3
                }
                else -> {
                    pointsMap[match.player1Id] = pointsMap[match.player1Id]!! + 1
                    pointsMap[match.player2Id] = pointsMap[match.player2Id]!! + 1
                }
            }
        }

        return players.map { player ->
            PointsTableEntry(
                player = player,
                points = pointsMap[player.id] ?: 0,
                totalScore = scoresMap[player.id] ?: 0
            )
        }.sortedWith(compareByDescending<PointsTableEntry> { it.points }.thenByDescending { it.totalScore })
    }
}