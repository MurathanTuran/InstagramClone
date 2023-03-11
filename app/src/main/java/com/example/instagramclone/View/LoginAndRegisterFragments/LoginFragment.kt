package com.example.instagramclone.View.LoginAndRegisterFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.ViewModel.LoginAndRegisterViewModels.LoginViewModel
import com.example.instagramclone.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        onClick()

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.containerFragmentLogin.visibility = View.GONE
                binding.progressBarLoginFragment.visibility = View.VISIBLE
            }
            else{
                binding.containerFragmentLogin.visibility = View.VISIBLE
                binding.progressBarLoginFragment.visibility = View.GONE
            }
        })
    }

    private fun onClick(){
        binding.loginButtonLoginFragment.setOnClickListener {
            val email = binding.emailTextLoginFragment.text.toString()
            val password = binding.passwordTextLoginFragment.text.toString()
            viewModel.login(email, password, it)
        }
    }

}