package com.ubaya.plantific

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_scan.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    val pref = "userSession"
    val key_name = "key.name"
    val key_email = "key.email"
    var input:String=""
    lateinit var sharedPref : SharedPreferences
    // TODO: Rename and change types of parameters
    var tanamans:ArrayList<Tanaman> = ArrayList()
    private val searchListAdapter = tanamanAdapter(arrayListOf())
    var v:View ?= null

    fun searchtanaman(){
        val q = Volley.newRequestQueue(this.context)
        val url =  "http://192.168.0.103/Plantific/searchtanaman.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("search", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val playObj = data.getJSONObject(i)
                        val playlist = Tanaman(
                            playObj.getInt("idTanaman"),
                            playObj.getString("namaTanaman"),
                            playObj.getString("manfaatTanaman"),
                            playObj.getString("zatTanaman"),
                            playObj.getString("img_url")
                        )
                        tanamans.add(playlist)
                    }
                    updateList()
                }

            },
            Response.ErrorListener {
                Log.d("cekparams", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["input"] = input

                return params
            }
        }
        q.add(stringRequest)
    }

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
        recSearch?.layoutManager = lm
        recSearch?.setHasFixedSize(true)
        recSearch?.adapter = activity?.let { searchAdapter(tanamans) }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Select All Data From DB
        var txtsearch=v?.findViewById<EditText>(R.id.txtSearch)
        var search=v?.findViewById<RecyclerView>(R.id.recSearch)
        var btnSearch = v?.findViewById<Button>(R.id.btnsearch)
        // Attach ke Recycler View dengan Adapter
        search?.layoutManager = LinearLayoutManager(context)
        search?.adapter = searchListAdapter
        //Text On Change
        view?.btnLoadImageScan?.setOnClickListener { view ->
            input=txtsearch?.text.toString()
            searchtanaman()
            Log.d("search","button terpencet")
        }

//        txtsearch?.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(
//                s: CharSequence, start: Int, before: Int,
//                count: Int
//            ) {
//                (s.length() > 0){
//                    searchtanaman(txtsearch.text.toString())
//                    Log.d("cobasearch",txtsearch.text.toString())
//                }
//            }
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int, count: Int,
//                after: Int
//            ) {
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })

        // Inflate the layout for this fragment
//        v =inflater.inflate(R.layout.fragment_home, container, false)
//        var name = v?.findViewById<TextView>(R.id.textViewName)
//        name!!.text = "Hallo, " + sharedPref.getString(key_name,null)

//        bacadata()
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()
        tanamans.clear()
        bacadata()
    }
}