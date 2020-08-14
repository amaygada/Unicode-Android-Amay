package com.example.unicodetasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.app_bar_main.*

class ContactDetails : AppCompatActivity() {

    var circleText : TextView ? =null
    var nameText : TextView? = null
    var numberText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        val actionBar = supportActionBar
        actionBar!!.title = "Details"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        circleText = findViewById(R.id.circleText)
        nameText = findViewById(R.id.nameDetails)
        numberText = findViewById(R.id.numberDetails)


        circleText?.setText(intent.getStringExtra("namee")?.substring(0,1))
        nameText?.setText(intent.getStringExtra("namee"))
        numberText?.setText(intent.getStringExtra("number"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}