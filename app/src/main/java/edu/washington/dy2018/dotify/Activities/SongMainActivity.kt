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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_main)

        val songListFragment:SongListFragment
        if (!hasBackStack() && getSongListFragment() == null) {
            songListFragment= SongListFragment()

            val argumentSongList = Bundle().apply {
                putParcelableArrayList(SongListFragment.ARG_SONGLIST, ArrayList(listOfSongs))
            }
            songListFragment.arguments = argumentSongList

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()

            initShuffleClick(songListFragment)

        } else if (hasBackStack()) {
            songListFragment = getSongListFragment() as SongListFragment
            initShuffleClick(songListFragment)
            miniPlayer.visibility = View.GONE

        } else {
            songListFragment = getSongListFragment() as SongListFragment
            initShuffleClick(songListFragment)
        }

        initMiniPlayerClick()
        initBackButton()
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