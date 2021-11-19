package com.example.room_lib_sample.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.room_lib_sample.db.*

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao: MovieDao = MoviesDatabase.getDatabase(application).movieDao()
    private val directorDao: DirectorDao = MoviesDatabase.getDatabase(application).directorDao()

    val moviesList: LiveData<List<Movie>> = movieDao.allMovies
    val directorsList: LiveData<List<Director>> = directorDao.allDirectors

    suspend fun insert(vararg movies: Movie) {
        movieDao.insert(*movies)
    }

    suspend fun update(movie: Movie) {
        movieDao.update(movie)
    }

    suspend fun delete(movie: Movie) {
        movieDao.delete(movie)
    }

    suspend fun deleteAll() {
        movieDao.deleteAll()
    }
}