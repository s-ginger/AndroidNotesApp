package com.example.notesapp.pages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.viewmodels.NotesViewModel
import com.example.notesapp.viewmodels.NotesViewModelFactory


class NotesFragment : Fragment() {

    private var  _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NotesAdapter

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity() as SecondActivity).repo)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NotesAdapter(emptyList(), { id ->
            viewModel.deleteNote(id = id)
        },
        { note ->
            val intent = Intent(requireContext(), AddNoteActivity::class.java)
            intent.putExtra("note", note)
            startActivity(intent)
        })

        binding.notesRecyclerView.adapter = adapter

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.updateData(notes)
        }

        viewModel.loadNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}