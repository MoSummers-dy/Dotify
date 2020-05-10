package edu.washington.dy2018.dotify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import edu.washington.dy2018.dotify.R
import kotlinx.android.synthetic.main.fragment_song_detail.*
import kotlin.properties.Delegates
import kotlin.random.Random

class NowPlayingFragment:Fragment() {
    private var randomPlay by Delegates.notNull<Int>()
    private var currSong: Song? = null

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SONG_KEY = "song_key"
        private const val PLAY_NUM = "play_num"

        fun getInstance(song: Song?) = NowPlayingFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SONG_KEY, song)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            // restore the play number if there is one stored
            with(savedInstanceState) {
                 randomPlay = getInt(PLAY_NUM, -1)
            }
        } else {
            randomPlay = Random.nextInt(1000, 10000)
        }

        arguments?.let { args ->
            currSong = args.getParcelable(SONG_KEY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PLAY_NUM, randomPlay)
        super.onSaveInstanceState(outState)
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

        initSongInfo(currSong)
        initPlayClick()
        initPrevClick()
        initNextClick()
    }

    private fun initSongInfo(song: Song?) {
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