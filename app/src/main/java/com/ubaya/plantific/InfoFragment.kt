package com.ubaya.plantific

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_scan.view.*
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.view.*


class SearchFragment : Fragment() {
    val pref = "userSession"
    val key_name = "key.name"
    val key_email = "key.email"
    var input:String=""
    lateinit var sharedPref : SharedPreferences
    var tanamans:ArrayList<Tanaman> = ArrayList()
    private val searchListAdapter = searchAdapter(arrayListOf())

//    fun searchtanaman(){
//        val q = Volley.newRequestQueue(this.context)
//        val url =  "http://192.168.0.103/Plantific/searchtanaman.php"
//        var stringRequest = object : StringRequest(
//            Request.Method.POST, url,
//            Response.Listener {
//                Log.d("search", it)
//                val obj = JSONObject(it)
//                if(obj.getString("result") == "OK") {
//                    val data = obj.getJSONArray("data")
//                    for (i in 0 until data.length()) {
//                        val playObj = data.getJSONObject(i)
//                        val playlist = Tanaman(
//                            playObj.getInt("idTanaman"),
//                            playObj.getString("namaTanaman"),
//                            playObj.getString("manfaatTanaman"),
//                            playObj.getString("zatTanaman"),
//                            playObj.getString("img_url")
//                        )
//                        tanamans.add(playlist)
//                    }
//                    updateFeaturedList(tanamans)
//                    updateList()
//                }
//
//            },
//            Response.ErrorListener {
//                Log.d("cekparams", it.message.toString())
//            }) {
//            override fun getParams(): MutableMap<String, String> {
//                val params = HashMap<String, String>()
//                params["input"] = input
//
//                return params
//            }
//        }
//        q.add(stringRequest)
//    }
//
//    fun bacadata(){
//        val q = Volley.newRequestQueue(activity)
//        val url = "http://192.168.0.103/Plantific/homeTanaman.php"
//        var stringRequest = StringRequest(
//            Request.Method.POST, url,
//            Response.Listener<String> {
//                Log.d("apiresult", it)
//                val obj = JSONObject(it)
//                if(obj.getString("result") == "OK") {
//                    val data = obj.getJSONArray("data")
//                    for(i in 0 until data.length()) {
//                        val playObj = data.getJSONObject(i)
//                        val playlist = Tanaman(playObj.getInt("idTanaman"),
//                            playObj.getString("namaTanaman"),
//                            playObj.getString("manfaatTanaman"),
//                            playObj.getString("zatTanaman"),
//                            playObj.getString("img_url"))
//
//                        tanamans.add(playlist)
//                    }
//
//                    updateList()
//
//                }
//            },
//            Response.ErrorListener {
//                Log.e("apiresult", it.message.toString())
//            })
//        q.add(stringRequest)
//    }
//
//    fun updateList() {
//
//        val lm = LinearLayoutManager(activity)
//        v?.recSearch?.layoutManager = lm
//        v?.recSearch?.setHasFixedSize(true)
//        v?.recSearch?.adapter = activity?.let { searchAdapter(tanamans) }
//
//    }
//    fun updateFeaturedList(newresepList: List<Tanaman>) {
//        tanamans.clear()
//        tanamans.addAll(newresepList)
//    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Select All Data From DB
         var v = inflater.inflate(R.layout.fragment_info, container, false)

//        var txtsearch=v?.findViewById<EditText>(R.id.txtSearch)
//        var search=v?.findViewById<RecyclerView>(R.id.recSearch)
//        var btnSearch = v?.findViewById<Button>(R.id.btnsearch)

        // Attach ke Recycler View dengan Adapter
//        v?.recSearch?.layoutManager = LinearLayoutManager(context)
//        v?.recSearch?.adapter = searchListAdapter
//        //Text On Change
//        v?.btnsearch?.setOnClickListener { view ->
//            Log.d("search","button terpencet")
//            input=txtsearch?.text.toString()
//            searchtanaman()
//        }

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
        return v
    }

    override fun onResume() {
        super.onResume()

    }
}