package com.example.groceryappb30.models

import java.io.Serializable

data class UserResponse(
    val token: String,
    val user: User
)

data class User(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
): Serializable {
    companion object {


        val KEY_NAME = "name key"
        val KEY_UID = "uid key"

        val KEY_EMAIL = "email key"
        val KEY_LOGGED_IN = "logged in key"
        val KEY_MOBILE = "mobile key"

    }
}
