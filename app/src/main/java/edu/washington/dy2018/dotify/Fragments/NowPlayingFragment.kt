package edu.washington.dy2018.dotify.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import edu.washington.dy2018.dotify.R
import kotlinx.android.synthetic.main.fragment_song_detail.*
import kotlin.random.Random

class NowPlayingFragment:Fragment() {
    private var randomPlay: Int? = null

    companion object {
        const val SONG_KEY = "SONG_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        randomPlay = Random.nextInt(1000, 10000)

        arguments?.let { args ->
            val song = args.getParcelable<Song>(SONG_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPlayClick()
        initPrevClick()
        initNextClick()
    }

    private fun initPlayClick() {
        btnPlay.setOnClickListener{
            randomPlay = randomPlay?.plus(1)
            tvPlaysNum.text = getString(R.string.play_messages, randomPlay)
        }
    }

    private fun initPrevClick() {
        btnPrevious.setOnClickListener{
            Toast.makeText(activity, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initNextClick() {
        btnNext.setOnClickListener{
            Toast.makeText(activity, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateSong(song: Song?) {
        tvPlaysNum.text = getString(R.string.play_messages, randomPlay)
        song?.let {
            tvSongName.text = song.title
            tvSinger.text = song.artist
            ivAlbumCover.setImageResource(song.largeImageID)
        }
    }
}