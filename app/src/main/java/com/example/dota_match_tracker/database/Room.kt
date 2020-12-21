package com.example.dota_match_tracker.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatchesDao {
    @Query("select * from matchdata ORDER BY ID DESC")
    fun getMatches(): LiveData<List<MatchData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(matchData: Array<MatchData>)
}

@Dao
interface HeroesDao {
    @Query("select * from hero")
    fun getHeroes(): LiveData<List<Hero>>

    @Query("select * from hero where id =:id ")
    suspend fun getHero(id : Long): Hero

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(matchData: List<Hero>)
}

@Dao
interface ItemsDao {
    @Query("select * from item")
    fun getItems(): LiveData<List<Item>>

    @Query("select * from item where id =:id ")
    suspend fun getItem(id : Long): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: List<Item>)
}

@Database(entities = [MatchData::class, Hero::class, Item::class], version = 2)
abstract class MatchesDatabase : RoomDatabase() {
    abstract val matchesDao: MatchesDao
    abstract val heroesDao: HeroesDao
    abstract val itemsDao: ItemsDao
}

private lateinit var INSTANCE: MatchesDatabase

fun getDatabase(context: Context): MatchesDatabase {
    synchronized(MatchesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MatchesDatabase::class.java,
                "matches"
            )
                .build()
        }
    }
    return INSTANCE
}