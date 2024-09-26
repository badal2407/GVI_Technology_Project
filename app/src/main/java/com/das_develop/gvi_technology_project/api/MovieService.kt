package com.das_develop.gvi_technology_project.api

import com.das_develop.gvi_technology_project.model.MovieDetails
import com.das_develop.gvi_technology_project.model.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/api/v1/movies")
    suspend fun getMovies(@Query("page") page: Int): Response<MovieList>



}