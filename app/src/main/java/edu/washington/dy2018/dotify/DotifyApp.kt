package edu.washington.dy2018.dotify

import android.app.Application

class DotifyApp:Application() {
    lateinit var songApiManager: SongApiManager
    var listenSongNum = 0

    override fun onCreate() {
        super.onCreate()
        // load manager
        songApiManager = SongApiManager(this)
    }

}