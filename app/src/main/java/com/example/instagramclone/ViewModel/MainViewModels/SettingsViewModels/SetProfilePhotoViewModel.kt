package com.example.instagramclone.ViewModel.MainViewModels.SettingsViewModels

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagramclone.R
import com.example.instagramclone.View.MainFragments.SettingsFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class SetProfilePhotoViewModel: ViewModel() {

    lateinit var activityLauncher: ActivityResultLauncher<Intent>
    lateinit var permissionLauncher: ActivityResultLauncher<String>

    lateinit var supportFragmentManager: FragmentManager

    var progress = MutableLiveData<Boolean>()

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private lateinit var email: String

    fun selectProfilePhoto(context: Context, activity: Activity, view: View){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view, "Permission Needed", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", View.OnClickListener {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()
            }
            else{
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityLauncher.launch(intentToGallery)
            }
        }
        else{
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityLauncher.launch(intentToGallery)
        }
    }


    fun saveProfilePhoto(context: Context, imageUri: Uri?){
        progress.value = true
        if(imageUri!=null){
            email = auth.currentUser!!.email.toString()
            val storageRef = storage.reference
            val imageRef = storageRef.child("${email}/profilePhoto.jpg")
            imageRef.putFile(imageUri).addOnSuccessListener {

                val uploadImageRef = storageRef.child("${email}/profilePhoto.jpg")
                uploadImageRef.downloadUrl.addOnSuccessListener {
                    updateProfilePhotoUrlFirestore(context, it.toString(), email)
                }.addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                    progress.value = false
                }

            }.addOnFailureListener{
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                progress.value = false
            }
        }
        else{
            Toast.makeText(context.applicationContext, "Select Image", Toast.LENGTH_LONG).show()
            progress.value = false
        }

    }

    private fun updateProfilePhotoUrlFirestore(context: Context, profilePhotoUrl: String, email: String){
        firestore.collection("Users").whereEqualTo("email", email).addSnapshotListener { value, error ->

            if(error!=null){
                Toast.makeText(context.applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                progress.value = false
            }
            else{
                if(value!=null){
                    val documentId = value.documents[0].id
                    println(profilePhotoUrl)
                    println(documentId)

                    firestore.collection("Users").document(documentId).update("profilePhotoUrl", profilePhotoUrl).addOnSuccessListener {
                        loadFragment(SettingsFragment())
                    }.addOnFailureListener {
                        Toast.makeText(context.applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
                        progress.value = false
                    }
                }
            }

        }

    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerViewContainerFragment, fragment)
        transaction.commit()
    }
}
