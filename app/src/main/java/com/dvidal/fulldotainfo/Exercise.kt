package com.dvidal.fulldotainfo

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// you can also use imports, for example:
// import kotlin.math.*

// you can write to stdout for debugging purposes, e.g.
// println("this is a debug message")

data class Photo(
    val fileName: String,
    val extension: String,
    val location: String,
    val date: LocalDateTime,
    var number: String = "",
)

@RequiresApi(Build.VERSION_CODES.O)
fun main() {

    val photosString = "photo.jpg, Warsaw, 2013-09-05 14:08:15\n" +
            "Jay.png, london, 2015-06-20 15:13:22\n" +
            "myFriends.png, Warsaw, 2013-09-05 14:07:13\n" +
            "Eiffel.jpg, Paris, 2015-07-23 08:03:02\n" +
            "pisatower.jpg, Paris, 2015-07-22 23:59:59\n" +
            "BOB.jpg, london, 2015-08-05 00:02:03\n" +
            "notredame.png, Paris, 2015-09-01 12:00:00\n" +
            "me.jpg, Warsaw, 2013-09-06 15:40:22\n" +
            "a.png, Warsaw, 2016-02-13 13:33:50\n" +
            "b.jpg, Warsaw, 2016-01-02 15:12:22\n" +
            "c.jpg, Warsaw, 2016-01-02 14:34:30\n" +
            "d.jpg, Warsaw, 2016-01-02 15:15:01\n" +
            "e.png, Warsaw, 2016-01-02 09:49:09\n" +
            "f.png, Warsaw, 2016-01-02 10:55:32\n" +
            "g.jpg, Warsaw, 2016-02-29 22:13:11"

    println(solution(photosString))
//    println(formatUsingDecimals(10,4))
}

fun formatUsingDecimals(max: Int, number: Int): String {
    val decimals = max.toString().length

    return String.format("%0${decimals}d", number)
}

@RequiresApi(Build.VERSION_CODES.O)
fun solution(S: String): String {

    // Convert into a string list
    val photosList = S.split("\n")

    // Convert into a Photo list
    val photoObjects = photosList.map { photoString ->
        val parts = photoString.split(", ")

        val completeFileName = parts[0].split(".")
        val fileName = completeFileName[0]
        val extension = completeFileName[1]
        val location = parts[1].take(20).replaceFirstChar { it.uppercase() }

        // Convert into a Date
        val dateString = parts[2]
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateString, formatter)

        Photo(fileName, extension, location, dateTime)
    }.distinctBy { it.date } // Distinct by date


    val locationCounter = mutableMapOf<String, Int>()

    // We need to use sortedByDate
    for (photo in photoObjects.sortedBy { it.date }) {

        // If the location is already encountered, prefix it with a counter
        if (locationCounter.containsKey(photo.location)) {
            val count = locationCounter[photo.location] ?: 1
            photo.number = formatUsingDecimals(photoObjects.count { it.location == photo.location }, count + 1)
            locationCounter[photo.location] = count + 1
        } else {
            locationCounter[photo.location] = 1
            photo.number = formatUsingDecimals(photoObjects.count { it.location == photo.location },  1)
        }
    }

    val result = photoObjects.map { "${it.location}${it.number}.${it.extension}" }
    return result.toString()
}