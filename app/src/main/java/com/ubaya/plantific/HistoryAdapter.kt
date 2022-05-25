package com.ubaya.plantific

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_1.view.*
import kotlinx.android.synthetic.main.card_2.view.*

class HistoryAdapter (val historys:ArrayList<History>): RecyclerView.Adapter<HistoryAdapter.PlaylistViewHolder>() {
    class PlaylistViewHolder(val v: View) : RecyclerView.ViewHolder(v)
    companion object{
        val id="id"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.card_2, parent, false)
        return HistoryAdapter.PlaylistViewHolder(v)
    }
    override fun onBindViewHolder(holder: HistoryAdapter.PlaylistViewHolder, position: Int) {
        val url = historys[position].img_url
        Picasso.get().load(url).into(holder.v.imgDaunhistory)
//        Log.d("apiresult", tanamans[position].namaTanaman)
//        holder.v.txtNamaDaun.text="abc"
        holder.v?.txtTanggal.text = historys[position].datePredict
        holder.v?.txtResult.text = historys[position].hasilPredict
        holder.v.setOnClickListener{
            val action = HistoryFragmentDirections.actionItemHistoryToItemDetail(historys[position].idTanaman.toString(),"0")
            Navigation.findNavController(holder.v).navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return historys.size
    }
}