package com.ubaya.plantific

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import org.json.JSONObject



/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        var idtanaman=DetailFragmentArgs.fromBundle(requireArguments()).idtanaman
        Log.d("idtanamandetail",idtanaman)
        var status=DetailFragmentArgs.fromBundle(requireArguments()).status
        Log.d("status",status)
        if(status=="0")
        {
            txtdummyprediksi.visibility=View.GONE
        }
        else if(status=="1")
        {
            txtdummyprediksi.visibility=View.VISIBLE
        }
        Log.d("idtanaman",idtanaman)
        val q = Volley.newRequestQueue(this.context)
        val url =  "http://192.168.0.103/Plantific/tanamanDetail.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("detail", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()) {
                        var data = data.getJSONObject(i)
                        txtNamaTanaman.text = data.getString("namaTanaman")
                        txtnama2.text = data.getString("namaTanaman")
                        txtnama3.text = data.getString("namaTanaman")
                        txtManfaatTanaman.text = data.getString("manfaatTanaman")
                        txtZatTanaman.text = data.getString("zatTanaman")
                        val url = data.getString("img_url")
                        Picasso.get().load(url).into(imgDetail)
                    }
                }
            },
            Response.ErrorListener {
                Log.d("cekparams", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["idtanaman"] = idtanaman
                return params
            }
        }
        q.add(stringRequest)
    }



}