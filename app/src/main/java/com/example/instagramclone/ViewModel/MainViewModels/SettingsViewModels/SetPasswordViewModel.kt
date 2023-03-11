package com.example.instagramclone.ViewModel.MainViewModels.SettingsViewModels

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagramclone.R
import com.example.instagramclone.View.MainFragments.SettingsFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class SetPasswordViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    lateinit var supportFragmentManager: FragmentManager

    var progress = MutableLiveData<Boolean>()

    fun setPassword(oldPassword: String, oldPasswordConfirm: String, newPassword: String, context: Context){
        progress.value = true
        if(!newPassword.equals("") && !oldPasswordConfirm.equals("") && !oldPassword.equals("")){
            if(oldPassword.equals(oldPasswordConfirm)){
                val currentUser = auth.currentUser
                val credential = EmailAuthProvider.getCredential(currentUser!!.email.toString(), oldPassword)
                currentUser.reauthenticate(credential).addOnSuccessListener {
                    currentUser.updatePassword(newPassword).addOnSuccessListener {
                        loadFragment(SettingsFragment())
                    }.addOnFailureListener {
                        Toast.makeText(context.applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
                        progress.value = false
                    }
                }.addOnFailureListener {
                    Toast.makeText(context.applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
                    progress.value = false
                }
            }
            else{
                Toast.makeText(context.applicationContext, "The confirm password is not equal to old password", Toast.LENGTH_LONG).show()
                progress.value = false
            }
        }
        else{
            Toast.makeText(context.applicationContext, "Fill areas", Toast.LENGTH_LONG).show()
            progress.value = false
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerViewContainerFragment, fragment)
        transaction.commit()
    }

}

