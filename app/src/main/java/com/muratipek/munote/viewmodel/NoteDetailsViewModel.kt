package com.muratipek.munote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muratipek.munote.model.Note

class NoteDetailsViewModel: ViewModel() {
    private var isNoteSaved = MutableLiveData<Boolean>()
    val noteSaved : LiveData<Boolean>
    get() = isNoteSaved

    fun saveFirebase(title: String, note:String, firestore: FirebaseFirestore){
        val date = Timestamp.now()

        val hashNote = hashMapOf(
            "title" to title,
            "note" to note,
            "date" to date
        )
        firestore.collection("Notes")
            .add(hashNote)
            .addOnSuccessListener {
                //Note saved
                isNoteSaved.postValue(true)
            }.addOnFailureListener {
                //Note not saved
                isNoteSaved.postValue(false)
            }
    }
}