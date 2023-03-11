package com.example.instagramclone.View.LoginAndRegisterFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.ViewModel.LoginAndRegisterViewModels.RegisterViewModel
import com.example.instagramclone.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        onClick()

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.containerRegisterFragment.visibility = View.GONE
                binding.progressBarRegisterFragment.visibility = View.VISIBLE
            }
            else{
                binding.containerRegisterFragment.visibility = View.VISIBLE
                binding.progressBarRegisterFragment.visibility = View.GONE
            }
        })
    }

    private fun onClick(){
        binding.registerButtonRegisterFragment.setOnClickListener {
            val email = binding.emailTextRegisterFragment.text.toString()
            val password = binding.passwordTextRegisterFragment.text.toString()
            viewModel.register(email, password, it)
        }
    }

}