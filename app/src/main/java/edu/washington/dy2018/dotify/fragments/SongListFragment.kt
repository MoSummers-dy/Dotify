package edu.washington.dy2018.dotify.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.washington.dy2018.dotify.*
import edu.washington.dy2018.dotify.model.IndividualSong
import kotlinx.android.synthetic.main.fragment_songs_list.rvSongs

class SongListFragment:Fragment() {
    private lateinit var songAdapter: SongListAdapter
    private lateinit var listOfSongs: List<IndividualSong>
    private var onSongClickedListener: OnSongClickListener? = null
    private var currSong : IndividualSong? = null
    // private var app: Application? = null
    private lateinit var songApiManager: SongApiManager

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val ARG_SONGLIST = "arg_song_list"
        const val SELECTED_SONG = "selected_song"
        const val OLD_SONGLIST = "old_song_list"

        fun getInstance() = SongListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        songApiManager = (context.applicationContext as DotifyApp).songApiManager

        if (context is OnSongClickListener) {
            onSongClickedListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            // preserve the previous song list and selected song
            with(savedInstanceState) {
                currSong = getParcelable(SELECTED_SONG)
            }
        }

        listOfSongs = songApiManager.listOfSongs

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SELECTED_SONG, currSong)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_songs_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songAdapter = SongListAdapter(listOfSongs)
        rvSongs.adapter = songAdapter

        songApiManager.getListOfSongs({ songList ->
            listOfSongs = songApiManager.listOfSongs
            songAdapter.shuffleUpdate(listOfSongs)
        }, {})

        val immutableSong = currSong
        immutableSong?.let {
            // if there is previous selected song, show the text in the mini player
            onSongClickedListener?.onSongClicked(immutableSong)
        }

        initAdapterClick()
    }

    private fun initAdapterClick() {
        songAdapter.onSongClickListener = {selectedSong: IndividualSong ->
            onSongClickedListener?.onSongClicked(selectedSong)
            currSong = selectedSong
        }
    }

    fun shuffleList() {
        songApiManager.shuffle()
        listOfSongs = songApiManager.listOfSongs
        songAdapter.shuffleUpdate(listOfSongs)

        // scroll to the top of the screen on every shuffle
        rvSongs.scrollToPosition(0)
    }

}