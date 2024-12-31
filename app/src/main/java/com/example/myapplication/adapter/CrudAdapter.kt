package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Crud
import com.example.myapplication.screen.DetailCrud
import com.example.myapplication.screen.UpdateCrudActivity

class CrudAdapter(
    private var crud:List<Crud>,
    context: Context
): RecyclerView.Adapter<CrudAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val txtItemTitle : TextView = itemView.findViewById(R.id.txtItemJudul)
        val txtItemContent : TextView = itemView.findViewById(R.id.txtItemIsiNote)
        val cardNote : CardView = itemView.findViewById(R.id.cardNote)
        val btnEdit : ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete : ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_crud,
            parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return crud.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val noteData = crud[position]
        holder.txtItemTitle.text = noteData.title
        holder.txtItemContent.text = noteData.content

        holder.cardNote.setOnClickListener() {
            val intent = Intent(holder.itemView.context, DetailCrud::class.java)
            intent.putExtra("title", noteData.title)
            intent.putExtra("content", noteData.content)

            holder.itemView.context.startActivity(intent)
        }

        //update
        holder.btnEdit.setOnClickListener(){
            val intent = Intent(holder.itemView.context, UpdateCrudActivity::class.java).apply {
                putExtra("note_id", noteData.id)
            }
            holder.itemView.context.startActivity(intent)
        }


        //delete
        holder.btnDelete.setOnClickListener(){
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Confirmation")
                setMessage("Do you want to continue ?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("Yes"){
                        dialogInterface, i->
                    db.deleteNote(noteData.id)
                    refreshData(db.getAllNotes())
                    Toast.makeText(holder.itemView.context, "Note Berhasil di Hapus",
                        Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                setNeutralButton("No"){
                        dialogInterface, i->
                    dialogInterface.dismiss()
                }
            }.show()//untuk menampilkan alert dialog
        }
    }


    //fitur untuk auto refres data
    fun refreshData(newNotes: List<Crud>){
        crud = newNotes
        notifyDataSetChanged()
    }
}