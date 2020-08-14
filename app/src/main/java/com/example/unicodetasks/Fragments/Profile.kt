package com.example.unicodetasks.Fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.unicodetasks.R
import kotlinx.android.synthetic.main.fragment_profile.*
import android.util.Log;
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

class Profile : Fragment() {

    var myActivity : Activity?=null
    var editName : EditText ? = null
    var editAge : EditText ? = null
    var editNumber : EditText ? = null
    var errorName : TextView ? =null
    var errorAge : TextView? =null
    var errorNumber : TextView ? =null
    var save : Button? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        closeKeyboard()
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_profile, container, false)
        activity?.title = "Profile"
        editName = layout.findViewById(R.id.editname)
        editAge = layout.findViewById(R.id.editAge)
        editNumber = layout.findViewById(R.id.editNumber)
        save = layout.findViewById(R.id.submitButton)
        errorName = layout.findViewById(R.id.nameError)
        errorAge = layout.findViewById(R.id.ageError)
        errorNumber = layout.findViewById(R.id.numberError)
        return layout
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myActivity = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("profile" , Context.MODE_PRIVATE )
      val name = sp1.getString("name","")
        editName?.setText(name)
        val age = sp1.getString("age" , "");
        val num = sp1.getString("number" , "");
        editAge?.setText(age)
        editNumber?.setText(num)
        val e : SharedPreferences.Editor = sp1.edit()
        save?.setOnClickListener {
            if(editName?.text.toString().trim()==""){
                errorName?.setText(" Enter Valid Name!")
            }
            else if(editAge?.text.toString().trim() =="" || editAge?.text.toString().length>3 || Integer.parseInt(editAge?.text.toString())>105){
                errorAge?.setText(" Enter valid Age!")
            }
            else if(editNumber?.text.toString().trim()==""|| editNumber?.text.toString().length!=10){
                errorNumber?.setText(" Enter valid Number!")
            }
            else{
                //System.out.println("hi")
                errorName?.setText("")
                errorAge?.setText("")
                errorNumber?.setText("")
                val p = editName?.text
                e.putString("name" , p.toString())
                e.putString("age" , editAge?.text.toString())
                e.putString("number" , editNumber?.text.toString())
                e.apply()
                e.commit()
                Toast.makeText( myActivity,"Success", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun closeKeyboard(){
        val view = myActivity?.getCurrentFocus()
        if(view!=null){
            val imm = (myActivity as Context).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken , 0)
        }
    }
}