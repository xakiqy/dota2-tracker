package com.example.dota_match_tracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dota_match_tracker.network.NetworkProMatchesData

@Entity
data class MatchData(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val matchId: String,
    val direScore: String,
    val durationSeconds: String,
    val leagueName: String,
    val direName: String,
    val radiantName: String,
    val radiantScore: String,
    val isRadiantWin: Boolean
)

fun List<MatchData>.asNetworkProMatchesData(): List<NetworkProMatchesData> {
    return map {
        NetworkProMatchesData(
            matchId = it.matchId,
            direScore = it.direScore,
            durationSeconds = it.durationSeconds,
            leagueName = it.leagueName,
            direName = it.direName,
            radiantName = it.radiantName,
            radiantScore = it.radiantScore,
            isRadiantWin = it.isRadiantWin
        )
    }
}

fun List<NetworkProMatchesData>.asMatchesData(): List<MatchData> {
    return map {
        MatchData(
            matchId = it.matchId ?: "0",
            direScore = it.direScore ?: "0",
            durationSeconds = it.durationSeconds ?: "0",
            leagueName = it.leagueName ?: "public",
            direName = it.direName ?: "Unknown",
            radiantName = it.radiantName ?: "Unknown",
            radiantScore = it.radiantScore ?: "0",
            isRadiantWin = it.isRadiantWin ?: true
        )
    }
}


