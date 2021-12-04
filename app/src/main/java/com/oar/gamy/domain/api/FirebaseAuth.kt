package com.oar.gamy.domain.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuth {
    private var auth: FirebaseAuth = Firebase.auth

    fun userIsLoged(): Boolean {
        return auth.currentUser == null
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        isRegistered: (Boolean, Exception?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (userVerifiedMail()) {
                        isRegistered(true, null)
                    } else {
                        signOut()
                        isRegistered(false, Exception("Verify email."))
                    }
                } else {
                    isRegistered(false, task.exception)
                }
            }
    }

    fun registerUser(
        email: String,
        password: String,
        isSuccessRegistration: (Boolean, Exception?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        auth.currentUser!!.sendEmailVerification()
                            .addOnCompleteListener {
                                isSuccessRegistration(true, null)
                            }
                    }
                    task.exception is FirebaseAuthUserCollisionException -> {
                        isSuccessRegistration(false, task.exception)
                    }
                    else -> {
                        isSuccessRegistration(false, Exception("Registration has failed."))
                    }
                }
            }
    }

    private fun userVerifiedMail(): Boolean {
        return auth.currentUser!!.isEmailVerified
    }

    private fun signOut() {
        auth.signOut()
    }
}