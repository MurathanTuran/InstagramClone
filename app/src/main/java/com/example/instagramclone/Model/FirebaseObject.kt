package com.example.instagramclone.Model

data class FirebaseObject (
    val email: String? = null,
    val profilePhotoUrl: String? = null,
    val posts: ArrayList<Post>? = null
)