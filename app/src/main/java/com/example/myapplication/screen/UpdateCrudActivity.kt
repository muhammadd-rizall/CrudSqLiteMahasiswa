package com.example.myapplication.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCrudAddBinding
import com.example.myapplication.helper.CrudDatabaseHelper

class UpdateCrudActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCrudAddBinding
    private lateinit var db : CrudDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db =CrudDatabaseHelper(this)
        noteId = intent.getIntExtra("note_id",-1)
        if(noteId == -1){
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        binding.etEditJudul.setText(note.title)
        binding.etEditCatatan.setText(note.content)

        //proses updaet ke database
        binding.btnUpdateNote.setOnClickListener(){
            val newTitle = binding.etEditJudul.text.toString()//setelah apa yang d dapatkan
            val newContent = binding.etEditCatatan.text.toString()//setelah apa yang d dapatkan

            val updateNote = Note(noteId, newTitle, newContent)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Update Berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}