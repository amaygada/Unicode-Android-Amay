package com.example.unicodetasks.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.icu.util.ValueIterator
import android.os.AsyncTask
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.unicodetasks.R

class Weather : Fragment() {

    var editCity : EditText ? =null
    var myActivity: Activity?=null
    var weatherButton : Button ? = null
    var progressBar : RelativeLayout? =null
    var mainLayout : RelativeLayout?=null
    var temp : TextView?=null
    var max_temp : TextView?=null
    var min_temp : TextView?=null
    var pressure : TextView?=null
    var humidity : TextView?=null
    var windSpeed : TextView?=null
    var error = false
    var CITY : String ? = null
    var API : String = "ceee5ec84d10261f0ca6fda426e9f1f5"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        closeKeyboard()
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_weather, container, false)
        editCity = view.findViewById(R.id.editCity)
        weatherButton = view.findViewById(R.id.weatherButton)
        progressBar = view.findViewById(R.id.progressBar)
        mainLayout = view.findViewById(R.id.mainLayout)
        temp = view.findViewById(R.id.temp)
        min_temp = view.findViewById(R.id.min_temp)
        max_temp= view.findViewById(R.id.max_temp)
        pressure= view.findViewById(R.id.pressure)
        humidity= view.findViewById(R.id.humidity)
        windSpeed= view.findViewById(R.id.wind)
        activity?.title = "Weather"
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myActivity = Activity()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weatherButton?.setOnClickListener {
            closeKeyboard()
            if(editCity?.text.toString().trim() !=""){
                CITY = editCity?.text.toString()
                makeAPIRequest()
            }
            else{
                Toast.makeText(myActivity , "Please Enter City Name" , Toast.LENGTH_LONG).show()
            }
        }
    }

    fun makeAPIRequest(){
        val weatherTask = WeatherTask()
        weatherTask.execute()
    }


    inner class WeatherTask : AsyncTask<String , Void , String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar?.visibility = View.VISIBLE
            mainLayout?.visibility = View.INVISIBLE
        }

        override fun doInBackground(vararg params: String?): String {
            var response:String
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response= ""
                error = true
            }
            return response
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
         try{
             if(result!=""){
             val jsonObj = JSONObject(result!!)
             val main = jsonObj.getJSONObject("main")
             val wind = jsonObj.getJSONObject("wind")
             val tempp = "TEMP: " + main.getString("temp")+"°C"
             val tempMinn = "MIN TEMP: " + main.getString("temp_min")+"°C"
             val tempMaxx = "MAX TEMP: " + main.getString("temp_max")+"°C"
             val pressuree = "PRESSURE: " + main.getString("pressure")+ " hPa"
             val humidityy = "HUMIDITY: " + main.getString("humidity")+ "%"
             val windSpeedd = "WIND SPEED: " + wind.getString("speed") + " m/s"

             temp?.setText(tempp)
             min_temp?.setText(tempMinn)
             max_temp?.setText(tempMaxx)
             pressure?.setText(pressuree)
             humidity?.setText(humidityy)
             windSpeed?.setText(windSpeedd)

             progressBar?.visibility = View.INVISIBLE
             mainLayout?.visibility = View.VISIBLE}
             else{
                 progressBar?.visibility = View.INVISIBLE
                 Toast.makeText(myActivity , "City Doesn't Exist" , Toast.LENGTH_LONG).show()
             }
         }catch(e:Exception){
            progressBar?.visibility = View.INVISIBLE
             Toast.makeText(myActivity , "City Doesn't Exist!" , Toast.LENGTH_LONG).show()
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