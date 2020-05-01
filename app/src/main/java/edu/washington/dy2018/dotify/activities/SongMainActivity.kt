package edu.washington.dy2018.dotify.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.washington.dy2018.dotify.fragments.NowPlayingFragment
import edu.washington.dy2018.dotify.fragments.SongListFragment
import edu.washington.dy2018.dotify.OnSongClickListener
import edu.washington.dy2018.dotify.R
import kotlinx.android.synthetic.main.activity_song_main.*

class SongMainActivity : AppCompatActivity(), OnSongClickListener {
    private var currSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_main)

        if (!hasBackStack() && getSongListFragment() == null) {
            // if this the first time open up the app, create new song list fragment
            val songListFragment= SongListFragment()

            // grab the song list information
            val argumentSongList = Bundle().apply {
                putParcelableArrayList(SongListFragment.ARG_SONGLIST, ArrayList(SongDataProvider.getAllSongs().toList()))
            }
            songListFragment.arguments = argumentSongList

            // set up the song list fragment in the manager, add song list frag tag
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()

        } else if (hasBackStack()){
            // if there is an nowPlaying fragment, hide miniPlayer
            miniPlayer.visibility = View.GONE
        }

        initShuffleClick()
        initMiniPlayerClick()
        initBackButton()
    }

    private fun hasBackStack() =  supportFragmentManager.backStackEntryCount > 0
    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG)


    private fun initShuffleClick() {
        btnShuffle.setOnClickListener{
            val songListFrag = getSongListFragment() as SongListFragment
            songListFrag.shuffleList()
        }
    }

    private fun initMiniPlayerClick() {
        miniPlayer.setOnClickListener {
            // when click, hide the miniPlayer
            miniPlayer.visibility = View.GONE

            var songDetailFragment = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

            if (songDetailFragment == null) {
                // if there is no existing song detail fragment, create a new one
                songDetailFragment = NowPlayingFragment()

                // grab the selected song, and passed to the detail fragment
                val argumentSong = Bundle().apply {
                    putParcelable(NowPlayingFragment.SONG_KEY, currSong)
                }
                songDetailFragment.arguments = argumentSong

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, songDetailFragment, NowPlayingFragment.TAG)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()

            } else {
                // if there is existing song detail fragment, update the selected song
                songDetailFragment.updateSong(currSong)
            }
        }
    }

    private fun initBackButton() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (hasBackStack()) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }

        if (hasBackStack()) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // when goes back to the song list fragment, show the mini player
        supportFragmentManager.popBackStack()
        miniPlayer.visibility = View.VISIBLE
        return super.onSupportNavigateUp()
    }

    override fun onSongClicked(song: Song) {
        tvSelectedSongInfo.text = getString(R.string.selected_info, song.title, song.artist)
        currSong = song
    }
}