package com.neandril.mynews.views.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neandril.mynews.R
import com.neandril.mynews.models.Article
import java.text.ParseException
import java.text.SimpleDateFormat


class DataAdpter(private var dataList: MutableList<Article>, private val context: Context) : RecyclerView.Adapter<DataAdpter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel= dataList[position]

        var section = dataModel.section
        val subsection = dataModel.subsection
        val publishedDate = dataModel.publishedDate

        // If subsection is not null, append it to section
        if (!subsection.isNullOrEmpty()) {
           section += " > $subsection"
        }

        holder.titleTextView.text = section
        holder.descTextView.text = dataModel.title
        holder.publishedDate.text = publishedDate?.substring(8,10) +
                                    "/" + publishedDate?.substring(5,7) +
                                    "/" + publishedDate?.substring(0,4)

        if (dataModel.media != null && dataModel.media!!.isNotEmpty()) {
            val mediaUrl = dataModel.media?.get(0)?.url
            Glide.with(context).load(mediaUrl).into(holder.thumbnail)
            Log.e("Adapter", "img not null : $mediaUrl")
        } else {
            Log.e("Adapter", "img null")
        }
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var titleTextView: TextView = itemLayoutView.findViewById(R.id.title)
        var descTextView: TextView = itemLayoutView.findViewById(R.id.description)
        var publishedDate: TextView = itemLayoutView.findViewById(R.id.date)
        var thumbnail: ImageView = itemLayoutView.findViewById(R.id.thumbnail)
    }

    fun setData(articles: List<Article>) {
        dataList.clear()
        dataList.addAll(articles)

        notifyDataSetChanged()
    }
}