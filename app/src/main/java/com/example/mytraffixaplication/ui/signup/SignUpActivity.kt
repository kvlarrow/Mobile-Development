package com.example.mytraffixaplication.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.widget.Toast
import com.example.mytraffixaplication.R
import com.example.mytraffixaplication.databinding.ActivitySignUpBinding
import com.example.mytraffixaplication.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setAction()
    }

    private fun setAction(){
        binding.signIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.costumButton.setOnClickListener {
            val email = binding.edSignupEmail.text.toString()
            val password = binding.edSignupPassword.text.toString()
            val confirmPassword = binding.edConfimPasswordSignup.text.toString()

            when {
                //email validation
                email.isEmpty() -> {
                    binding.edSignupEmail.error = "Please enter your email"
                    binding.edSignupEmail.requestFocus()
                    return@setOnClickListener
                }
                //password validation
                password.isEmpty() -> {
                    binding.edSignupPassword.error = "Please enter your email"
                    binding.edSignupPassword.requestFocus()
                    return@setOnClickListener
                }
                //confirm password
                confirmPassword.isEmpty() -> {
                    binding.edConfimPasswordSignup.error = "Please enter your email"
                    binding.edConfimPasswordSignup.requestFocus()
                    return@setOnClickListener
                }
                //confirm password
                (confirmPassword != password) -> {
                    binding.edConfimPasswordSignup.error = "The passwords do not match"
                    binding.edConfimPasswordSignup.requestFocus()
                    return@setOnClickListener
                }
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edSignupEmail.error = "Email is not valid"
                binding.edSignupEmail.requestFocus()
                return@setOnClickListener
            }

            registerFirebase(email, confirmPassword)
        }
    }

    private fun registerFirebase(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful) {
                    Toast.makeText(this, "$email telah terdaftar", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}