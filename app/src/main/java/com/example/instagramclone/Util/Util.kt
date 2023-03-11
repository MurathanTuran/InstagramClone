package com.example.instagramclone.Util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

fun Navigation.changeFragment(view: View, action: NavDirections){
    findNavController(view).navigate(action)
}

fun ImageView.setImageWithUrl(url: String){
    if(url!=null && !url.equals("")){
        Picasso.get().load(url).into(this)
    }
}

fun ImageView.setImageWithUrl(url: String, progressBar: ProgressBar){
    if(url!=null && !url.equals("")){
        Picasso.get()
            .load(url)
            .into(this, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    TODO("Not yet implemented")
                }
            })
    }
    else{
        progressBar.visibility = View.GONE
    }
}

