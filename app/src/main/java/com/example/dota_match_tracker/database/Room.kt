package com.example.dota_match_tracker.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatchesDao {
    @Query("select * from matchdata ORDER BY ID DESC")
    fun getMatches(): LiveData<List<MatchData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg matchData: MatchData)
}

@Database(entities = [MatchData::class], version = 2)
abstract class MatchesDatabase : RoomDatabase() {
    abstract val matchesDao: MatchesDao
}

private lateinit var INSTANCE: MatchesDatabase

fun getDatabase(context: Context): MatchesDatabase {
    synchronized(MatchesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MatchesDatabase::class.java,
                "matches")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}