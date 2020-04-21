package edu.washington.dy2018.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        // Data set
        val listOfSongs = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(listOfSongs)
        rvSongs.adapter = songAdapter
    }
}
