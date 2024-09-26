package com.das_develop.gvi_technology_project.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.das_develop.gvi_technology_project.LoginActivity
import com.das_develop.gvi_technology_project.MovieDetail
import com.das_develop.gvi_technology_project.R
import com.das_develop.gvi_technology_project.model.MovieList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MovieAdapter(private var movie:MovieList, private val context: Context):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val movieName : TextView = view.findViewById(R.id.movie_name)
        val movieImdb : TextView = view.findViewById(R.id.movie_imbd)
        val movieImage : ImageView = view.findViewById(R.id.movie_image)



    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_list,parent,false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {

        return movie.data.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movie.data[position]
        holder.movieName.text = movie.title
        holder.movieImdb.text = "Imdb Rating: ${ movie.imdb_rating }"

        Glide.with(holder.itemView.context).load(movie.poster).into(holder.movieImage)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, MovieDetail::class.java)
            intent.putExtra("title", movie.title)
           // Log.d("TAG", "onBindViewHolder: "+movie.title)
            // Pass the necessary movie details as extras
            intent.putExtra("title", movie.title)
            intent.putExtra("year", movie.year)
            intent.putExtra("country", movie.country)
           intent.putExtra("genres", movie.genres.toTypedArray()) // Convert List to Array
            intent.putExtra("imdb_rating", movie.imdb_rating)
            intent.putExtra("poster", movie.poster)
            context.startActivity(intent)
        }

    }
}