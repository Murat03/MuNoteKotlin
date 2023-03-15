package com.muratipek.munote.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muratipek.munote.R
import com.muratipek.munote.databinding.FragmentNoteDetailsBinding
import com.muratipek.munote.model.Note
import com.muratipek.munote.viewmodel.NoteDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class NoteDetailsFragment : Fragment(R.layout.fragment_note_details) {

    private var fragmentBinding: FragmentNoteDetailsBinding ?= null
    lateinit var viewModel : NoteDetailsViewModel
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =ViewModelProvider(requireActivity()).get(NoteDetailsViewModel::class.java)

        val binding = FragmentNoteDetailsBinding.bind(view)
        fragmentBinding = binding

        observeLiveData()

        binding.saveButton.setOnClickListener {
            viewModel.saveFirebase(binding.titleText.text.toString(),binding.noteText.text.toString(),db)
        }
    }
    fun observeLiveData(){
        viewModel.noteSaved.observe(viewLifecycleOwner){ isSaved ->
            isSaved?.let {
                if(it){
                    findNavController().popBackStack()
                }else{
                    //error
                }
            }
        }
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}