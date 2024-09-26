package com.das_develop.gvi_technology_project.repository


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.das_develop.gvi_technology_project.api.MovieService
import com.das_develop.gvi_technology_project.model.MovieDetails
import com.das_develop.gvi_technology_project.model.MovieList

class MovieRepository(private val movieService: MovieService) {

    private val movieLiveData = MutableLiveData<MovieList>()

    val movies: LiveData<MovieList>
        get() = movieLiveData


    suspend fun getMovies(page: Int) {
        val response = movieService.getMovies(page)
        if (response.body() != null) {
            movieLiveData.postValue(response.body())

        } else {
            Log.d("TAG", "getMovies: data is null  ")
            //Toast.makeText(this@MovieRepository, "data is null", Toast.LENGTH_SHORT).show()

        }
    }

}