package com.ubaya.plantific

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    val pref = "userSession"
    val key_name = "key.name"
    val key_email = "key.email"
    lateinit var sharedPref : SharedPreferences
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var iduser=""

    var historys:ArrayList<History> = ArrayList()
    var v:View ?= null

    fun bacadata(){
        val q = Volley.newRequestQueue(this.context)
        val url = "http://192.168.0.103/Plantific/history.php"
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bacadata()
        // Inflate the layout for this fragment
        sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
        sharedPref.getString(key_id,"")?.let {
            iduser=it
            Log.d("id", it) }.toString()
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onResume() {
        super.onResume()
        historys.clear()
        bacadata()
    }
}