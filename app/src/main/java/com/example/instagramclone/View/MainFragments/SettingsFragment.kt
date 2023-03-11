package com.example.instagramclone.View.MainFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.R
import com.example.instagramclone.View.MainActivity
import com.example.instagramclone.ViewModel.MainViewModels.SettingsViewModel
import com.example.instagramclone.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        viewModel.supportFragmentManager = requireActivity().supportFragmentManager

        onClick()

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.containerSettingsFragment.visibility = View.GONE
                binding.progressBarSettingsFragment.visibility = View.VISIBLE
            }
        })

    }

    private fun onClick(){
        binding.setPasswordButtonSettingsFragment.setOnClickListener {
            viewModel.setFragment(R.id.setPasswordFragment)
        }

        binding.setProfilePhotoButtonSettingsFragment.setOnClickListener {
            viewModel.setFragment(R.id.setProfilePhotoFragment)
        }

        binding.logoutButtonSettingsFragment.setOnClickListener {
            viewModel.logout()

            val intent = Intent(requireView().context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }


}