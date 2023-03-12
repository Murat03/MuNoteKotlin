package com.muratipek.munote.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.muratipek.munote.R
import com.muratipek.munote.databinding.FragmentNoteDetailsBinding
import com.muratipek.munote.viewmodel.NoteDetailsViewModel

class NoteDetailsFragment : Fragment() {

    private var fragmentBinding: FragmentNoteDetailsBinding ?= null
    lateinit var viewModel : NoteDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =ViewModelProvider(requireActivity()).get(NoteDetailsViewModel::class.java)

        val binding = FragmentNoteDetailsBinding.bind(view)
        fragmentBinding = binding

        binding.saveButton.setOnClickListener {
            binding.titleText.text
        }
    }

    private fun save(){

    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}