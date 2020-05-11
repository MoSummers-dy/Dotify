package edu.washington.dy2018.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.washington.dy2018.dotify.activities.SongDiffCallback
import edu.washington.dy2018.dotify.model.IndividualSong

class SongListAdapter(initialListOfSongs: List<IndividualSong>) :RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    private var listOfSongs: List<IndividualSong> = initialListOfSongs.toList()
    var onSongClickListener: ((song: IndividualSong) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listOfSongs[position]
        holder.bind(song)
    }

    fun shuffleUpdate(newSongs: List<IndividualSong>) {
        // animated way of updating the songs list
        val callback = SongDiffCallback(
            listOfSongs,
            newSongs
        )
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        listOfSongs = newSongs
    }

    fun removeUpdate(newSongs: List<IndividualSong>) {
        listOfSongs = newSongs
        notifyDataSetChanged()
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongName = itemView.findViewById<TextView>(R.id.tvSongName)
        private val tvArtistName = itemView.findViewById<TextView>(R.id.tvArtistName)
        private val ivSongCover = itemView.findViewById<ImageView>(R.id.ivSongImage)

        fun bind(song: IndividualSong) {
            tvSongName.text = song.title
            tvArtistName.text = song.artist
            Picasso.get().load(song.smallImageURL).into(ivSongCover)

            itemView.setOnClickListener{
                onSongClickListener?.invoke(song)
            }
        }
    }
}