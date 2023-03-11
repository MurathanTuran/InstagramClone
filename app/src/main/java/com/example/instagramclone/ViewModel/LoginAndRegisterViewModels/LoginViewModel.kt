package com.example.instagramclone.ViewModel.LoginAndRegisterViewModels

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.instagramclone.Util.changeFragment
import com.example.instagramclone.View.LoginAndRegisterFragments.LoginFragmentDirections
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var progress = MutableLiveData<Boolean>()

    fun login(email: String, password: String, view: View){
        progress.value = true
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Navigation.changeFragment(view, LoginFragmentDirections.actionLoginFragmentToContainerFragment())
        }.addOnFailureListener {
            Toast.makeText(view.context.applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
            progress.value = false
        }
    }

}