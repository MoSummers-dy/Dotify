package edu.washington.dy2018.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(initialListOfSongs: List<Song>) :RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    private var listOfSongs: List<Song> = initialListOfSongs.toList();
    var onSongClickListener: ((song: Song) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view);
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongListAdapter.SongViewHolder, position: Int): Unit {
        val song = listOfSongs[position]
        holder.bind(song, position)
    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongName = itemView.findViewById<TextView>(R.id.tvSongName)
        private val ivSongCover = itemView.findViewById<ImageView>(R.id.ivSongImage)

        fun bind(song: Song, position: Int) {
            tvSongName.text = song.title
            ivSongCover.setImageResource(song.smallImageID)
        }
    }
}