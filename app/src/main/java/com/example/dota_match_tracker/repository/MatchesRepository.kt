package com.example.dota_match_tracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.dota_match_tracker.database.MatchData
import com.example.dota_match_tracker.database.MatchesDatabase
import com.example.dota_match_tracker.database.asNetworkProMatchesData
import com.example.dota_match_tracker.network.NetworkProMatchesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatchesRepository(private val database: MatchesDatabase) {

    val matches: LiveData<List<NetworkProMatchesData>> =
        Transformations.map(database.matchesDao.getMatches()) {
            it.asNetworkProMatchesData()
        }

    suspend fun getHeroName(id : String) : String{
        return database.heroesDao.getHero(id.toLong()).heroName
    }

    suspend fun getItemName(id : String) : String{
        return database.itemsDao.getItem(id.toLong())?.itemName ?: "0"
    }

    suspend fun addViewedMatch(vararg matches: MatchData) {
        withContext(Dispatchers.IO) {
            database.matchesDao.insertAll(*matches.toList().toTypedArray())
        }
    }
}