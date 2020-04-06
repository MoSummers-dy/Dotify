package edu.washington.dy2018.dotify

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
        tvPlaysNum.text = "${randomPlay.toString()} plays"

        btnPlay.setOnClickListener{ btnPlayClicked: View ->
            randomPlay += 1
            tvPlaysNum.text = "${randomPlay.toString()} plays"
        }

        btnPrevious.setOnClickListener{ btnPrevClicked: View ->
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener{ btnNextClicked: View ->
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        btnChangeUser.setOnClickListener{ btnChangeUserClicked: View ->
            changeUser(btnChangeUserClicked)
        }
    }

    fun changeUser(view: View) {
        if (btnChangeUser.text == "Change User") {
            tvUserName.visibility = View.INVISIBLE

            etUserName.setText(tvUserName.text)
            etUserName.visibility = View.VISIBLE

            btnChangeUser.text = "Apply"
        } else {
            tvUserName.text = etUserName.text
            tvUserName.visibility = View.VISIBLE

            etUserName.visibility = View.INVISIBLE

            btnChangeUser.text = "Change User"
        }

    }
}
