package com.example.spotifystatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FollowedArtistsAdapter(private val aristsList: MutableList<Artist>):
    RecyclerView.Adapter<FollowedArtistsAdapter.FollowedArtistViewHolder>() {
        class FollowedArtistViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
            val nameOfArtist=itemview.findViewById<TextView>(R.id.nameOfArtist)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowedArtistViewHolder {
            val view=LayoutInflater.from(parent.context).inflate(R.layout.followed_artist_item,parent,false)
            return FollowedArtistViewHolder(view)
        }
        override fun getItemCount(): Int {
            return aristsList.size
        }
        override fun onBindViewHolder(holder: FollowedArtistViewHolder, position: Int) {
            holder.nameOfArtist.text=aristsList[position].name
        }
}