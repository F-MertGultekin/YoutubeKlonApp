package com.example.youtubeklonapp.view


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.model.Videos

class VideosCardAdapter(private val videos : Videos, val context: Context): RecyclerView.Adapter<VideosCardAdapter.ViewHolder>() {



    class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        val Layout = itemView.findViewById<CardView>(R.id.videos_cv)
        val Title : TextView = itemView.findViewById<TextView>(R.id.Title)
        val ViewCount : TextView = itemView.findViewById<TextView>(R.id.ViewCount)
        val ChannelName : TextView = itemView.findViewById<TextView>(R.id.ChannelName)
        val VideoImage : ImageView = itemView.findViewById<ImageView>(R.id.VideoImage)
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_video_cards,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ChannelName.text = videos.items[position].snippet.channelTitle
        holder.Title.text = videos.items[position].snippet.title

        Glide.with(context).load(videos.items[position].snippet.thumbnails.high.url)
            .into(holder.VideoImage)

    }

    override fun getItemCount(): Int {

        return videos.items.size
    }

}