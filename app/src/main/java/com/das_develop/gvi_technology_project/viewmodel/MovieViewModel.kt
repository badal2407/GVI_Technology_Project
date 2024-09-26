package com.das_develop.gvi_technology_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.das_develop.gvi_technology_project.model.MovieDetails
import com.das_develop.gvi_technology_project.model.MovieList
import com.das_develop.gvi_technology_project.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            movieRepository.getMovies(20)
        }

    }

    val movies: LiveData<MovieList>
        get() = movieRepository.movies

}