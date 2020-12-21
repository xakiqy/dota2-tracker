package com.example.dota_match_tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dota_match_tracker.database.getDatabase
import com.example.dota_match_tracker.network.MatchApiStatus
import com.example.dota_match_tracker.network.Network
import com.example.dota_match_tracker.network.NetworkMatchData
import com.example.dota_match_tracker.repository.MatchesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MatchesViewedModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = MatchesRepository(getDatabase(application))

    val matches = getDatabase(application).matchesDao.getMatches()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _apiStatus = MutableLiveData<MatchApiStatus>()
    val apiStatus: LiveData<MatchApiStatus>
        get() = _apiStatus

    private val _matchData = MutableLiveData<NetworkMatchData>()
    val matchData: LiveData<NetworkMatchData>
        get() = _matchData

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
