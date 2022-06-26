package com.ubaya.plantific

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_1.view.*

class tanamanAdapter (val tanamans:ArrayList<Tanaman>): RecyclerView.Adapter<tanamanAdapter.PlaylistViewHolder>() {
    class PlaylistViewHolder(val v: View) : RecyclerView.ViewHolder(v)
    companion object{
        val id="id"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.card_1, parent, false)
        return PlaylistViewHolder(v)
    }
    
    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val url = tanamans[position].img_url
        Picasso.get().load(url).into(holder.v.imgDaun)
        holder.v?.txtNamaDaun.text = tanamans[position].namaTanaman
        holder.v.setOnClickListener{
            val action = HomeFragmentDirections.actionhometodetail(tanamans[position].idTanaman.toString(),"0")
            Navigation.findNavController(holder.v).navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return tanamans.size
    }
}