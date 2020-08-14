package com.example.unicodetasks

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unicodetasks.Fragments.Profile

class MainActivity : AppCompatActivity() {

    var NavigationDrawerList : ArrayList<String> = arrayListOf()
    object Statified {
        var drawerLayout: DrawerLayout? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        MainActivity.Statified.drawerLayout = findViewById(R.id.drawer_layout)
        NavigationDrawerList.add("Profile")
        NavigationDrawerList.add("Contacts")
        NavigationDrawerList.add("Weather")

        val profile = Profile()
            this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailsFragment , profile)
            .commit()

        val toggle = ActionBarDrawerToggle(this@MainActivity, Statified.drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        MainActivity.Statified.drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        val navigationAdapterObj = NavigationAdapter(NavigationDrawerList , this)
        navigationAdapterObj.notifyDataSetChanged()

        val navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter = navigationAdapterObj
        navigation_recycler_view.setHasFixedSize(true)
    }
}