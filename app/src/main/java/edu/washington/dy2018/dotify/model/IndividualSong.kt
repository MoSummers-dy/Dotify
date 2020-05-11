package edu.washington.dy2018.dotify.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IndividualSong(
    val id: String,
    val title: String,
    val artist: String,
    val durationMillis: Int,
    val smallImageURL: String,
    val largeImageURL: String
): Parcelable

/**
    {
        "id": "1588825540885InTheEnd_LinkinPark",
        "title": "In The End",
        "artist": "Linkin Park",
        "durationMillis": 193790,
        "smallImageURL": "https://picsum.photos/seed/InTheEnd/50",
        "largeImageURL": "https://picsum.photos/seed/InTheEnd/256"
    },
 */