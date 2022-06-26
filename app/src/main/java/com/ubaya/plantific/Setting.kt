package com.ubaya.plantific

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*


class Setting : Fragment() {

    lateinit var sharedPref : SharedPreferences
    var modelklasifikasi=""
    private fun saveSession(thres:String,modell:String){
        val edit: SharedPreferences.Editor = sharedPref.edit()
        edit.putString(threshold,thres)
        edit.putString(model,modell)
        edit.apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v=inflater.inflate(R.layout.fragment_setting, container, false)
        sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
        var threshold: String? =sharedPref.getString(threshold,"")
        var model: String? =sharedPref.getString(model,"")
        Log.d("model var",model.toString())
        v.txtInputthreshold.setText(threshold.toString())
        if(model=="")
        {
            modelklasifikasi="mobilenet"
        }
        if(threshold=="")
        {
            threshold=0.5f.toString()
        }
        else if(model=="mobilenet")
        {
            v.radioGroup.check(R.id.radioButtonMobileNet)
        }
        else if(model=="vgg16")
        {
            v.radioGroup.check(R.id.radioButtonVGG16)
        }
        else if(model=="densenet121")
        {
            v.radioGroup.check(R.id.radioButtonDensenet121)
        }
        v.btnSimpanSetting.setOnClickListener{
            threshold=txtInputthreshold.text.toString()
            if(v.radioButtonMobileNet.isChecked())
            {
                modelklasifikasi="mobilenet"
            }
            else if(v.radioButtonVGG16.isChecked())
            {
                modelklasifikasi="vgg16"
            }
            else if(v.radioButtonDensenet121.isChecked())
            {
                modelklasifikasi="densenet121"
            }
        saveSession(threshold!!, modelklasifikasi!!)
            val dialog = SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
            dialog.setTitleText("Success")
            dialog.setContentText("Setting Berhasil Diupdate")
            dialog.setConfirmText("OK")
            dialog.show()
            Log.d("threshold",threshold.toString())
            Log.d("model", model.toString())
            Log.d("modelklasifik",modelklasifikasi)
        }
        v.btnbacktoscan.setOnClickListener{
            val action = SettingDirections.actToScan()
            view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
        }
        return v
    }


}