package com.example.instagramclone.ViewModel.MainViewModels

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
import com.example.instagramclone.Model.FirebaseObject
import com.example.instagramclone.Model.Post
import com.example.instagramclone.R
import com.example.instagramclone.View.MainFragments.HomeFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddPostViewModel: ViewModel() {

    lateinit var permissionLauncher: ActivityResultLauncher<String>
    lateinit var activityLauncher: ActivityResultLauncher<Intent>
    lateinit var supportFragmentManager: FragmentManager

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    var progress = MutableLiveData<Boolean>()

    fun selectImage(context: Context, activity: Activity, view: View){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view, "Permission Needed", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", View.OnClickListener {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()
            }
            else{
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        else{
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityLauncher.launch(intentToGallery)
        }
    }

    fun sharePost(context: Context, email: String, imageUri: Uri, comment: String){
        progress.value = true
        val uuid = UUID.randomUUID()
        val storageRef = storage.reference
        val imageRef = storageRef.child("${email}/$uuid.jpg")
        imageRef.putFile(imageUri).addOnSuccessListener {
            val uploadImageRef = storageRef.child("${email}/$uuid.jpg")
            uploadImageRef.downloadUrl.addOnSuccessListener {
                val post = Post(it.toString(), comment)
                post.email = email
                addPostToFirestore(context, post)
            }.addOnFailureListener {
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                progress.value = false
            }
        }.addOnFailureListener{
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            progress.value = false
        }
    }

    private fun addPostToFirestore(context: Context, post: Post){
        firestore.collection("Users").whereEqualTo("email", post.email).addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(context.applicationContext, error.localizedMessage, Toast.LENGTH_LONG).show()
                progress.value = false
            }
            else{
                if(value!=null){
                    val documentId = value.documents[0].id

                    firestore.collection("Users").document(documentId).get().addOnSuccessListener {
                        val firebaseObject = (it.toObject(FirebaseObject::class.java))
                        val postArray = firebaseObject?.posts
                        //////////////////////////////////////////////
                        postArray?.add(post)
                        postArray?.let {
                            for (i in 0..postArray.size-1){
                                println(postArray[i])
                            }
                        }

                        firestore.collection("Users").document(documentId).update("posts", postArray).addOnSuccessListener {
                            loadFragment(HomeFragment())
                        }.addOnFailureListener {
                            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                            progress.value = false
                        }
                        ////////////////////////////////////////////////
                    }.addOnFailureListener {
                        Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
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



























/*
val documentId = it.documents[0].id
            firestore.collection("Users").document(documentId).get().addOnSuccessListener {
                val postArray = (it.get("posts") as ArrayList<Post>).add(post)
                firestore.collection("Users").document(documentId).update("posts", postArray).addOnSuccessListener {

                }.addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
 */