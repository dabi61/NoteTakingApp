package com.example.notetakingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notetakingapp.MainActivity
import com.example.notetakingapp.R
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.FragmentNewNoteBinding
import com.example.notetakingapp.databinding.FragmentUpdateNoteBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteViewModel


class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {
    private var _binding : FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel : NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    private lateinit var currentNote : Note
    // Kể từ khi các UpdateNoteFragment chứa các arguments trong nav graph,
    private val args : UpdateNoteFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.etNoteTitle.setText(currentNote.noteTitle)
        binding.etNoteBody.setText(currentNote.noteBody)

        // Nếu người dùng nhập vào cập nhật note
        binding.fabDone.setOnClickListener{
            val title = binding.etNoteTitle.text.toString().trim()
            val body = binding.etNoteBody.text.toString().trim()

            if(title.isNotEmpty()){
                val note = Note(currentNote.id, title, body)
                notesViewModel.updateNote(note)
                //Dùng khi muốn lưu fragment vào backstack
                //view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(context, "Please Enter Note Title", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun deleteNote(){
        activity?.let {
            AlertDialog.Builder(it).apply {
                setTitle("Delete Note ")
                setMessage("Are you sure you want to delete this note?")
                setPositiveButton("Delete"){_,_ ->
                    notesViewModel.deleteNote(currentNote)
                    view?.findNavController()?.navigate(
                        R.id.action_updateNoteFragment_to_homeFragment
                    )
                    requireActivity().supportFragmentManager.popBackStack()
                }
                setNegativeButton("Cancel", null)
            }.create().show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}