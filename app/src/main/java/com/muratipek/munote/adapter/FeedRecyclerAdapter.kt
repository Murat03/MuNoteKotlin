package com.muratipek.munote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratipek.munote.databinding.NoteRowBinding
import com.muratipek.munote.model.Note

class FeedRecyclerAdapter(val noteList: ArrayList<Note>): RecyclerView.Adapter<FeedRecyclerAdapter.NoteHolder>() {
    class NoteHolder(val binding: NoteRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = NoteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.binding.textView.text = noteList[position].title
        holder.itemView.setOnClickListener {
            println(noteList[position].title)
        }
    }
    fun updateNoteList(newNoteList: List<Note>){
        noteList.clear()
        noteList.addAll(newNoteList)
        notifyDataSetChanged()
    }
}