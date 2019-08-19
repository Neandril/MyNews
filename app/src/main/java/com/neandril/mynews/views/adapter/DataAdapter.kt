package com.neandril.mynews.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neandril.mynews.R
import com.neandril.mynews.models.Article


class DataAdapter(private var dataList: MutableList<Article>, private val context: Context) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(articles: Article)
    }

    /**
     * Class for the viewHolder of the RecyclerView
     * Make the classe inner for allowing us to handle click event
     */
    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView), View.OnClickListener {
        private var titleTextView: TextView = itemLayoutView.findViewById(R.id.title)
        private var descTextView: TextView = itemLayoutView.findViewById(R.id.description)
        private var publishedDate: TextView = itemLayoutView.findViewById(R.id.date)
        var thumbnail: ImageView = itemLayoutView.findViewById(R.id.thumbnail)

        // Click event
        override fun onClick(v: View) {
            mClickListener.onClick(dataList[adapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind() {
            val dataModel= dataList[adapterPosition]

            // Define variables
            var section = dataModel.section
            val subsection = dataModel.subsection
            val strPublishedDate = dataModel.publishedDate

            // If subsection is not null, append it to section
            if (!subsection.isNullOrEmpty()) {
                section += " > $subsection"
            }

            // Retrieve news datas
            titleTextView.text = section
            descTextView.text = dataModel.title
            publishedDate.text = context.getString(R.string.dateformat, strPublishedDate?.substring(8,10), strPublishedDate?.substring(5,7), strPublishedDate?.substring(0,4)) //TODO: Jodatime

            // If an image is found, "glide it"
            if (dataModel.media != null && dataModel.media!!.isNotEmpty()) {
                val mediaUrl = dataModel.media?.get(0)?.url
                Glide.with(context).load(mediaUrl).into(thumbnail)
            } else {
                Glide.with(context).load(R.drawable.ic_newyorktimes).into(thumbnail)
            }
        }
    }

    /**
     * Function that put articles inside the list
     * The list is cleared each time, to prevent duplicates
     */
    fun setData(articles: List<Article>) {
        dataList.clear()
        dataList.addAll(articles.sortedByDescending { it.publishedDate })

        notifyDataSetChanged()
    }
}