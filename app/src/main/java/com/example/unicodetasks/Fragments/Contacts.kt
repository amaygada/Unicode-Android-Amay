package com.example.unicodetasks.Fragments

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.example.unicodetasks.R
import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unicodetasks.ContactAdapter
import com.example.unicodetasks.ContactModel
import java.util.*
import kotlin.collections.ArrayList

class Contacts : Fragment() {

    companion object{
        var Permission_Request_Contacts = 100
    }
    var myActivity : Activity?=null
    lateinit var contactModel : ContactModel
    var list : ArrayList<ContactModel> = ArrayList<ContactModel>()
    var recyclelrView : RecyclerView ? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        closeKeyboard()
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_contacts, container, false)
        recyclelrView = view.findViewById(R.id.contactsRecyclerView)
        activity?.title = "Contacts"
        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myActivity = activity
    }

    fun checkPermissions(){
       if(ContextCompat.checkSelfPermission(myActivity!! ,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
           requestPermission()
       }
        else{
           getContacts()
       }
    }

    fun requestPermission(){
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
            Permission_Request_Contacts)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Permission_Request_Contacts){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getContacts()
            }
            else{
                Toast.makeText(myActivity!! , "Accept Permission to See Contact Details" , Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getContacts(){

        val resolver : ContentResolver = myActivity!!.getContentResolver()
        val cursor = resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
            null)

        if(cursor!!.count > 0){
            cursor.moveToFirst()
            while (cursor.moveToNext()){
                val name  = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number  = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactModel = ContactModel(name , number)
                list.add(contactModel)
            }
            cursor.close()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkPermissions()

        list.sortBy {it.name.toString()}
        val contactAdapterObj = ContactAdapter(myActivity!! , list)
        contactAdapterObj.notifyDataSetChanged()

        recyclelrView?.layoutManager = LinearLayoutManager(myActivity as Context)
        recyclelrView?.itemAnimator = DefaultItemAnimator()
        recyclelrView?.adapter = contactAdapterObj
    }

    private fun closeKeyboard(){
        val view = myActivity?.getCurrentFocus()
        if(view!=null){
            val imm = (myActivity as Context).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken , 0)
        }
    }

}