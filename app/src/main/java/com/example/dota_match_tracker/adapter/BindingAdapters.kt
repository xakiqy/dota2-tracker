package com.example.dota_match_tracker.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dota_match_tracker.R
import com.example.dota_match_tracker.database.MatchData
import com.example.dota_match_tracker.network.*
import java.time.LocalTime

@BindingAdapter("matchApiStatus")
fun bindStatus(statusImageView: ImageView, status: MatchApiStatus?) {
    when (status) {
        MatchApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MatchApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MatchApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MatchData>?) {
    val adapter = recyclerView.adapter as DotaMatchesAdapter
    adapter.submitList(data)
}

@BindingAdapter("listPlayersData")
fun bindRecyclerViewForPlayerData(recyclerView: RecyclerView, data: List<PlayerInfo>?) {
    val adapter = recyclerView.adapter as HeroesDataAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    val ro = RequestOptions()
    Glide.with(imageView.context).load(url).apply(ro.override(110, 100))
        .into(imageView)
}

@BindingAdapter("radiantWinVisibility")
fun setImageVisibilityRadiant(imageView: ImageView, boolean: Boolean) {
    if(boolean){
        imageView.visibility = View.VISIBLE
    }else{
        imageView.visibility = View.INVISIBLE
    }
}

@BindingAdapter("direWinVisibility")
fun setImageVisibilityDire(imageView: ImageView, boolean: Boolean) {
    if(boolean){
        imageView.visibility = View.INVISIBLE
    }else{
        imageView.visibility = View.VISIBLE
    }
}

@BindingAdapter("timeToClock")
fun formatToClock(textView: TextView, value: String) {
    textView.text = LocalTime.ofSecondOfDay(value.toLong()).toString()
}

@BindingAdapter("heroIcon")
fun heroIcon(imageView: ImageView, heroId: String){
    val hero = Heroes.listHeroes!!.find{it.id == heroId}
    val url = String.format("http://cdn.dota2.com/apps/dota2/images/heroes/" +
            hero!!.name.substring(14) + "_lg.png")

//    val ro = RequestOptions()
    Glide.with(imageView.context).load(url)
//        .apply(ro.override(110, 100))
        .into(imageView)
}

@BindingAdapter("teamIcon")
fun teamIcon(imageView: ImageView, url: String){
    Glide.with(imageView.context).load(url)
        .into(imageView)
}

@BindingAdapter("itemIcon")
fun itemIcon(imageView: ImageView, itemId: String?){
    if(itemId == "0" || itemId == null)
    {
        imageView.setImageResource(R.mipmap.slotempty)
        return
    }
    val item = Items.listItems!!.find{it.id == itemId}
    val url = String.format("http://cdn.dota2.com/apps/dota2/images/items/" +
            item!!.name.substring(5) + "_lg.png")

//    val ro = RequestOptions()
    Glide.with(imageView.context).load(url)
//        .apply(ro.override(80, 80))
        .into(imageView)
}

@BindingAdapter("dataToK")
fun formatToK(textView: TextView, valueToK: String) {
        textView.text = String.format("%.1f", valueToK.toDouble().div(1000)) + "k"
}