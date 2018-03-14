package br.com.aaascp.androidapp.presentation

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SingleRowStaticViewAdapter(
        @field:LayoutRes
        private val layoutResourceId: Int,
        private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<SingleRowStaticViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                layoutInflater.inflate(layoutResourceId, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Static views, no bind required
    }

    override fun getItemCount(): Int {
        return 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
