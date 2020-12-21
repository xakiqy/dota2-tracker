package com.example.dota_match_tracker.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dota_match_tracker.database.MatchesDatabase
import com.example.dota_match_tracker.database.getDatabase
import com.example.dota_match_tracker.network.NetworkDotaInfo
import com.example.dota_match_tracker.network.asHeroesDb
import com.example.dota_match_tracker.network.asItemsDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DotaInfoWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val db = getDatabase(applicationContext)
                heroesDataUpdate(db)
                itemsDataUpdate(db)
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }

    private suspend fun heroesDataUpdate(db : MatchesDatabase){
        val heroes = NetworkDotaInfo.dotaData.getHeroesData().result.heroes
        db.heroesDao.insertAll(heroes.asHeroesDb())
    }

    private suspend fun itemsDataUpdate(db : MatchesDatabase){
        val items = NetworkDotaInfo.dotaData.getItemsData().result.items
        db.itemsDao.insertAll(items.asItemsDb())
    }
}