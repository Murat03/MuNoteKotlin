package com.muratipek.munote.viewmodel

import android.app.Activity
import androidx.core.app.RemoteInput.EditChoicesBeforeSending
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muratipek.munote.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class SignInViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth

    private var userEmail = MutableLiveData<String>()
    val mail : LiveData<String>
        get() = userEmail

    private var successLogin = MutableLiveData<Boolean>()
    val successLgn : LiveData<Boolean>
        get() = successLogin

    fun signInOrCreateUser(user: User, requireActivity: FragmentActivity){
        auth = Firebase.auth
        if(auth.currentUser == null){
            auth.createUserWithEmailAndPassword(user.email,user.password)
                .addOnCompleteListener(requireActivity) { task ->
                    if(task.isSuccessful){
                        println("User Created")
                        userEmail.postValue(user.email)
                        successLogin.postValue(true)
                    }else{
                        auth.signInWithEmailAndPassword(user.email, user.password)
                            .addOnCompleteListener { task ->
                                if(task.isSuccessful){
                                    println("User Sign In")
                                    userEmail.postValue(user.email)
                                    successLogin.postValue(true)
                                }else{
                                    //Can't login
                                    println("Can't login")
                                    successLogin.postValue(false)
                                }
                            }
                    }
                }
        }

    }

}