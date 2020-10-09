package com.example.dota_match_tracker.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.dota_match_tracker.R
import com.example.dota_match_tracker.adapter.DotaMatchesAdapter
import com.example.dota_match_tracker.database.getDatabase
import com.example.dota_match_tracker.databinding.FragmentSearchBinding
import com.example.dota_match_tracker.network.MatchApiStatus
import com.example.dota_match_tracker.repository.MatchesRepository
import com.example.dota_match_tracker.viewmodel.SearchViewModel

class SearchFragment: Fragment() {

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentSearchBinding.inflate(inflater)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.button.setOnClickListener{
            val idText = binding.editIdText.text.toString()
            viewModel.getMatchInfo(idText)
        }

        binding.matchesGrid.adapter =  DotaMatchesAdapter(DotaMatchesAdapter.OnClickListener{
            viewModel.getMatchInfo(it.matchId)
        })

        viewModel.apiStatus.observe(viewLifecycleOwner, Observer {
            if(it == MatchApiStatus.DONE) {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragment2ToMatchDetailFragment2(viewModel.matchData.value!!))
                viewModel.navigatedToDetail()
            }
        })
        return binding.root
    }

}