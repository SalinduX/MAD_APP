package com.example.notes

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter (private var notes:List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(), Parcelable {

        private val db: NotesDatabaseHelper= NotesDatabaseHelper(context)

    class NoteViewHolder(iteamView: View) : RecyclerView.ViewHolder(iteamView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = iteamView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = iteamView.findViewById(R.id.deleteButton)
    }


    constructor(parcel: Parcel) : this(
        TODO("notes"),
        TODO("context")
    ) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }


    override fun getItemCount(): Int = notes.size

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotesAdapter> {
        override fun createFromParcel(parcel: Parcel): NotesAdapter {
            return NotesAdapter(parcel)
        }

        override fun newArray(size: Int): Array<NotesAdapter?> {
            return arrayOfNulls(size)
        }
    }

    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)

        }

    holder.deleteButton.setOnClickListener{
        db.deleteNote(note.id)
        refreshData(db.getAllNotes())
        Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
    }

    }

    fun refreshData(allNotes: List<Note>) {
        notes = allNotes
        notifyDataSetChanged()
    }
}



