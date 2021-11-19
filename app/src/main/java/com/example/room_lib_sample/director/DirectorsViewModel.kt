package com.example.room_lib_sample.director

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.room_lib_sample.db.Director
import com.example.room_lib_sample.db.DirectorDao
import com.example.room_lib_sample.db.MoviesDatabase

class DirectorsViewModel(application: Application) : AndroidViewModel(application) {

    private val directorDao: DirectorDao = MoviesDatabase.getDatabase(application).directorDao()
    val directorList: LiveData<List<Director>> = directorDao.allDirectors

    suspend fun insert(vararg directors: Director) {
        directorDao.insert(*directors)
    }

    suspend fun update(director: Director) {
        directorDao.update(director)
    }

    suspend fun delete(director: Director) {
        directorDao.delete(director)
    }

    suspend fun deleteAll() {
        directorDao.deleteAll()
    }
}