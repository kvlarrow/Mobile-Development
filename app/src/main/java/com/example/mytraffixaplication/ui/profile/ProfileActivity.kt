package com.example.mytraffixaplication.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mytraffixaplication.R
import com.example.mytraffixaplication.databinding.ActivityProfileBinding
import com.example.mytraffixaplication.helper.extractUsernameFromEmail
import com.example.mytraffixaplication.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREF_NAME = "MyAppPreferences"
        private const val JWT_TOKEN_KEY = "jwt_token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val email = user.email
            val username = extractUsernameFromEmail(email)

            binding.tvName.text = username

            val googleSignInAccount = GoogleAuthProvider.getCredential(user.uid, null)
            val photoUrl = user.photoUrl
            photoUrl?.let { profileImageUrl ->
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.profile_image)
                    .error(R.drawable.profile_image)

                Glide.with(this)
                    .load(profileImageUrl)
                    .apply(requestOptions)
                    .into(binding.profileImage)
            }
        }

        setAction()
    }

    private fun setAction(){
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
        binding.menuHelp.setOnClickListener {
            Toast.makeText(this, "The menu is not ready yet", LENGTH_SHORT).show()
        }
        binding.menuLogout.setOnClickListener {
            sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove(JWT_TOKEN_KEY)
            editor.apply()

            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}