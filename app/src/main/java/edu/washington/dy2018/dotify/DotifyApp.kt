package edu.washington.dy2018.dotify

import android.app.Application
import android.util.Log
import edu.washington.dy2018.dotify.model.IndividualSong

class DotifyApp:Application() {
    lateinit var songApiMangaer: SongApiManager
    // var listOfSongs: List<IndividualSong> ?= null

    override fun onCreate() {
        super.onCreate()
        // load manager
        songApiMangaer = SongApiManager(this)

        /**
         *
        songApiMangaer.getListOfSongs({ allSongs ->
        val songList = allSongs.songs
        listOfSongs = songList
        },
        {
        Log.i("DY", "error")
        })
         */


    }
}