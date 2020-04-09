package edu.washington.dy2018.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etUserName.visibility = View.INVISIBLE

        var randomPlay: Int = Random.nextInt(1000, 10000000)
        tvPlaysNum.text = getString(R.string.play_messages, randomPlay)

        btnPlay.setOnClickListener{
            randomPlay += 1
            tvPlaysNum.text = getString(R.string.play_messages, randomPlay)
        }

        btnPrevious.setOnClickListener{
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener{
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        btnChangeUser.setOnClickListener{
            changeUser()
        }

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
