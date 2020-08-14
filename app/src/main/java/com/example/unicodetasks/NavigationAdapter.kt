package com.example.unicodetasks

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.unicodetasks.Fragments.Contacts
import com.example.unicodetasks.Fragments.Profile
import com.example.unicodetasks.Fragments.Weather

class NavigationAdapter(contentList : ArrayList<String> , context: Context) : RecyclerView.Adapter<NavigationAdapter.NavViewHolder>() {

    var contentList : ArrayList<String>?=null
    var context : Context?=null

    init{
        this.contentList = contentList
        this.context = context
    }

    class NavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var navText : TextView? = null
        var navContentHolder : RelativeLayout ? = null

        init{
            navText = itemView.findViewById(R.id.text_navdrawer)
            navContentHolder = itemView.findViewById(R.id.navdrawer_item_content_holder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_custom_navigationbar , parent,false)
        return NavViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return (contentList as ArrayList<String>).size
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder.navText?.setText(contentList?.get(position))
        holder.navContentHolder?.setOnClickListener{
            if(position==0){
                val profile = Profile()
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detailsFragment , profile)
                    .commit()
            }
            if(position==1){
                val contacts = Contacts()
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detailsFragment , contacts)
                    .commit()
            }
            if(position==2){
                val weather = Weather()
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detailsFragment , weather)
                    .commit()
            }
            MainActivity.Statified.drawerLayout?.closeDrawers()
        }
    }
}
