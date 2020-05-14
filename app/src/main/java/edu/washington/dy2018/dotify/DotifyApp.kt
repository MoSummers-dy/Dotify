package edu.washington.dy2018.dotify

import android.app.Application
import edu.washington.dy2018.dotify.model.IndividualSong


class DotifyApp:Application() {
    lateinit var songApiMangaer: SongApiManager
    var listenSongNum = 0
    var songListenListener: SongListenListener? = null

    override fun onCreate() {
        super.onCreate()
        // load manager
        songApiMangaer = SongApiManager(this)
    }

    fun onSongListened(song: IndividualSong) {
        listenSongNum++
        songListenListener?.onSongListened(song)
    }
}