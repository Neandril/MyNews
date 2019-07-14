package com.neandril.mynews.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.neandril.mynews.R
import com.neandril.mynews.models.Article

class DataAdpter(private var dataList: MutableList<Article>, private val context: Context) : RecyclerView.Adapter<DataAdpter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel= dataList[position]

        Log.e("Adapter", "Titre : " + dataModel.section)

        holder.titleTextView.text = dataModel.section
        holder.descTextView.text = dataModel.title
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var titleTextView: TextView = itemLayoutView.findViewById(R.id.title)
        var descTextView: TextView = itemLayoutView.findViewById(R.id.description)
    }

    fun setData(articles: List<Article>) {
        dataList.clear()
        dataList.addAll(articles)

        notifyDataSetChanged()
    }
}