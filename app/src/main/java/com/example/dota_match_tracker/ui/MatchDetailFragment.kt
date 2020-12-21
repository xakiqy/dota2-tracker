package com.example.dota_match_tracker.ui

import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dota_match_tracker.adapter.HeroesDataAdapter
import com.example.dota_match_tracker.databinding.FragmentMatchDetailBinding
import com.example.dota_match_tracker.network.NetworkMatchData
import com.example.dota_match_tracker.viewmodel.MatchDetailViewModel
import org.jetbrains.annotations.NotNull
import java.time.LocalTime


class MatchDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMatchDetailBinding.inflate(inflater)
        val networkMatchData =
            MatchDetailFragmentArgs.fromBundle(requireArguments()).selectedProperty

        val viewModel =
            ViewModelProvider(
                this,
                MatchDetailViewModel.Factory(networkMatchData, requireContext())
            ).get(
                MatchDetailViewModel::class.java
            )

        binding.viewModel = viewModel

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.playersGridRadiant.adapter = HeroesDataAdapter()
        binding.playersGridDire.adapter = HeroesDataAdapter()
        infoBinding(binding, networkMatchData)
        return binding.root
    }

    private fun infoBinding(
        binding: @NotNull FragmentMatchDetailBinding,
        networkMatchData: NetworkMatchData
    ) {
        with(binding) {
            radiantTeamUrl = networkMatchData.radiantTeam?.logo
                ?: "https://static.wikia.nocookie.net/dota2_gamepedia/images/2/2a/Radiant_icon.png/revision/latest/scale-to-width-down/120?cb=20160609200132"
            direTeamUrl = networkMatchData.direTeam?.logo
                ?: "https://static.wikia.nocookie.net/dota2_gamepedia/images/0/0e/Dire_icon.png/revision/latest/scale-to-width-down/120?cb=20160609200134"
            direTeamName.text = networkMatchData.direTeam?.name ?: "Dire"
            radiantTeamName.text = networkMatchData.radiantTeam?.name ?: "Radiant"
            gameMode.text = if (networkMatchData.gameMode.toInt() == 3) "All Pick" else "CM"
            direScore.text = networkMatchData.direScore
            radiantScore.text = networkMatchData.radiantScore
            winner.text = if (networkMatchData.isRadiantWin) {
                winner.setTextColor(GREEN); "Victory"
            } else {
                winner.setTextColor(RED); "Victory"
            }
            matchId.text = "ID ${networkMatchData.matchId}"
            time.text =
                LocalTime.ofSecondOfDay(networkMatchData.durationSeconds.toLong()).toString()
        }
    }
}