package com.example.instagramclone.ViewModel.LoginAndRegisterViewModels

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.instagramclone.Model.FirebaseObject
import com.example.instagramclone.Util.changeFragment
import com.example.instagramclone.View.LoginAndRegisterFragments.RegisterFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore

    var progress = MutableLiveData<Boolean>()

    fun register(email: String, password: String, view: View){
        progress.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
           createFirestore(email, view)
        }.addOnFailureListener {
            Toast.makeText(view.context.applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
            progress.value = false
        }
    }

    private fun createFirestore(email: String, view: View){
        val firebaseObject = FirebaseObject(email, "", ArrayList())
        firestore.collection("Users").add(firebaseObject).addOnSuccessListener {
            Navigation.changeFragment(view, RegisterFragmentDirections.actionRegisterFragmentToContainerFragment())
        }.addOnFailureListener {
            Toast.makeText(view.context.applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
            progress.value = false
        }
    }

}