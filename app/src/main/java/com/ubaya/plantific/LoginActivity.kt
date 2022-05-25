package com.ubaya.plantific

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    val pref = "userSession"
    val key_name = "key.name"
    val key_email = "key.email"
    val key_id="key.id"
    lateinit var sharedPref : SharedPreferences
    private fun saveSession(name:String,email:String,iduser:String){
        val edit:SharedPreferences.Editor = sharedPref.edit()
        edit.putString(key_name,name)
        edit.putString(key_email,name)
        edit.putString(key_id,iduser)
        edit.apply()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ubaya.plantific.R.layout.activity_login)
        sharedPref = getSharedPreferences(pref, Context.MODE_PRIVATE)
        btnLogin.setOnClickListener{
            val snackBarInvalid = Snackbar.make(it, "Email/Password Salah, Coba Lagi", Snackbar.LENGTH_SHORT).setAction("Action", null)
            val snackBarEmpty = Snackbar.make(it, "Input Kosong", Snackbar.LENGTH_SHORT).setAction("Action", null)
            if(txtInputEmail.text.toString() == "" || txtInputPass.text.toString()== ""){
                snackBarEmpty.show()
            }
            else{
                val q = Volley.newRequestQueue(this)
                val url =  "http://192.168.0.103/Plantific/login.php"
                var stringRequest = object : StringRequest(
                    Method.POST, url,
                    Response.Listener {
                        Log.d("cekparams", it)
                        val obj = JSONObject(it)
                        if(obj.getString("result") == "OK") {
                            val data = obj.getJSONArray("data")
                            for(i in 0 until data.length()) {
                                var user = data.getJSONObject(i)
                                val intent = Intent(this, MainActivity::class.java)
                                saveSession(user.getString("name"),user.getString("email"),user.getString("idUser"))
                                startActivity(intent)
                            }
                        }
                        else{
                            snackBarInvalid.show()
                        }
                    },
                    Response.ErrorListener {
                        Log.d("cekparams", it.message.toString())
                        snackBarInvalid.show()
                    }){
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["email"] = txtInputEmail.text.toString()
                        params["password"] = txtInputPass.text.toString()
                        return params
                    }
                }
                q.add(stringRequest)
            }
        }

        txtRegister.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}