package edu.washington.dy2018.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var randomPlay:Int = Random.nextInt(1000, 10000000)

    companion object {
        const val SONG_KEY = "SONG_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val song = intent.getParcelableExtra<Song>(SONG_KEY)

        initSongInfo(song)
        initPlayClick()
        initPrevClick()
        initNextClick()
        initChangeUserClick()
        initShowCoverLongClick()
    }

    private fun initSongInfo(song: Song?) {
        etUserName.visibility = View.INVISIBLE
        tvPlaysNum.text = getString(R.string.play_messages, randomPlay)
        song?.let {
            tvSongName.text = song.title
            tvSinger.text = song.artist
            ivAlbumCover.setImageResource(song.largeImageID)
        }
    }

    private fun initPlayClick() {
        btnPlay.setOnClickListener{
            randomPlay += 1
            tvPlaysNum.text = getString(R.string.play_messages, randomPlay)
        }
    }

    private fun initPrevClick() {
        btnPrevious.setOnClickListener{
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initNextClick() {
        btnNext.setOnClickListener{
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initChangeUserClick() {
        btnChangeUser.setOnClickListener{
            changeUser()
        }
    }

    private fun initShowCoverLongClick() {
        ivAlbumCover.setOnLongClickListener {
            changeTextViewColor()
            true
        }
    }

    private fun changeUser() {
        if (btnChangeUser.text == "Change User") {
            tvUserName.visibility = View.INVISIBLE
            etUserName.setText(tvUserName.text)
            etUserName.visibility = View.VISIBLE
            btnChangeUser.text = getString(R.string.apply)
        } else {
            val usernameInput = etUserName.text.toString()
            if (usernameInput.isEmpty()){
                Toast.makeText(this, "Please enter a valid username", Toast.LENGTH_SHORT).show()
            } else {
                tvUserName.text = etUserName.text
                tvUserName.visibility = View.VISIBLE
                etUserName.visibility = View.INVISIBLE
                btnChangeUser.text = getString(R.string.change_user)
            }
        }
    }

    private fun changeTextViewColor() {
        val randomColor = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        tvPlaysNum.setTextColor(randomColor)
        tvUserName.setTextColor(randomColor)
        tvSinger.setTextColor(randomColor)
        tvSongName.setTextColor(randomColor)
    }
}
