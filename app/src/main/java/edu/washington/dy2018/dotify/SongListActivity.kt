package edu.washington.dy2018.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.washington.dy2018.dotify.MainActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {
    private val listOfSongs = SongDataProvider.getAllSongs()
    private val songAdapter= SongListAdapter(listOfSongs)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = getString(R.string.all_songs)

        rvSongs.adapter = songAdapter

        initShuffleClick()
        initAdapterClick()
    }

    fun initShuffleClick() {
        btnShuffle.setOnClickListener{
            val newSongs = listOfSongs.shuffled()
            songAdapter.change(newSongs)
        }
    }

    fun initAdapterClick() {
        songAdapter.onSongClickListener = {someSong: Song ->
            // intent.putExtra(SONG_KEY, someSong)
            // val selectedSong = intent.getParcelableArrayExtra<Song>(SONG_KEY)
            tvSelectedSongInfo.text = "${someSong.title} - ${someSong.artist}"
        }
    }
}
