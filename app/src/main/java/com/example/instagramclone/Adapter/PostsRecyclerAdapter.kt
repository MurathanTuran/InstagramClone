package com.example.instagramclone.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Model.Post
import com.example.instagramclone.Util.setImageWithUrl
import com.example.instagramclone.databinding.RecyclerRowBinding

class PostsRecyclerAdapter(private val posts: ArrayList<Post>, private val fromWhere: String): RecyclerView.Adapter<PostsRecyclerAdapter.PostsRecyclerViewHolder>() {

    class PostsRecyclerViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsRecyclerViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsRecyclerViewHolder, position: Int) {

        if(fromWhere.equals("HomeViewModel")){
            holder.binding.emailTextRecyclerRow.text = posts[position].email.toString()
            holder.binding.profilePhotoRecyclerRow.setImageWithUrl(posts[position].profilePhotoUrl.toString(), holder.binding.profilePhotoProgressBarRecyclerRow)
        }
        else if(fromWhere.equals("AccountViewModel")){
            holder.binding.emailTextRecyclerRow.visibility = View.GONE
            holder.binding.profilePhotoRecyclerRow.visibility = View.GONE
            holder.binding.profilePhotoProgressBarRecyclerRow.visibility = View.GONE
        }
        holder.binding.imageViewRecyclerRow.setImageWithUrl(posts[position].imageUrl.toString(), holder.binding.imageProgressBarRecyclerRow)
        holder.binding.commentTextRecyclerRow.text = posts[position].comment.toString()

    }

    override fun getItemCount(): Int {
        return posts.size
    }
}