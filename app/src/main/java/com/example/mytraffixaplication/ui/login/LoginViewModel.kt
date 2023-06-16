package com.example.mytraffixaplication.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginResult.value = LoginResult(success = true)
                } else {
                    val error = task.exception?.message ?: "Login failed"
                    _loginResult.value = LoginResult(success = false, error = error)
                }
            }
    }
}