package com.example.instagramclone.View.LoginAndRegisterFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.ViewModel.LoginAndRegisterViewModels.LoginOrRegisterViewModel
import com.example.instagramclone.databinding.FragmentLoginOrRegisterBinding
import androidx.lifecycle.ViewModelProvider

class LoginOrRegisterFragment : Fragment() {

    private lateinit var binding: FragmentLoginOrRegisterBinding

    private lateinit var viewModel: LoginOrRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginOrRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginOrRegisterViewModel::class.java)

        onClick()
    }

    private fun onClick(){
        binding.loginButtonLoginOrRegisterFragment.setOnClickListener {
            viewModel.toLoginPage(it)
        }

        binding.registerButtonLoginOrRegisterFragment.setOnClickListener {
            viewModel.toRegisterPage(it)
        }
    }

}