package com.example.youtubeklonapp.view


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.model.Item
import androidx.databinding.DataBindingUtil
import com.example.youtubeklonapp.databinding.ListItemVideoCardsBinding


class VideosCardAdapter(private var items : ArrayList<Item>, val context: Context, val listener: View.OnClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding : ListItemVideoCardsBinding

    inner class ListItemViewHolder(val binding: ListItemVideoCardsBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item : Item,context: Context)
        {
            binding.ChannelName.text = item.snippet.channelTitle
            binding.Title.text = item.snippet.title

            Glide.with(context).load(item.snippet.thumbnails.high.url)
                .into(binding.VideoImage)
            binding.executePendingBindings()
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inf = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inf,R.layout.list_item_video_cards,parent,false)
        /*val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_video_cards,parent,false)
        */
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ListItemViewHolder){
            holder.bind(items[position],context)
        }
        binding.videosCv.tag = items[position]
        binding.videosCv.setOnClickListener(listener)

    }

    override fun getItemCount(): Int {

        return items.size
    }

    fun addData(listItems: List<Item>) {
        var size = this.items.size
        this.items += listItems
        var sizeNew = this.items.size
        notifyItemRangeChanged(size, sizeNew)
    }

}