package com.neandril.mynews.views.adapter

import android.content.Context
import android.support.v7.app.AlertDialog
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
import com.neandril.mynews.controllers.activities.ResultsActivity
import com.neandril.mynews.models.Doc

class ResultsAdapter(private var dataList: MutableList<Doc>, private val context: Context) : RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {

    lateinit var mClickListener: ClickListener
    private val activity : ResultsActivity = context as ResultsActivity
    private val urlPrefix = context.getString(R.string.urlPrefix)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ResultsAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(doc: Doc)
    }

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
            val section = dataModel.sectionName
            val snippet = dataModel.snippet
            val strPublishedDate = dataModel.pubDate

            titleTextView.text = section
            descTextView.text = snippet
            publishedDate.text = context.getString(R.string.dateformat, strPublishedDate?.substring(8,10), strPublishedDate?.substring(5,7), strPublishedDate?.substring(0,4)) //TODO: Jodatime

            // If an image is found, "glide it"
            if (dataModel.multimedia != null && dataModel.multimedia!!.isNotEmpty()) {
                val mediaUrl = "" + urlPrefix + dataModel.multimedia?.get(0)?.url
                Log.e("Results", "url : $mediaUrl")
                Glide.with(context).load(mediaUrl).into(thumbnail)
            } else {
                Glide.with(context).load(R.drawable.ic_newyorktimes).into(thumbnail)
            }
        }
    }

    /**
     * Function that put articles inside the list
     * The list is cleared each time, to prevent duplications
     */
    fun setData(articles: List<Doc>) {

        dataList.addAll(articles)

        notifyDataSetChanged()

        // notifyItemRangeChanged(size, sizeNew)

        if (dataList.size == 0) {
            Log.e("Results", "empty")
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.alertDialogTitle))
            builder.setMessage(context.getString(R.string.alertDialogText))
            builder.setNeutralButton("OK") { _, _ ->
                activity.finishMe()
                Toast.makeText(context, context.getString(R.string.alertDialogButton), Toast.LENGTH_LONG).show()
            }
            builder.show()
        }
    }
}