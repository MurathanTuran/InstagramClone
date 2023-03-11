package com.example.instagramclone.View.MainFragments.SettingsFragments

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
import com.example.instagramclone.ViewModel.MainViewModels.SettingsViewModels.SetProfilePhotoViewModel
import com.example.instagramclone.databinding.FragmentSetProfilePhotoBinding

class SetProfilePhotoFragment : Fragment() {

    private lateinit var binding: FragmentSetProfilePhotoBinding

    private lateinit var viewModel: SetProfilePhotoViewModel

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SetProfilePhotoViewModel::class.java)

        viewModel.supportFragmentManager = requireActivity().supportFragmentManager

        registerLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetProfilePhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.containerSetProfilePhotoFragment.visibility = View.GONE
                binding.progressBarSetProfilePhotoFragment.visibility = View.VISIBLE
            }
            else{
                binding.containerSetProfilePhotoFragment.visibility = View.VISIBLE
                binding.progressBarSetProfilePhotoFragment.visibility = View.GONE
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
                Toast.makeText(requireContext(), "Permission Needed", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val intentFromResult = it.data
                if(intentFromResult != null){
                    val uri = intentFromResult.data
                    if(uri != null){
                        binding.imageViewSetProfilePhotoFragment.setImageURI(uri)
                        imageUri = uri
                    }
                }
            }
        }
    }

    private fun onClick(){
        binding.imageViewSetProfilePhotoFragment.setOnClickListener {
            viewModel.selectProfilePhoto(requireContext(), requireActivity(), requireView())
        }
        binding.saveButtonSetProfilePhotoFragment.setOnClickListener {
            viewModel.saveProfilePhoto(requireContext(), imageUri)
        }
    }

}