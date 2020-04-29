package edu.washington.dy2018.dotify.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.washington.dy2018.dotify.Fragments.NowPlayingFragment
import edu.washington.dy2018.dotify.Fragments.SongListFragment
import edu.washington.dy2018.dotify.OnSongClickListener
import edu.washington.dy2018.dotify.R
import kotlinx.android.synthetic.main.activity_song_main.*

class SongMainActivity : AppCompatActivity(), OnSongClickListener {
    private var listOfSongs = SongDataProvider.getAllSongs().toList()
    private var currSong: Song? = null

    companion object {
        private const val SONG_LIST = "song_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_main)

        /*
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                val pastSongList = getParcelableArrayList<Song>(SONG_LIST)
                pastSongList?.let {
                    listOfSongs = pastSongList.toList()
                }
            }
        }

         */

        val songListFragment= SongListFragment()
        val argumentSongList = Bundle().apply {
            putParcelableArrayList(SongListFragment.ARG_SONGLIST, ArrayList(listOfSongs))
        }
        songListFragment.arguments = argumentSongList

        if (!hasBackStack() && getSongListFragment() == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment)
                .commit()
        } else if (hasBackStack()) {
            miniPlayer.visibility = View.GONE
        }

        initShuffleClick(songListFragment)
        initMiniPlayerClick()
        initBackButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(SONG_LIST, ArrayList(listOfSongs))
        super.onSaveInstanceState(outState)
    }

    private fun hasBackStack() =  supportFragmentManager.backStackEntryCount > 0
    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG)


    private fun initShuffleClick(songListFrag: SongListFragment) {
        btnShuffle.setOnClickListener{
            songListFrag.shuffleList()
        }
    }

    private fun initMiniPlayerClick() {
        miniPlayer.setOnClickListener {
            miniPlayer.visibility = View.GONE

            var songDetailFragment = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

            if (songDetailFragment == null) {
                songDetailFragment = NowPlayingFragment()
                val argumentSong = Bundle().apply {
                    putParcelable(NowPlayingFragment.SONG_KEY, currSong)
                }
                songDetailFragment.arguments = argumentSong

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, songDetailFragment, NowPlayingFragment.TAG)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()

                Log.i("Diana", "${supportFragmentManager.backStackEntryCount}")
            } else {
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
        supportFragmentManager.popBackStack()
        miniPlayer.visibility = View.VISIBLE
        return super.onSupportNavigateUp()
    }

    override fun onSongClicked(song: Song) {
        tvSelectedSongInfo.text = getString(R.string.selected_info, song.title, song.artist)
        currSong = song
    }
}