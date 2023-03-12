package com.muratipek.munote.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.muratipek.munote.R
import com.muratipek.munote.databinding.FragmentSignInBinding
import com.muratipek.munote.model.User
import com.muratipek.munote.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var auth: FirebaseAuth
    private var fragmentBinding: FragmentSignInBinding ?= null
    private lateinit var viewModel: SignInViewModel

    private var userEmail: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            userEmail = auth.currentUser?.email
            goFeedFragment()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SignInViewModel::class.java)
        val binding = FragmentSignInBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.signInButton.setOnClickListener {
            val email = binding.emailAddressText.text.toString()
            val password = binding.passwordText.text.toString()
            val user = User(email, password)
            viewModel.signInOrCreateUser(user, requireActivity())
        }


    }
    fun goFeedFragment(){
        userEmail?.let {
            val action = SignInFragmentDirections.actionSignInFragmentToNoteFeedFragment(it)
            findNavController().navigate(action)
        }
    }
    private fun subscribeToObservers(){
        viewModel.mail.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                userEmail = it
            }
        })

        viewModel.successLgn.observe(viewLifecycleOwner, Observer { success ->
            if(success){
                    goFeedFragment()
            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}