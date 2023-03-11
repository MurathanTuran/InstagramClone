package com.example.instagramclone.View.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.R
import com.example.instagramclone.ViewModel.MainViewModels.ContainerViewModel
import com.example.instagramclone.databinding.FragmentContainerBinding

class ContainerFragment : Fragment() {

    private lateinit var binding: FragmentContainerBinding

    private lateinit var viewModel: ContainerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ContainerViewModel::class.java)
        //ContextSingleton.context = requireActivity().supportFragmentManager
        viewModel.supportFragmentManager = requireActivity().supportFragmentManager

        viewModel.setFragment(R.id.homeFragment)

        onClick()
    }

    private fun onClick(){
        binding.bottomNavigationViewContainerFragment.setOnItemSelectedListener{
            viewModel.setFragment(it.itemId)
            true
        }
    }

}