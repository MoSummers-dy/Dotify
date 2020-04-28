package edu.washington.dy2018.dotify.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import edu.washington.dy2018.dotify.OnSongClickListener
import edu.washington.dy2018.dotify.R
import edu.washington.dy2018.dotify.SongListAdapter
import kotlinx.android.synthetic.main.fragment_songs_list.*
import kotlinx.android.synthetic.main.fragment_songs_list.rvSongs

class SongListFragment:Fragment() {
    private lateinit var songAdapter: SongListAdapter
    private lateinit var listOfSongs: List<Song>
    private var onSongClickedListener: OnSongClickListener? = null

    companion object {
        const val ARG_SONGLIST = "arg_songlist"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickedListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val songList = args.getParcelableArrayList<Song>(ARG_SONGLIST)
            if (songList != null) {
                this.listOfSongs = songList
            }
        }
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

        initAdapterClick()
    }

    private fun initAdapterClick() {
        songAdapter.onSongClickListener = {selectedSong: Song ->
            onSongClickedListener?.onSongClicked(selectedSong)
        }
    }

    fun shuffleList() {
        val newSongs = listOfSongs.toMutableList().apply { shuffle() }
        songAdapter.shuffleUpdate(newSongs)
        // scroll to the top of the screen on every shuffle
        rvSongs.scrollToPosition(0)
    }

}