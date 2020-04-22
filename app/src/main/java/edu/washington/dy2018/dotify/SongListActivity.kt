package edu.washington.dy2018.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.washington.dy2018.dotify.MainActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {
    private val listOfSongs = SongDataProvider.getAllSongs()
    private val songAdapter= SongListAdapter(listOfSongs)
    private var currSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = getString(R.string.all_songs)

        rvSongs.adapter = songAdapter

        initShuffleClick()
        initAdapterClick()
        initAdapterLongClick()
        initMiniPlayerClick()
    }

    private fun initShuffleClick() {
        btnShuffle.setOnClickListener{
            val newSongs = listOfSongs.shuffled()
            songAdapter.change(newSongs)
        }
    }

    private fun initAdapterClick() {
        songAdapter.onSongClickListener = {someSong: Song ->
            tvSelectedSongInfo.text = getString(R.string.selected_info, someSong.title, someSong.artist)
            currSong = someSong
        }
    }

    private fun initAdapterLongClick() {
        songAdapter.onSongLongClickListener = { someSong: Song ->
            Toast.makeText(this, "${someSong.title} was deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initMiniPlayerClick() {
        miniPlayer.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SONG_KEY, currSong)
            startActivity(intent)
        }
    }
}
