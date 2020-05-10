package edu.washington.dy2018.dotify

import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SongApiManager {
    var listOfSongs: List<Song>

    init {
        listOfSongs = SongDataProvider.getAllSongs().toList()
    }

    fun shuffle() {
        listOfSongs = listOfSongs.toMutableList().apply {
            shuffle()
        }.toList()
    }
}