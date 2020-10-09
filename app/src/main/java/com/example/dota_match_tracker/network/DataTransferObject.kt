package com.example.dota_match_tracker.network

import android.os.Parcelable
import androidx.annotation.Nullable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@JsonClass(generateAdapter = true)
data class NetworkProMatchesData(
    @Json(name = "match_id") val matchId: String?,
    @Json(name = "dire_score") val direScore: String?,
    @Json(name = "duration") val durationSeconds: String?,
    @Json(name = "league_name") val leagueName: String?,
    @Json(name = "dire_name") val direName: String?,
    @Json(name = "radiant_name") val radiantName: String?,
    @Json(name = "radiant_score") val radiantScore: String?,
    @Json(name = "radiant_win") val isRadiantWin: Boolean?
)

@JsonClass(generateAdapter = true)
@Parcelize
data class NetworkMatchData(
    @Json(name = "match_id") val matchId: String,
    @Json(name = "dire_score") val direScore: String,
    @Json(name = "duration") val durationSeconds: String,
    @Json(name = "game_mode") val gameMode: String,
    @Json(name = "radiant_score") val radiantScore: String,
    @Json(name = "radiant_win") val isRadiantWin: Boolean,
    @Json(name = "players") val players: List<PlayerInfo>,
    @Json(name = "radiant_team") val radiantTeam: RadiantTeam?,
    @Json(name = "dire_team") val direTeam: DireTeam?,
    @Json(name = "league") val league: League?
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PlayerInfo(
    @Json(name = "player_slot") val playerSlot: String?,
    @Json(name = "account_id") val playerAccountId: String?,
    @Json(name = "personaname")val name: String?,
    val assists: String?,
    val deaths: String?,
    val gold: String?,
    val kills: String?,
    val level: String?,
    val kda: String?,
    @Json(name = "tower_damage") val towerDamage: String?,
    @Json(name = "total_gold") val totalGold: String?,
    @Json(name = "last_hits") val lastHits: String?,
    @Json(name = "gold_per_min") val goldPerMin: String?,
    @Json(name = "xp_per_min") val xpPerMin: String?,
    @Json(name = "hero_damage") val heroDamage: String?,
    @Json(name = "hero_healing") val heroHealing: String?,
    @Json(name = "hero_id") val heroId: String?,
    @Json(name = "item_0") val firstItem: String?,
    @Json(name = "item_1") val secondItem: String?,
    @Json(name = "item_2") val thirdItem: String?,
    @Json(name = "item_3") val fourthItem: String?,
    @Json(name = "item_4") val fivesItem: String?,
    @Json(name = "item_5")  val sixthItem: String?,
    @Json(name = "item_neutral") val neutralItem: String?
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class RadiantTeam(
    @Json(name = "team_id") val teamId: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "tag") val tag: String?,
    @Json(name = "logo_url") val logo: String?
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class DireTeam(
    @Json(name = "team_id") val teamId: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "tag") val tag: String?,
    @Json(name = "logo_url") val logo: String?
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class League(
    @Json(name = "league_id") val leagueId: String?,
    @Json(name = "name") val name: String?
) : Parcelable

@JsonClass(generateAdapter = true)
data class HeroesFromJson(
    val id : String,
    val name: String)

@JsonClass(generateAdapter = true)
data class ItemsFromJson(
    val id : String,
    val name: String)

enum class MatchApiStatus {LOADING, ERROR, DONE}