package com.example.room_lib_sample.db

import androidx.room.*
import com.example.room_lib_sample.db.Movie.Companion.TITLE
import com.example.room_lib_sample.db.Movie.Companion.DIRECTOR_ID
import com.example.room_lib_sample.db.Movie.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = Director::class,
        parentColumns = ["did"],
        childColumns = [DIRECTOR_ID],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(TITLE), Index(DIRECTOR_ID)]
)
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid") var id: Long = 0,
    @ColumnInfo(name = TITLE) var title: String,
    @ColumnInfo(name = DIRECTOR_ID) var directorId: Long) {

    companion object {
        const val TABLE_NAME = "movie"
        const val TITLE = "title"
        const val DIRECTOR_ID = "directorId"
    }
}