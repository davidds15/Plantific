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
import android.widget.TextView
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.json.JSONObject


class ProfileFragment : Fragment() {

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


    fun updateprofilename()
    {
        val q = Volley.newRequestQueue(activity)
        val url =  "https://plantific.ifubaya.id/updateprofilename.php"
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
        val url =  "https://plantific.ifubaya.id/updateprofilepass.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("cekparams", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val dialog = SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    dialog.setTitleText("Success")
                    dialog.setContentText("Profil Berhasil Diupdate")
                    dialog.setConfirmText("OK")
                    dialog.show()
                } else {
                    val dialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    dialog.setTitleText("Failed")
                    dialog.setContentText("Profil Tidak Berhasil Diupdate")
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
                params["iduser"] = idLogin.toString()
                params["password"] = txtInputPassProfile.text.toString()
                params["name"] = txtInputNamaProfile.text.toString()
                params["email"]=txtInputEmailProfile.text.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
    fun bacadata(){
        val q = Volley.newRequestQueue(activity)
        val url =  "https://plantific.ifubaya.id/bacaProfile.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("cekparams", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()) {
                        var data = data.getJSONObject(i)
                        txtInputNamaProfile?.setText(data.getString("name"))
                        txtInputEmailProfile?.setText(data.getString("email"))
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

//        var btnLogOut = v?.findViewById<Button>(R.id.btnLogout)
//        var btnSave=v?.findViewById<Button>(R.id.btnSave)
//        var txtinputnama=v?.findViewById<TextInputEditText>(R.id.txtInputNamaProfile)
//        var inputpass=v?.findViewById<TextInputEditText>(R.id.txtInputPassProfile)
//        var confinputpass=v?.findViewById<TextInputEditText>(R.id.txtInputRePass)
//        var txtnote=v?.findViewById<TextView>(R.id.txtnoteprofile)
//        var txtinputemail=v?.findViewById<TextInputEditText>(R.id.txtInputEmailProfile)
//        var txtinputlayoutemail=v?.findViewById<TextInputLayout>(R.id.textInputLayoutemail)
//        var txtinputlayoutpass=v?.findViewById<TextInputLayout>(R.id.textInputLayoutpassword)
//        var txtinputlayoutconfpass=v?.findViewById<TextInputLayout>(R.id.textInputLayoutconfpass)
        bacadata()
        var idLogin = sharedPref.getString(key_id, "")
        if(idLogin=="0")
        {
            v.btnSave?.visibility=View.GONE
            v.txtInputPassProfile?.visibility=View.GONE
            v.txtInputEmailProfile?.visibility=View.GONE
            v.txtInputRePass?.visibility=View.GONE
            v.txtnoteprofile?.visibility=View.GONE
            v.textInputLayoutemail?.visibility=View.GONE
            v.textInputLayoutpassword?.visibility=View.GONE
            v.textInputLayoutconfpass?.visibility=View.GONE
            v.txtInputNamaProfile?.setEnabled(false)
        }
        else
        {
            v.btnSave?.visibility=View.VISIBLE
            v.txtInputPassProfile?.visibility=View.VISIBLE
            v.txtInputEmailProfile?.visibility=View.VISIBLE
            v.txtInputRePass?.visibility=View.VISIBLE
            v.txtnoteprofile?.visibility=View.VISIBLE
            v.textInputLayoutemail?.visibility=View.VISIBLE
            v.textInputLayoutpassword?.visibility=View.VISIBLE
            v.textInputLayoutconfpass?.visibility=View.VISIBLE
            v.txtInputNamaProfile?.setEnabled(true)
        }

        v.btnDaftarPustaka?.setOnClickListener(){
            val action = ProfileFragmentDirections.profiletodaftarpustaka()
            view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
        }
        v.btnsettingprofile?.setOnClickListener(){
            val action = ProfileFragmentDirections.actprofiletosetting()
            view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
        }
        v.btnSave?.setOnClickListener(){
            if (v.txtInputPassProfile?.text.toString() != "") {
                if(v.txtInputPassProfile?.text.toString() ==v.txtInputRePass?.text.toString()){
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
        }

        v.btnLogout?.setOnClickListener {
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


}