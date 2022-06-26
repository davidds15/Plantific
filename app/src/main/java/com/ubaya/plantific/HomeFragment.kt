package com.ubaya.plantific

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

val pref = "userSession"
val key_name = "key.name"
val key_id="key.id"
val threshold="threshold"
var model="mobilenet"
lateinit var sharedPref : SharedPreferences


class HomeFragment : Fragment() {


    var tanamans:ArrayList<Tanaman> = ArrayList()
    var v:View ?= null

    fun bacadata(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://plantific.ifubaya.id/homeTanaman.php"
        var stringRequest = StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()) {
                        val playObj = data.getJSONObject(i)
                        val playlist = Tanaman(playObj.getInt("idTanaman"),
                            playObj.getString("namaTanaman"),
                            playObj.getString("manfaatTanaman"),
                            playObj.getString("zatTanaman"),
                            playObj.getString("img_url"))

                        tanamans.add(playlist)
                    }
                    updateList()
                }
            },
            Response.ErrorListener {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }

    fun updateList() {
        val lm = LinearLayoutManager(activity)
        recPlants?.layoutManager = lm
        recPlants?.setHasFixedSize(true)
        recPlants?.adapter = activity?.let { tanamanAdapter(tanamans) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v=inflater.inflate(R.layout.fragment_home, container, false)
        var thresholdd=""
        var modell=""
        sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
        sharedPref.getString(key_name,"")?.let { Log.d("nama", it) }
        sharedPref.getString(key_id,"")?.let { Log.d("id", it) }
        sharedPref.getString(threshold,"")?.let {
            thresholdd=it
            Log.d("threshold main",thresholdd.toString()) }.toString()
        sharedPref.getString(model,"")?.let {
            modell=it
            Log.d("modell main",modell.toString()) }.toString()
        if(thresholdd==""||thresholdd==null||modell==""||modell==null)
        {
            val edit:SharedPreferences.Editor = sharedPref.edit()
            edit.putString(threshold,"0.5")
            edit.putString(model,"mobilenet")
            edit.apply()
        }

        bacadata()
        return v
    }


    override fun onResume() {
        super.onResume()
        tanamans.clear()
        bacadata()
    }
}