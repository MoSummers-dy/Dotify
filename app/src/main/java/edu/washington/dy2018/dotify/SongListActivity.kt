package edu.washington.dy2018.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {
    private val listOfSongs = SongDataProvider.getAllSongs()
    private val songAdapter= SongListAdapter(listOfSongs)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        rvSongs.adapter = songAdapter

        initShuffleClick()
    }

    fun initShuffleClick() {
        btnShuffle.setOnClickListener{
            val newSongs = listOfSongs.shuffled()
            songAdapter.change(newSongs)
        }
    }
}
