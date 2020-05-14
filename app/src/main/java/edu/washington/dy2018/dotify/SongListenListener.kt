package edu.washington.dy2018.dotify

import edu.washington.dy2018.dotify.model.IndividualSong

interface SongListenListener {
    fun onSongListened(song: IndividualSong)
}