package com.unifydream.starwarsblasterstournament.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unifydream.starwarsblasterstournament.databinding.ActivityPointsTableBinding
import com.unifydream.starwarsblasterstournament.ui.adapter.PointsTableAdapter
import com.unifydream.starwarsblasterstournament.ui.viewmodel.TournamentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PointsTableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPointsTableBinding
    private val viewModel: TournamentViewModel by viewModels()
    private lateinit var adapter: PointsTableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointsTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        viewModel.loadPointsTable()
    }

    private fun setupRecyclerView() {
        adapter = PointsTableAdapter { player ->
            val intent = Intent(this, MatchesActivity::class.java)
            intent.putExtra("playerId", player.id)
            intent.putExtra("playerName", player.name)
            startActivity(intent)
        }
        binding.pointsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pointsRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.pointsTable.observe(this) { pointsTable ->
            adapter.submitList(pointsTable)
        }
    }
}