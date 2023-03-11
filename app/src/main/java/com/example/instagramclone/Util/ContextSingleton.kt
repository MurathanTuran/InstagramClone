package com.example.instagramclone.Util

import androidx.fragment.app.FragmentManager

object ContextSingleton {

    var supportFragmentManager: FragmentManager? = null

    @JvmName("getSupportFragmentManager1")
    fun getSupportFragmentManager(): FragmentManager? {
        return supportFragmentManager
    }
}