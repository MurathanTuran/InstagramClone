package com.example.instagramclone.ViewModel.MainViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.Adapter.PostsRecyclerAdapter
import com.example.instagramclone.Model.FirebaseObject
import com.example.instagramclone.Model.Post
import com.example.instagramclone.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val email = auth.currentUser!!.email.toString()

    private lateinit var recyclerAdapter: PostsRecyclerAdapter
    private var firebaseObjects = ArrayList<FirebaseObject>()
    private var posts = ArrayList<Post>()

    fun getPostsFromFirebase(binding: FragmentHomeBinding, context: Context, lifecycleOwner: LifecycleOwner){

        firestore.collection("Users").whereNotEqualTo("email", email).addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(context.applicationContext, error.toString(), Toast.LENGTH_LONG).show()
            }
            else{
                if(value!=null){
                    val documents = value.documents
                    for(i in 0..documents.size-1){
                        firebaseObjects.add(documents[i].toObject(FirebaseObject::class.java) as FirebaseObject)
                        firebaseObjects[i].posts?.let {
                            for(j in 0..it.size-1){
                                it[j].email = firebaseObjects[i].email.toString()
                                it[j].profilePhotoUrl = firebaseObjects[i].profilePhotoUrl.toString()
                            }
                            posts.addAll(it)
                        }
                    }
                    setRecyclerView(binding, context, lifecycleOwner)
                }
            }
        }
    }

    private fun setRecyclerView(binding: FragmentHomeBinding, context: Context, lifecycleOwner: LifecycleOwner){
        binding.recyclerViewHomeFragment.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = PostsRecyclerAdapter(posts, "HomeViewModel")
        binding.recyclerViewHomeFragment.adapter = recyclerAdapter
    }

}