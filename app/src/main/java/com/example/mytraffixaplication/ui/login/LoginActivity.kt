package com.example.mytraffixaplication.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.mytraffixaplication.ui.main.MainActivity
import com.example.mytraffixaplication.databinding.ActivityLoginBinding
import com.example.mytraffixaplication.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.mytraffixaplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPref: SharedPreferences
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val jwtToken = sharedPref.getString("jwt_token", "")
        Log.d("Token", jwtToken.toString())
        if (!jwtToken.isNullOrEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setAction()
    }


    private fun setAction() {
        binding.googleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        binding.signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.costumButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.edLoginEmail.error = "Please enter your email"
                    binding.edLoginEmail.requestFocus()
                    return@setOnClickListener
                }
                //password validation
                password.isEmpty() -> {
                    binding.edLoginPassword.error = "Please enter your email"
                    binding.edLoginPassword.requestFocus()
                    return@setOnClickListener
                }
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edLoginEmail.error = "Email is not valid"
                binding.edLoginEmail.requestFocus()
                return@setOnClickListener
            }
            loginFirebase(email, password)
        }
    }

    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.getIdToken(true)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val jwtToken = task.result?.token
                                val editor = sharedPref.edit()
                                editor.putString("jwt_token", jwtToken)
                                editor.apply()
                                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                                Log.d("Firebase jwt", jwtToken ?: "Token is null")
                                handleLoginSuccess(jwtToken.toString())
                            } else {
                                Log.e("Firebase jwt", "Error getting ID token", task.exception)
                            }
                        }
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            //menagani login google
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                authWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, e.localizedMessage, LENGTH_SHORT).show()
            }
        }
    }

    private fun authWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.getIdToken(true)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val jwtToken = task.result?.token
                                val editor = sharedPref.edit()
                                editor.putString("jwt_token", jwtToken)
                                editor.apply()
                                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                                Log.d("Firebase jwt", jwtToken ?: "Token is null")
                                handleLoginSuccess(jwtToken.toString())
                            } else {
                                Log.e("Firebase jwt", "Error getting ID token", task.exception)
                            }
                        }
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun handleLoginSuccess(jwtToken: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("jwt_token", jwtToken)
        startActivity(intent)
        finish()
    }
}