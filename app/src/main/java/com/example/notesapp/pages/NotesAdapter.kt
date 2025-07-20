package com.example.notesapp.pages


import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.example.notesapp.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.models.Note
import androidx.appcompat.app.AlertDialog

class NotesAdapter(
    private var notes: List<Note>,
    private val onDeleteClick: (String) -> Unit,
    private val onNoteClick: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val contentText: TextView = itemView.findViewById(R.id.contentText)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.imageDeleteButton)
    }

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleText.text = note.Name
        holder.contentText.text = note.Text

        holder.titleText.setOnClickListener {
            onNoteClick(note)
        }

        holder.contentText.setOnClickListener {
            onNoteClick(note)
        }


        holder.deleteBtn.setOnClickListener { view ->
            AlertDialog.Builder(view.context)
                .setTitle("Удалить заметку?")
                .setMessage("Вы уверены, что хотите удалить эту заметку?")
                .setPositiveButton("Удалить") { dialog, _ ->
                    onDeleteClick(note.id)
                    dialog.dismiss()
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

}

//class NotesDiffCallback(
//    private val oldList: List<Note>,
//    private val newList: List<Note>
//) : androidx.recyclerview.widget.DiffUtil.Callback() {
//
//    override fun getOldListSize(): Int = oldList.size
//    override fun getNewListSize(): Int = newList.size
//
//    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
//        return oldList[oldPos].id == newList[newPos].id
//    }
//
//    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
//        return oldList[oldPos] == newList[newPos]
//    }
//}
//
//
//

