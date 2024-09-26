package com.das_develop.gvi_technology_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.das_develop.gvi_technology_project.adapter.MovieAdapter
import com.das_develop.gvi_technology_project.api.MovieService
import com.das_develop.gvi_technology_project.api.RetrofitHelper
import com.das_develop.gvi_technology_project.databinding.ActivityMainBinding
import com.das_develop.gvi_technology_project.repository.MovieRepository
import com.das_develop.gvi_technology_project.viewmodel.MovieViewModel
import com.das_develop.gvi_technology_project.viewmodel.MovieViewModelFactory
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieViewModel

    private lateinit var adapter: MovieAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var recyclerView4: RecyclerView
    private lateinit var recyclerView5: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        recyclerView = binding.recyclerView
        recyclerView.layoutManager =   LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)// GridLayoutManager(this, 2) // LinearLayoutManager(this)

        recyclerView2 = binding.recyclerView2
        recyclerView2.layoutManager =   LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)// GridLayoutManager(this, 2) // LinearLayoutManager(this)

        recyclerView3 = binding.recyclerView3
        recyclerView3.layoutManager =   LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)// GridLayoutManager(this, 2) // LinearLayoutManager(this)

        recyclerView4 = binding.recyclerView4
        recyclerView4.layoutManager =   LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)// GridLayoutManager(this, 2) // LinearLayoutManager(this)

        recyclerView5 = binding.recyclerView5
        recyclerView5.layoutManager =   LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)// GridLayoutManager(this, 2) // LinearLayoutManager(this)

        val service = RetrofitHelper.getInstance().create(MovieService::class.java)
        val repository  = MovieRepository(service)
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(repository)
        )[MovieViewModel::class.java]


        binding.loadProgressBar.visibility = View.VISIBLE

        viewModel.movies.observe(this){ movieData->

            //  Log.d("TAG", "onCreate:"+movieData.toString())

            binding.loadProgressBar.visibility = View.GONE
            adapter = MovieAdapter(movieData,this)
            recyclerView.adapter = adapter
            recyclerView2.adapter = adapter
            recyclerView3.adapter = adapter
            recyclerView4.adapter = adapter
            recyclerView5.adapter = adapter

        }


        if (auth.currentUser != null) {
            binding.logoutButton.visibility = View.VISIBLE
        } else {
            binding.logoutButton.visibility = View.GONE
        }

        binding.logoutButton.setOnClickListener {

           signOut()

        }
    }

    private fun signOut() {
        // Clear login state from SharedPreferences
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("is_logged_in", false)
        editor.apply()


        if(auth.currentUser != null){
            auth.signOut()
            Toast.makeText(this, "logged out Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
        }else{
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show()
        }


        finish()
    }

}