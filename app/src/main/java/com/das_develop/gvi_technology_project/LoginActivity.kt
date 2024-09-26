package com.das_develop.gvi_technology_project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.das_develop.gvi_technology_project.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginButtonLI.setOnClickListener {
            saveLoginState(true)
            val email = binding.emailLoginET.text.toString()
            val password = binding.passwordLoginET.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email ,password).addOnCompleteListener{
                 //   Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()

                    if(it.isSuccessful){
                        Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signupButtonLI.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }

    // Function to save login state
    private fun saveLoginState(isLoggedIn: Boolean) {
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
    }
}