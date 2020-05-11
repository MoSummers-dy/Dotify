package edu.washington.dy2018.dotify

import edu.washington.dy2018.dotify.model.IndividualSong

interface OnSongClickListener {
    fun onSongClicked(song: IndividualSong)
}
