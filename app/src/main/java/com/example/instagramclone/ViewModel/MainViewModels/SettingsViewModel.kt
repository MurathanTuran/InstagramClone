package com.example.instagramclone.ViewModel.MainViewModels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagramclone.R
import com.example.instagramclone.View.MainFragments.SettingsFragments.SetPasswordFragment
import com.example.instagramclone.View.MainFragments.SettingsFragments.SetProfilePhotoFragment
import com.google.firebase.auth.FirebaseAuth

class SettingsViewModel: ViewModel() {

    lateinit var supportFragmentManager: FragmentManager // make livedata
    var progress = MutableLiveData<Boolean>()

    private val auth = FirebaseAuth.getInstance()

    fun setFragment(id: Int){
        when(id){
            R.id.setProfilePhotoFragment -> { loadFragment(SetProfilePhotoFragment()) }
            R.id.setPasswordFragment -> { loadFragment(SetPasswordFragment()) }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerViewContainerFragment, fragment)
        transaction.commit()
    }

    fun logout(){
        progress.value = true
        auth.signOut()
    }

}