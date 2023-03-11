package com.example.instagramclone.View.MainFragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.ViewModel.MainViewModels.AddPostViewModel
import com.example.instagramclone.databinding.FragmentAddPostBinding
import com.google.firebase.auth.FirebaseAuth

class AddPostFragment : Fragment() {

    private lateinit var binding: FragmentAddPostBinding

    private lateinit var viewModel: AddPostViewModel

    private lateinit var imageUri: Uri

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddPostViewModel::class.java)

        viewModel.supportFragmentManager = requireActivity().supportFragmentManager

        registerLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.containerAddPostFragment.visibility = View.GONE
                binding.progressBarAddPostFragment.visibility = View.VISIBLE
            }
            else{
                binding.containerAddPostFragment.visibility = View.VISIBLE
                binding.progressBarAddPostFragment.visibility = View.GONE
            }
        })

    }

    private fun registerLauncher(){
        viewModel.permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                viewModel.activityLauncher.launch(intentToGallery)
            }
            else{
                Toast.makeText(requireContext().applicationContext, "Permission Needed", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val intentFromResult = it.data
                if(intentFromResult!=null){
                    val uri = intentFromResult.data
                    if(uri!=null){
                        binding.imageViewAddPostFragment.setImageURI(uri)
                        imageUri = uri
                    }
                }
            }
        }
    }

    private fun onClick(){
        binding.imageViewAddPostFragment.setOnClickListener {
            viewModel.selectImage(requireContext(), requireActivity(), requireView())
        }

        binding.shareButtonAddPostFragment.setOnClickListener {
            viewModel.sharePost(requireContext(), auth.currentUser!!.email.toString(), imageUri, binding.commentTextAddPostFragment.text.toString())
        }
    }

}