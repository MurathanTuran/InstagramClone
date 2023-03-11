package com.example.instagramclone.View.MainFragments.SettingsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.ViewModel.MainViewModels.SettingsViewModels.SetPasswordViewModel
import com.example.instagramclone.databinding.FragmentSetPasswordBinding

class SetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentSetPasswordBinding

    private lateinit var viewModel: SetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SetPasswordViewModel::class.java)

        viewModel.supportFragmentManager = requireActivity().supportFragmentManager

        onClick()

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.containerSetPasswordFragment.visibility = View.GONE
                binding.progressBarSetPasswordFragment.visibility = View.VISIBLE
            }
            else{
                binding.containerSetPasswordFragment.visibility = View.VISIBLE
                binding.progressBarSetPasswordFragment.visibility = View.GONE
            }
        })

    }

    private fun onClick(){
        binding.setPasswordButtonSetPasswordFragment.setOnClickListener {
            val oldPassword = binding.oldPasswordTextSetPasswordFragment.text.toString()
            val oldPasswordConfirm = binding.oldPasswordConfirmTextSetPasswordFragment.text.toString()
            val newPassword = binding.newPasswordTextSetPasswordFragment.text.toString()

            viewModel.setPassword(oldPassword, oldPasswordConfirm, newPassword, requireContext())
        }
    }

}




