package com.example.dota_match_tracker.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dota_match_tracker.database.MatchData
import com.example.dota_match_tracker.database.getDatabase
import com.example.dota_match_tracker.network.NetworkMatchData
import com.example.dota_match_tracker.network.NetworkProMatchesData
import com.example.dota_match_tracker.network.PlayerInfo
import com.example.dota_match_tracker.repository.MatchesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MatchDetailViewModel(private val matchData: NetworkMatchData, context: Context) :
    ViewModel() {
    private val repository = MatchesRepository(getDatabase(context))
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val firstTeam = MutableLiveData<List<PlayerInfo>>()
    val secondTeam = MutableLiveData<List<PlayerInfo>>()

    init {
        firstTeam.value = matchData.players.take(5)
        secondTeam.value = matchData.players.takeLast(5)
        uiScope.launch {
            repository.addViewedMatch(
                MatchData(
                    null,
                    matchData.matchId,
                    matchData.direScore,
                    matchData.durationSeconds,
                    matchData.league?.name ?: "Public",
                    matchData.direTeam?.name ?: "Dire",
                    matchData.radiantTeam?.name ?: "Radiant",
                    matchData.radiantScore,
                    matchData.isRadiantWin
                )
            )
        }
    }

    /**
     * Factory for constructing MatchDetailViewModel with parameter
     */
    class Factory(private val matchData: NetworkMatchData, private val context: Context) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MatchDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MatchDetailViewModel(matchData, context) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
