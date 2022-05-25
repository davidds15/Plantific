package com.ubaya.plantific

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val pref = "userSession"
    val key_name = "key.name"
    val key_email = "key.email"
    val key_id = "key.id"
    var v:View ?= null
    lateinit var sharedPref : SharedPreferences
    private fun logoutSession(){
        val edit: SharedPreferences.Editor = sharedPref.edit()
        edit.clear()
        edit.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    fun updateprofilename()
    {
        val q = Volley.newRequestQueue(activity)
        val url =  "http://192.168.0.103/Plantific/updateprofilename.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("cekparams", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    var nameUpdate = v?.findViewById<TextInputEditText>(R.id.txtInputNamaProfile)
                    var emailupdate = v?.findViewById<TextInputEditText>(R.id.txtInputEmailProfile)
                    var name=nameUpdate?.text
                    var email=emailupdate?.text
                    val edit: SharedPreferences.Editor = sharedPref.edit()
                    edit.putString(key_name, name.toString())
                    edit.putString(key_email,email.toString())
                    edit.apply()
                    val dialog = SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    dialog.setTitleText("Success")
                    dialog.setContentText("Nama dan email Berhasil Diupdate")
                    dialog.setConfirmText("OK")
                    dialog.show()
                } else {
                    val dialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    dialog.setTitleText("Failed")
                    dialog.setContentText("Nama dan email Tidak Berhasil Diupdate")
                    dialog.setConfirmText("OK")
                    dialog.show()
                }
            },
            Response.ErrorListener {
                Log.d("cekparams", it.message.toString())
            }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                var idLogin = sharedPref.getString(key_id, "")
                var nameUpdate = v?.findViewById<TextInputEditText>(R.id.txtInputNamaProfile)
                var emailupdate = v?.findViewById<TextInputEditText>(R.id.txtInputEmailProfile)
                params["iduser"] = idLogin.toString()
                params["name"] = txtInputNamaProfile.text.toString()
                params["email"]=txtInputEmailProfile.text.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
    fun updateprofilepass()
    {
        val q = Volley.newRequestQueue(activity)
        val url =  "http://192.168.0.103/Plantific/updateprofilepass.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("cekparams", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    updateprofilename()
                    var passupdate = v?.findViewById<TextInputEditText>(R.id.txtInputPassProfile)
                    val dialog = SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    dialog.setTitleText("Success")
                    dialog.setContentText("Password Berhasil Diupdate")
                    dialog.setConfirmText("OK")
                    dialog.show()
                } else {
                    val dialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    dialog.setTitleText("Failed")
                    dialog.setContentText("Password Tidak Berhasil Diupdate")
                    dialog.setConfirmText("OK")
                    dialog.show()
                }
            },
            Response.ErrorListener {
                Log.d("cekparams", it.message.toString())
            }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                var idLogin = sharedPref.getString(key_id, "")
                var passupdate = v?.findViewById<TextInputEditText>(R.id.txtInputPassProfile)
                params["iduser"] = idLogin.toString()
                params["password"] = txtInputPassProfile.text.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
    fun bacadata(){
        val q = Volley.newRequestQueue(activity)
        val url =  "http://192.168.0.103/Plantific/bacaProfile.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("cekparams", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()) {
                        var data = data.getJSONObject(i)
                        txtInputNamaProfile.setText(data.getString("name"))
                        txtInputEmailProfile.setText(data.getString("email"))
//                        val url = data.getString("image_url")
//                        Picasso.get().load(url).into(imageViewGambar)
                    }
                }
            },
            Response.ErrorListener {
                Log.d("cekparams", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
                var iduser =sharedPref.getString(key_id,"")
                params["iduser"] = iduser.toString()

                return params
            }
        }
        q.add(stringRequest)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_profile, container, false)
        sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
        var name =sharedPref.getString(key_name,"")
        var btnLogOut = v?.findViewById<Button>(R.id.btnLogout)
        var btnSave=v?.findViewById<Button>(R.id.btnSave)
        var inputpass=v?.findViewById<TextInputEditText>(R.id.txtInputPassProfile)
        var confinputpass=v?.findViewById<TextInputEditText>(R.id.txtInputConfPass)
        bacadata()

        btnSave!!.setOnClickListener(){
            if (inputpass != null) {
                if(inputpass?.text.isNullOrEmpty()) {
                    updateprofilename()
                    }
                else if(inputpass.getText().toString().equals(confinputpass?.getText().toString())){
                    updateprofilepass()
                }
                else
                {
                    val dialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    dialog.setTitleText("Failed")
                    dialog.setContentText("Update Profile Gagal")
                    dialog.setConfirmText("OK")
                    dialog.show()
                }
                }
            else
            {
                updateprofilename()
            }

//            val dialog = SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
//                dialog.setTitleText("Change Confirm")
//                dialog.setContentText("Are You Sure To Change Profile?")
//                dialog.setConfirmText("YES")
//                dialog.showCancelButton(true)
//                dialog.setConfirmClickListener {
////                val intent = Intent(activity, LoginActivity::class.java)
////                startActivity(intent)
//
//                }
//                dialog.setCancelText("NO")
//                dialog.show()
//            }
//            else
//            {
//                val dialog = SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
//                dialog.setTitleText("ERROR!")
//                dialog.setContentText("Password field and Confirm Password Field is not the same")
//                dialog.setConfirmText("OK")
//                dialog.show()
//            }



        }

        btnLogOut!!.setOnClickListener {
            val dialog = SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
            dialog.setTitleText("Logout Confirm")
            dialog.setContentText("Logout now?")
            dialog.setConfirmText("YES")
            dialog.showCancelButton(true)
            dialog.setConfirmClickListener {
                dialog.dismiss()
                logoutSession()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            dialog.setCancelText("NO")
            dialog.show()
        }
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}