package com.muratipek.munote.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.muratipek.munote.model.Note

class NoteFeedViewModel(): ViewModel() {
    private var firestoreException = MutableLiveData<String>()
    val exception : LiveData<String>
    get() = firestoreException

    private var noteList = MutableLiveData<List<Note>>()
    val notes : LiveData<List<Note>>
    get() = noteList
    fun getDataFromFirestore(firestore: FirebaseFirestore){
       firestore.collection("Notes").orderBy("date", Query.Direction.DESCENDING)
           .addSnapshotListener{snapshot, fException ->
                if(fException != null){
                    firestoreException.postValue(fException.localizedMessage)
                }else{
                    if((snapshot != null) && (!snapshot.isEmpty)){
                        val documents = snapshot.documents
                        val list = ArrayList<Note>()
                        for(document in documents){
                            val title = document.get("title") as String
                            val note = document.get("note") as String

                            list.add(Note(title, note))
                        }
                        noteList.postValue(list)
                    }
                }
           }
    }
}