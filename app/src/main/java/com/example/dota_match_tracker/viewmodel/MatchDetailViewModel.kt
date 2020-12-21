package com.example.dota_match_tracker.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dota_match_tracker.database.MatchData
import com.example.dota_match_tracker.database.PlayerInfoUI
import com.example.dota_match_tracker.database.getDatabase
import com.example.dota_match_tracker.network.NetworkMatchData
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

    val firstTeam = MutableLiveData<List<PlayerInfoUI>>()
    val secondTeam = MutableLiveData<List<PlayerInfoUI>>()

    init {
        var playerInfoUI: List<PlayerInfoUI>
        uiScope.launch {
            playerInfoUI =
                matchData.players.map {
                    with(repository) {
                    PlayerInfoUI(
                        it.playerSlot,
                        it.playerAccountId,
                        it.name,
                        it.assists,
                        it.deaths,
                        it.gold,
                        it.kills,
                        it.level,
                        it.kda,
                        it.towerDamage,
                        it.lastHits,
                        it.goldPerMin,
                        it.xpPerMin,
                        it.heroDamage,
                        getHeroName(it.heroId!!),
                        getItemName(it.firstItem ?: "0"),
                        getItemName(it.secondItem?: "0"),
                        getItemName(it.thirdItem?: "0"),
                        getItemName(it.fourthItem?: "0"),
                        getItemName(it.fivesItem?: "0"),
                        getItemName(it.sixthItem?: "0"),
                        getItemName(it.neutralItem?: "0")
                    )
                }}
            firstTeam.value = playerInfoUI.take(5)
            secondTeam.value = playerInfoUI.takeLast(5)
        }

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
