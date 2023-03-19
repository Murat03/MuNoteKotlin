package com.muratipek.munote.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.muratipek.munote.model.Note
import com.muratipek.munote.service.NoteDatabase
import com.muratipek.munote.util.SpecialSharedPreferences
import kotlinx.coroutines.launch

class NoteFeedViewModel(application: Application): CoroutineViewModel(application) {
    private var firestoreException = MutableLiveData<String>()
    val exception : LiveData<String>
    get() = firestoreException

    private var noteList = MutableLiveData<List<Note>>()
    val notes : LiveData<List<Note>>
    get() = noteList

    private var updateTime = 10 * 60 * 1000 * 1000 * 1000L

    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())

    fun refreshData(firestore : FirebaseFirestore){
        val savedTime = specialSharedPreferences.getTime()
        println(savedTime)
        if(savedTime != null && savedTime != 0L && System.nanoTime() - savedTime < updateTime){
            println("From SQLite")
            getDataFromSQLite()
        }else{
            println("From Firestore")
            getDataFromFirestore(firestore)
        }
    }
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
                        saveSqlite(list)
                    }
                }
           }
    }
    fun getDataFromSQLite(){
        launch {
            val noteListSQL = NoteDatabase(getApplication()).noteDao().getAllNotes()
            noteList.postValue(noteListSQL)
        }
    }
    fun saveSqlite(noteListSQL: List<Note>){
        launch {
            val dao =NoteDatabase(getApplication()).noteDao()
            dao.deleteAllNotes()
            val uuidList = dao.insertAll(*noteListSQL.toTypedArray())
            var i = 0
            while (i < noteListSQL.size){
                noteListSQL[i].uuid = uuidList[i].toInt()
                i = i + 1
            }
            noteList.postValue(noteListSQL)
        }
        specialSharedPreferences.saveTime(System.nanoTime())
    }
}