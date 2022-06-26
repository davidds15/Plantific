package com.ubaya.plantific

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_1.view.*

class searchAdapter(val tanamans:ArrayList<Tanaman>): RecyclerView.Adapter<searchAdapter.PlaylistViewHolder>()  {
    class PlaylistViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.card_1, parent, false)
        return PlaylistViewHolder(v)
    }
    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val url = tanamans[position].img_url
        Picasso.get().load(url).into(holder.v.imgDaun)
//        Log.d("apiresult", tanamans[position].namaTanaman)
//        holder.v.txtNamaDaun.text="abc"
        holder.v?.txtNamaDaun.text = tanamans[position].namaTanaman
        holder.v.setOnClickListener{
            val action = SearchFragmentDirections.actionsearchtodetail(tanamans[position].idTanaman.toString(),"0")
            Navigation.findNavController(holder.v).navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return tanamans.size
    }

}