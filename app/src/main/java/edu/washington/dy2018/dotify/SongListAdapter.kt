package edu.washington.dy2018.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(initialListOfSongs: List<Song>) :RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    private var listOfSongs: List<Song> = initialListOfSongs.toList()
    var onSongClickListener: ((song: Song) -> Unit) ?= null
    var onSongLongClickListener: ((song: Song) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listOfSongs[position]
        holder.bind(song)
    }

    fun change(newSongs: List<Song>) {
        // normal way of updating the songs list
        // listOfSongs = newSongs
        // notifyDataSetChanged()

        // animated way of updating the songs list
        val callback = SongDiffCallback(listOfSongs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        listOfSongs = newSongs
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongName = itemView.findViewById<TextView>(R.id.tvSongName)
        private val tvArtistName = itemView.findViewById<TextView>(R.id.tvArtistName)
        private val ivSongCover = itemView.findViewById<ImageView>(R.id.ivSongImage)

        fun bind(song: Song) {
            tvSongName.text = song.title
            tvArtistName.text = song.artist
            ivSongCover.setImageResource(song.smallImageID)

            itemView.setOnClickListener{
                onSongClickListener?.invoke(song)
            }

            itemView.setOnLongClickListener{
                onSongLongClickListener?.invoke(song)
                true
            }
        }
    }
}