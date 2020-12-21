package com.example.dota_match_tracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dota_match_tracker.adapter.DotaMatchesAdapter
import com.example.dota_match_tracker.databinding.FragmentMatchesViewedBinding
import com.example.dota_match_tracker.network.MatchApiStatus
import com.example.dota_match_tracker.viewmodel.MatchesViewedModel

class MatchesViewedFragment : Fragment() {
    private val viewModel: MatchesViewedModel by lazy {
        ViewModelProvider(this).get(MatchesViewedModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchesViewedBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.matchesGrid.adapter = DotaMatchesAdapter(DotaMatchesAdapter.OnClickListener {
            viewModel.getMatchInfo(it.matchId)
        })

        viewModel.apiStatus.observe(viewLifecycleOwner, Observer {
            if (it == MatchApiStatus.DONE) {
                findNavController().navigate(
                    MatchesViewedFragmentDirections.actionMatchesViewedFragmentToMatchDetailFragment2(
                        viewModel.matchData.value!!
                    )
                )
                viewModel.navigatedToDetail()
            }
        })

        return binding.root
    }
}