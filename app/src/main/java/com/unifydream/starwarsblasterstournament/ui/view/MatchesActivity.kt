package com.unifydream.starwarsblasterstournament.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unifydream.starwarsblasterstournament.databinding.ActivityMatchesDetailBinding
import com.unifydream.starwarsblasterstournament.ui.adapter.MatchesAdapter
import com.unifydream.starwarsblasterstournament.ui.viewmodel.TournamentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchesDetailBinding
    private val viewModel: TournamentViewModel by viewModels()
    private lateinit var adapter: MatchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerId = intent.getStringExtra("playerId") ?: return
        val playerName = intent.getStringExtra("playerName") ?: ""

        binding.playerNameText.text = playerName

        setupRecyclerView()
        observeViewModel()

        viewModel.loadPlayerMatches(playerId)
    }

    private fun setupRecyclerView() {
        adapter = MatchesAdapter()
        binding.matchesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.matchesRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.playerMatches.observe(this) { matches ->
            adapter.submitList(matches)
        }
    }
}