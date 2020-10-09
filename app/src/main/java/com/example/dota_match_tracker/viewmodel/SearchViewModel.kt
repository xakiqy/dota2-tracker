package com.example.dota_match_tracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dota_match_tracker.database.MatchData
import com.example.dota_match_tracker.database.asMatchesData
import com.example.dota_match_tracker.network.MatchApiStatus
import com.example.dota_match_tracker.network.Network
import com.example.dota_match_tracker.network.NetworkMatchData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel : ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _apiStatus = MutableLiveData<MatchApiStatus>()
    val apiStatus: LiveData<MatchApiStatus>
        get() = _apiStatus

    private val _matchData = MutableLiveData<NetworkMatchData>()
    val matchData: LiveData<NetworkMatchData>
        get() = _matchData

    private val _proMatchesData = MutableLiveData<List<MatchData>>()
    val proMatchData: LiveData<List<MatchData>>
        get() = _proMatchesData

    init {
        uiScope.launch {
            _proMatchesData.value = Network.matchData.getProMatches().asMatchesData()

        }
    }

    fun getMatchInfo(matchId: String) {
        uiScope.launch {
            try {
                _apiStatus.value = MatchApiStatus.LOADING
                _matchData.value = Network.matchData.getMatchInfo(matchId)
                _apiStatus.value = MatchApiStatus.DONE
            } catch (e: HttpException) {
                _apiStatus.value = MatchApiStatus.ERROR
            }
        }
    }

    fun navigatedToDetail() {
        _apiStatus.value = null
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}