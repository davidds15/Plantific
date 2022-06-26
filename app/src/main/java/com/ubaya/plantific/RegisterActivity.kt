package com.ubaya.plantific

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    val pref = "userSession"
    val key_name = "key.name"
    val key_email = "key.email"
    lateinit var sharedPref : SharedPreferences
    private fun saveSession(name:String,email:String){
        val edit: SharedPreferences.Editor = sharedPref.edit()
        edit.putString(key_name,name)
        edit.putString(key_email,name)
        edit.apply()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        sharedPref = getSharedPreferences(pref, Context.MODE_PRIVATE)
        txtLogin.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnRegister.setOnClickListener{
            val snackBarInvalid = Snackbar.make(it, "Terjadi kesalahan, silahkan coba lagi.",
                Snackbar.LENGTH_SHORT).setAction("Action", null)
            val snackBarEmail = Snackbar.make(it, "Email is not available.", Snackbar.LENGTH_SHORT).setAction("Action", null)
            val snackBarEmpty = Snackbar.make(it, "Input Kosong", Snackbar.LENGTH_SHORT).setAction("Action", null)
            val snackBarPassword = Snackbar.make(it, "Password tidak match", Snackbar.LENGTH_SHORT).setAction("Action", null)
            if(txtInputName.text.toString() == "" || txtInputEmailRegister.text.toString() == "" || txtInputPassRegister.text.toString()== "" || txtInputConfPass.text.toString()== ""){
                snackBarEmpty.show()
            }
            else if(txtInputPassRegister.text.toString() != txtInputConfPass.text.toString() ){
                snackBarPassword.show()
            }
            else {
                val q = Volley.newRequestQueue(this)
                val url =  "https://plantific.ifubaya.id/register.php"
                var stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener {
                        Log.d("cekparams", it)
                        val obj = JSONObject(it)
                        if(obj.getString("result") == "OK") {
                            val data = obj.getJSONArray("data")
                            for(i in 0 until data.length()) {
                                var user = data.getJSONObject(i)
                                val intent = Intent(this, LoginActivity::class.java)
//                                saveSession(user.getString("name"),user.getString("email"))
                                startActivity(intent)
                            }
                        }
                        else if(obj.getString("result") == "ERROREMAIL"){
                            snackBarEmail.show()
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
                        params["name"] = txtInputName.text.toString()
                        params["email"] = txtInputEmailRegister.text.toString()
                        params["password"] = txtInputPassRegister.text.toString()
                        return params
                    }
                }
                q.add(stringRequest)
            }
        }


    }
}