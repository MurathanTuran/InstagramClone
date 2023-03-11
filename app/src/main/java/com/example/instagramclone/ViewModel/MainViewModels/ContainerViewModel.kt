package com.example.instagramclone.ViewModel.MainViewModels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.instagramclone.R
import com.example.instagramclone.View.MainFragments.AccountFragment
import com.example.instagramclone.View.MainFragments.AddPostFragment
import com.example.instagramclone.View.MainFragments.HomeFragment
import com.example.instagramclone.View.MainFragments.SettingsFragment

class ContainerViewModel: ViewModel() {

    lateinit var supportFragmentManager: FragmentManager

    fun setFragment(id: Int){
        when(id){
            R.id.homeFragment -> { loadFragment(HomeFragment()) }
            R.id.addPostFragment -> { loadFragment(AddPostFragment()) }
            R.id.accountFragment -> { loadFragment(AccountFragment()) }
            R.id.settingsFragment -> { loadFragment(SettingsFragment()) }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerViewContainerFragment, fragment)
        transaction.commit()
    }

}