package com.neandril.mynews.controllers.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.neandril.mynews.R
import com.neandril.mynews.api.ApiCall
import com.neandril.mynews.models.Article
import com.neandril.mynews.models.NYTModel
import com.neandril.mynews.utils.inflate
import com.neandril.mynews.views.adapter.DataAdpter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopStoriesFragment : Fragment() {

    /** Array of news */
    private var dataList : ArrayList<Article> = ArrayList()
    /** Defining the RecyclerView */
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container?.inflate(R.layout.fragment_topstories)

        progressBar = view!!.findViewById(R.id.progress)

        recyclerView = view!!.findViewById(R.id.topStories_RecyclerView)
        recyclerView.setHasFixedSize(true) // For improve performances
        recyclerView.adapter = DataAdpter(dataList, activity!!.applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getData()

        return view
    }

    /**
     * Fetch data from the API
     */
    private fun getData() {
        val apiCall = ApiCall.getInstance()
        apiCall?.topStories()?.enqueue(object : Callback<NYTModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTModel>?, response: Response<NYTModel>?) {
                /** If at least one item is received, populate the list */
                if(response?.body()?.articles != null){
                    progressBar.visibility = View.GONE
                    dataList.addAll(response.body()!!.articles!!.asIterable())
                }
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTModel>?, t: Throwable?) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}