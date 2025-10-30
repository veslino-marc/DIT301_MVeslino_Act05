package com.example.notekeeperapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.appbar.MaterialToolbar

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var etTitle: TextInputEditText
    private lateinit var etContent: TextInputEditText
    private lateinit var btnSave: MaterialButton
    private lateinit var btnDelete: MaterialButton
    private lateinit var toolbar: MaterialToolbar
    private lateinit var dbHelper: DatabaseHelper

    private var noteId: Int = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        etTitle = findViewById(R.id.etNoteTitle)
        etContent = findViewById(R.id.etNoteContent)
        btnSave = findViewById(R.id.btnSaveNote)
        btnDelete = findViewById(R.id.btnDeleteNote)
        toolbar = findViewById(R.id.toolbar)

        dbHelper = DatabaseHelper(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        noteId = intent.getIntExtra("NOTE_ID", -1)
        if (noteId != -1) {
            isEditMode = true
            toolbar.title = "Edit Note"
            btnDelete.visibility = android.view.View.VISIBLE
            loadNoteData()
        } else {
            toolbar.title = "Add Note"
        }

        btnSave.setOnClickListener { saveNote() }

        btnDelete.setOnClickListener {
            if (isEditMode) {
                val result = dbHelper.deleteNote(noteId)
                if (result > 0) {
                    Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadNoteData() {
        val note = dbHelper.getNoteById(noteId)
        note?.let {
            etTitle.setText(it.title)
            etContent.setText(it.content)
        }
    }

    private fun saveNote() {
        val title = etTitle.text.toString().trim()
        val content = etContent.text.toString().trim()

        if (title.isEmpty()) {
            etTitle.error = "Title is required"
            etTitle.requestFocus()
            return
        }
        if (content.isEmpty()) {
            etContent.error = "Content is required"
            etContent.requestFocus()
            return
        }

        if (isEditMode) {
            val note = Note(noteId, title, content, System.currentTimeMillis())
            val result = dbHelper.updateNote(note)
            if (result > 0) {
                Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show()
            }
        } else {
            val note = Note(0, title, content, System.currentTimeMillis())
            val id = dbHelper.addNote(note)
            if (id > 0) {
                Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
