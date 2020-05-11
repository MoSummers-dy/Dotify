package edu.washington.dy2018.dotify

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import edu.washington.dy2018.dotify.model.AllSongs
import edu.washington.dy2018.dotify.model.IndividualSong

class SongApiManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)
    lateinit var listOfSongs: List<IndividualSong>

    init {

        listOfSongs = listOf(
            IndividualSong("1588825540885InTheEnd_LinkinPark",
                "In The End",
                "Linkin Park",
                193790,
                "https://picsum.photos/seed/InTheEnd/50",
                "https://picsum.photos/seed/InTheEnd/256")
        )

        this.getListOfSongs({songList ->
            listOfSongs = songList
        }, {})
    }

    fun getListOfSongs(onSongListReady: (List<IndividualSong>) -> Unit, onError: (() -> Unit)? = null) {
        val songListURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"

        val request = StringRequest(
            Request.Method.GET, songListURL,
            { response ->
                // success
                val gson = Gson()
                val allSongs = gson.fromJson(response, AllSongs::class.java)
                onSongListReady(allSongs.songs)
                Log.i("DY", "fetch success")
            }, {
                Log.i("DY", "error when fetching")
                // onError?.invoke()
            }
        )

        queue.add(request)
    }

    fun shuffle() {
        listOfSongs = listOfSongs.toMutableList().apply {
            shuffle()
        }.toList()
    }
}