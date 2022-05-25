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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

val pref = "userSession"
val key_name = "key.name"
val key_email = "key.email"
val key_id="key.id"
lateinit var sharedPref : SharedPreferences
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var tanamans:ArrayList<Tanaman> = ArrayList()
    var v:View ?= null

    fun bacadata(){
        val q = Volley.newRequestQueue(activity)
        val url = "http://192.168.0.103/Plantific/homeTanaman.php"
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
        // Inflate the layout for this fragment
//        v =inflater.inflate(R.layout.fragment_home, container, false)
        sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
        sharedPref.getString(key_name,"")?.let { Log.d("nama", it) }
        sharedPref.getString(key_id,"")?.let { Log.d("id", it) }
        bacadata()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onResume() {
        super.onResume()
        tanamans.clear()
        bacadata()
    }
}