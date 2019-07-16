package com.neandril.mynews.views.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.neandril.mynews.R
import com.neandril.mynews.controllers.activities.WebviewActivity
import com.neandril.mynews.models.Article


class MostPopularAdapter(private var dataList: MutableList<Article>, private val context: Context) : RecyclerView.Adapter<MostPopularAdapter.ViewHolder>() {

    lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel= dataList[position]

        val section = dataModel.section
        val type = dataModel.type
        val publishedDate = dataModel.publishedDate

        holder.titleTextView.text = type + " > $section"
        holder.descTextView.text = dataModel.title
        holder.publishedDate.text = publishedDate?.substring(8,10) +
                "/" + publishedDate?.substring(5,7) +
                "/" + publishedDate?.substring(0,4)

        if (dataModel.media != null && dataModel.media!!.isNotEmpty()) {
            val mediaUrl = dataModel.media?.get(0)?.mediaMetadata?.get(0)?.url
            Glide.with(context).load(mediaUrl).into(holder.thumbnail)
            Log.e("Adapter", "img not null : $mediaUrl")
        } else {
            Log.e("Adapter", "img null")
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }

    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView), View.OnClickListener {
        var titleTextView: TextView = itemLayoutView.findViewById(R.id.title)
        var descTextView: TextView = itemLayoutView.findViewById(R.id.description)
        var publishedDate: TextView = itemLayoutView.findViewById(R.id.date)
        var thumbnail: ImageView = itemLayoutView.findViewById(R.id.thumbnail)

        override fun onClick(v: View) {
            mClickListener.onClick(adapterPosition, v)
            val dataModel = dataList[adapterPosition]
            val articleUrl = dataModel.url
            val articleTitle = dataModel.title

            val intent = Intent(context, WebviewActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK // TODO: try to remove this tag
            intent.putExtra("url", articleUrl)
            intent.putExtra("title", articleTitle)
            context.startActivity(intent)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun setData(articles: List<Article>) {
        dataList.clear()
        dataList.addAll(articles)

        notifyDataSetChanged()
    }

}