package com.example.dota_match_tracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.dota_match_tracker.database.MatchData
import com.example.dota_match_tracker.database.MatchesDatabase
import com.example.dota_match_tracker.database.asMatchesData
import com.example.dota_match_tracker.database.asNetworkProMatchesData
import com.example.dota_match_tracker.network.NetworkProMatchesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatchesRepository (private val database: MatchesDatabase) {

    /**
     * A playlist of videos that can be shown on the screen.
     */
    val matches: LiveData<List<NetworkProMatchesData>> =
        Transformations.map(database.matchesDao.getMatches()) {
            it.asNetworkProMatchesData()
        }

    /**
     * Refresh the videos stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the videos for use, observe [videos]
     */
    suspend fun addViewedMatch(vararg matches: MatchData) {
        withContext(Dispatchers.IO) {
            database.matchesDao.insertAll(*matches.toList().toTypedArray())
        }
    }
}