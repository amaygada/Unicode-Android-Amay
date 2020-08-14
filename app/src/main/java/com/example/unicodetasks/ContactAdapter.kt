package com.example.unicodetasks

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(context : Context, list : ArrayList<ContactModel>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    var list : ArrayList<ContactModel> ? =null
    var context : Context ? =null
    init {
        this.list = list
        this.context = context
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var circleText : TextView? = null
        var nameText : TextView? = null
        var container : RelativeLayout ? =null
        init {
            circleText = itemView.findViewById(R.id.circleText)
            nameText = itemView.findViewById(R.id.textContact)
            container = itemView.findViewById(R.id.containerContact)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_custom_contacts , parent , false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return (list as ArrayList).size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.circleText?.setText(list?.get(position)?.name?.substring(0,1))
        holder.nameText?.setText(list?.get(position)?.name)
        holder.container?.setOnClickListener{
            val namee = list?.get(position)?.name
            val numberr = list?.get(position)?.number
            val i = Intent(context , ContactDetails :: class.java)
            i.putExtra("namee" , namee)
            i.putExtra("number" , numberr)
            context?.startActivity(i)
        }
    }
}