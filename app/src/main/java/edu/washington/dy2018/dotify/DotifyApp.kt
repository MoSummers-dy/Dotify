package edu.washington.dy2018.dotify

import android.app.Application

class DotifyApp:Application() {
    lateinit var songApiMangaer: SongApiManager

    override fun onCreate() {
        super.onCreate()
        songApiMangaer = SongApiManager()
    }
}