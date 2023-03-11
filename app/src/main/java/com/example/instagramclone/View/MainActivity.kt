package com.example.instagramclone.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagramclone.R
import com.example.instagramclone.View.MainFragments.ContainerFragment
import com.example.instagramclone.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
        checkCurrentUser()
    }

    private fun checkCurrentUser(){
        if(auth.currentUser!=null){
            val transaction = this.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, ContainerFragment()).commit()
        }
    }
}