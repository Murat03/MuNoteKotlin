package com.muratipek.munote.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muratipek.munote.R
import com.muratipek.munote.adapter.FeedRecyclerAdapter
import com.muratipek.munote.databinding.FragmentNoteFeedBinding
import com.muratipek.munote.model.Note
import com.muratipek.munote.viewmodel.NoteFeedViewModel

class NoteFeedFragment : Fragment(R.layout.fragment_note_feed) {

    private var fragmentBinding: FragmentNoteFeedBinding ?= null
    private lateinit var viewModel: NoteFeedViewModel
    private var recyclerFoodAdapter = FeedRecyclerAdapter(arrayListOf())
    private lateinit var db : FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNoteFeedBinding.bind(view)

        db = Firebase.firestore

        viewModel = ViewModelProvider(requireActivity()).get(NoteFeedViewModel::class.java)
        viewModel.getDataFromFirestore(db)

        binding.recyclerViewFeed.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFeed.adapter = recyclerFoodAdapter

        binding.fab.setOnClickListener {
            findNavController().navigate(NoteFeedFragmentDirections.actionNoteFeedFragmentToNoteDetailsFragment())
        }

        observeLiveData()
    }
    fun observeLiveData(){
        viewModel.exception.observe(viewLifecycleOwner){
            if(it != null){
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.notes.observe(viewLifecycleOwner){ note ->
            note?.let {
                recyclerFoodAdapter.updateNoteList(it)
            }
        }
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}