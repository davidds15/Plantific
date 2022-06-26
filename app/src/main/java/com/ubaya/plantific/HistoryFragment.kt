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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject


class HistoryFragment : Fragment() {
    val pref = "userSession"
    lateinit var sharedPref : SharedPreferences
    var iduser=""
    var historys:ArrayList<History> = ArrayList()
    var v:View ?= null

    fun bacadata(){
        val q = Volley.newRequestQueue(this.context)
        val url = "https://plantific.ifubaya.id/history.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("cekparams", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()) {
                        val playObj = data.getJSONObject(i)
                        val playlist = History(playObj.getInt("idHistory"),
                            playObj.getString("datePredict"),
                            playObj.getString("hasilPredict"),
                            playObj.getString("Tanaman_idTanaman"),
                            playObj.getString("img_url"),
                            playObj.getString("idTanaman"))

                        historys.add(playlist)
                    }
                    updateList()
                }
                else
                {
                    if(iduser!="0")
                    {
                        txtnotehistory.visibility=View.VISIBLE
                        txtnotehistory.text="Anda Belum Memiliki Riwayat Pengenalan. Silahkan lakukan pengenalan tanaman pertama anda."
                    }
                    else
                    {
                        txtnotehistory.text="Anda saat ini login sebagai tamu. Jika ingin menggunakan fitur riwayat, silahkan Login menggunakan email yang telah terdaftar."
                    }
                }
            },
            Response.ErrorListener {
                Log.d("cekparams", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["iduser"] = iduser
                return params
            }
        }
        q.add(stringRequest)
    }




    fun updateList() {
        val lm = LinearLayoutManager(activity)
        recHistory?.layoutManager = lm
        recHistory?.setHasFixedSize(true)
        recHistory?.adapter = activity?.let { HistoryAdapter(historys) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v=inflater.inflate(R.layout.fragment_history, container, false)

        var txtnote=v?.findViewById<TextView>(R.id.txtnotehistory)
        sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
        sharedPref.getString(key_id,"")?.let {
            iduser=it
            Log.d("iduser", iduser) }.toString()
        if(iduser=="0")
        {
            txtnote?.visibility=View.VISIBLE
            txtnote?.text="Anda saat ini login sebagai tamu. Jika ingin menggunakan fitur riwayat, silahkan Login menggunakan email yang telah terdaftar."
            Log.d("history","masuk if")
        }
        else
        {
            txtnote?.visibility=View.GONE
            bacadata()
            Log.d("history","masuk else")
        }

        return v
    }

    override fun onResume() {
        super.onResume()
        historys.clear()
        bacadata()
    }
}