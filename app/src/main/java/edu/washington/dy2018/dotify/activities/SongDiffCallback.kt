package edu.washington.dy2018.dotify.activities

import androidx.recyclerview.widget.DiffUtil
import edu.washington.dy2018.dotify.model.IndividualSong

class SongDiffCallback (
    private val oldSongs: List<IndividualSong>,
    private val newSongs: List<IndividualSong>
): DiffUtil.Callback (){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldSongs[oldItemPosition]
        val newSong = newSongs[newItemPosition]
        return oldSong.id == newSong.id
    }

    override fun getOldListSize(): Int = oldSongs.size

    override fun getNewListSize(): Int = newSongs.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldSongs[oldItemPosition]
        val newSong = newSongs[newItemPosition]
        return (oldSong.title == newSong.title
                && oldSong.artist == newSong.artist
                && oldSong.durationMillis == newSong.durationMillis
                && oldSong.smallImageURL == newSong.smallImageURL
                && oldSong.largeImageURL == newSong.largeImageURL)
    }

}