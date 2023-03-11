package com.example.instagramclone.ViewModel.LoginAndRegisterViewModels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.instagramclone.Util.changeFragment
import com.example.instagramclone.View.LoginAndRegisterFragments.LoginOrRegisterFragmentDirections

class LoginOrRegisterViewModel: ViewModel() {

    fun toLoginPage(view: View){
        Navigation.changeFragment(view, LoginOrRegisterFragmentDirections.actionLoginOrRegisterFragmentToLoginFragment())
    }

    fun toRegisterPage(view: View){
        Navigation.changeFragment(view, LoginOrRegisterFragmentDirections.actionLoginOrRegisterFragmentToRegisterFragment())
    }

}