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
import com.bumptech.glide.Glide
import com.das_develop.gvi_technology_project.databinding.ActivityMovieDetailBinding
import com.google.android.play.integrity.internal.s
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONException
import org.json.JSONObject

class MovieDetail : AppCompatActivity() ,PaymentResultWithDataListener{
    private lateinit var binding: ActivityMovieDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Fetch intent data
      val title = intent.getStringExtra("title")
        val year = intent.getStringExtra("year")
        val country = intent.getStringExtra("country")
      val genres = intent.getStringArrayExtra("genres")?.joinToString(", ") // Convert Array to String
        val imdbRating = intent.getStringExtra("imdb_rating")
        val poster = intent.getStringExtra("poster")


       // Log.d("TAG", "onCreate: "+genres)
        // Set data to the views
        binding.textViewTitle.text = title
        binding.textViewYearCountry.text = "$year | $country"
        binding.textViewGenres.text = genres
        binding.textViewRating.text = "IMDb: $imdbRating"
        binding.textViewDescription.text = "Description : \n Set in a post-apocalyptic desert wasteland where petrol and water are scarce commodities, Fury Road follows Max Rockatansky (Hardy), who joins forces with Imperator Furiosa (Theron) against warlord Immortan Joe (Keays-Byrne) and his army, leading to a lengthy road battle."

        // Load the poster image using Glide
        Glide.with(this).load(poster).into(binding.imageViewPoster)

        binding.buttonBookTicket.setOnClickListener {
            binding.movieDetailCv.visibility = View.VISIBLE

            payButton()

        }



    }

    private fun payButton() {
        binding.payBtn.setOnClickListener {
            paymentMode()
        }
    }

    private fun paymentMode() {
        // on below line getting amount from edit text
        val amt = 200
        val name = binding.nameTV.text.toString()
        val phoneNo = binding.phoneTV.text.toString()

        if(name.isNotEmpty()){
            if(phoneNo.isEmpty()){
                Toast.makeText(this, "Enter phone no", Toast.LENGTH_SHORT).show()
            }
            else if (phoneNo.length == 10 && phoneNo.all { it.isDigit() }) {
                // Phone number is valid and 10 digits, proceed with the rest of your logic
                // rounding off the amount.
                val amount = Math.round(amt.toFloat() * 100).toInt()
                val lavenderColor = String.format("#%06X", 0xFFFFFF and resources.getColor(R.color.lavender, null))

                // on below line we are
                // initializing razorpay account
                val checkout = Checkout()

                // on the below line we have to see our id.
                checkout.setKeyID("rzp_test_Nqy8gmPWtyPySL")

                // set image
                checkout.setImage(R.drawable.logo)

                // initialize json object
                val obj = JSONObject()
                try {
                    // to put name
                    obj.put("name", name)

                    // put description
                    obj.put("description", "Test payment")

                    // to set theme color
                    obj.put("theme.color", lavenderColor)

                    // put the currency
                    obj.put("currency", "INR")

                    // put amount
                    obj.put("amount", amount)

                    // put mobile number
                    obj.put("prefill.contact", phoneNo)

                    // put email
                    obj.put("prefill.email", "demo@gmail.com")

                    // open razorpay to checkout activity
                    checkout.open(this, obj)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                // Your else logic goes here
            }
            else{
               Toast.makeText(this, "Enter 10 digit phone no", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show()
        }





    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
        binding.movieDetailCv.visibility = View.GONE
        binding.buttonBookTicket.visibility = View.GONE
        binding.successLayout.visibility = View.VISIBLE
        homeBack()

    }

    private fun homeBack() {
        binding.homeTv.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment Failed due to error : " + p1, Toast.LENGTH_SHORT).show();

    }


}


