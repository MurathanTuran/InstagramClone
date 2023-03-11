package com.example.instagramclone.ViewModel.MainViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.Adapter.PostsRecyclerAdapter
import com.example.instagramclone.Model.FirebaseObject
import com.example.instagramclone.Model.Post
import com.example.instagramclone.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val email = auth.currentUser!!.email.toString()

    private lateinit var recyclerAdapter: PostsRecyclerAdapter
    private lateinit var currentUserFirebaseObject: FirebaseObject
    private var currentUserPosts = ArrayList<Post>()

    fun getCurrentUserPostsFromFirebase(binding: FragmentAccountBinding, context: Context, lifecycleOwner: LifecycleOwner){
        firestore.collection("Users").whereEqualTo("email", email).addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(context.applicationContext, error.toString(), Toast.LENGTH_LONG).show()
            }
            else{
                if(value!=null){
                    firestore.collection("Users").document(value.documents[0].id).get().addOnSuccessListener {
                        currentUserFirebaseObject = it.toObject(FirebaseObject::class.java) as FirebaseObject
                        currentUserFirebaseObject.posts?.let {
                            currentUserPosts = it
                        }
                        setRecyclerView(binding, context)
                    }.addOnFailureListener {
                        Toast.makeText(context.applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setRecyclerView(binding: FragmentAccountBinding, context: Context){
        binding.recyclerViewAccountFragment.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = PostsRecyclerAdapter(currentUserPosts, "AccountViewModel")
        binding.recyclerViewAccountFragment.adapter = recyclerAdapter
    }

}