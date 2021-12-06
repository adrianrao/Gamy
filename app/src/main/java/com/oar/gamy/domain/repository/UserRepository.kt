package com.oar.gamy.domain.repository

import com.oar.gamy.domain.api.FirebaseAuth

class UserRepository {
    private val service: FirebaseAuth = FirebaseAuth()

    fun authenticateUser(
        email: String,
        password: String,
        isRegistered: (Boolean, Exception?) -> Unit
    ) {
        service.signInWithEmailAndPassword(email, password, isRegistered)
    }

    fun registerUser(
        email: String,
        password: String,
        isSuccessRegistration: (Boolean, Exception?) -> Unit
    ) {
        service.registerUser(email, password, isSuccessRegistration)
    }

    fun userIsLoged(): Boolean {
        return service.userIsLoged()
    }

    fun signOut(){
        service.signOut()
    }
}