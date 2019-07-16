package com.neandril.mynews.controllers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.neandril.mynews.R
import com.neandril.mynews.api.ApiCall
import com.neandril.mynews.models.Article
import com.neandril.mynews.models.NYTModel
import com.neandril.mynews.utils.inflate
import com.neandril.mynews.views.adapter.MostPopularAdapter
import com.neandril.mynews.views.adapter.ScienceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScienceFragment : Fragment() {

    /** Array of news */
    private var dataList : MutableList<Article> = mutableListOf()
    /** Defining the RecyclerView */
    lateinit var recyclerView: RecyclerView
    lateinit var loadingPanel: RelativeLayout
    lateinit var mAdapter: ScienceAdapter
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container?.inflate(R.layout.fragment_science)

        loadingPanel = view!!.findViewById(R.id.loadingPanel)
        loadingPanel.visibility = View.VISIBLE
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        recyclerView = view.findViewById(R.id.science_RecyclerView)
        recyclerView.setHasFixedSize(true) // For improve performances
        mAdapter = ScienceAdapter(dataList, activity!!.applicationContext)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getData()

        mAdapter.setOnItemClickListener(object: ScienceAdapter.ClickListener {
            override fun onClick(pos: Int, aView: View) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            }
        })

        mSwipeRefreshLayout.setOnRefreshListener {
            // Refresh items
            getData()
        }

        return view
    }

    fun onItemsLoadComplete() {
        // Stop refresh animation
        mSwipeRefreshLayout.isRefreshing = false
    }

    /**
     * Fetch data from the API
     */
    private fun getData() {
        val apiCall = ApiCall.getInstance()
        apiCall?.science()?.enqueue(object : Callback<NYTModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTModel>?, response: Response<NYTModel>?) {
                /** If at least one item is received, populate the list */
                if(response?.body()?.mArticles != null){
                    loadingPanel.visibility = View.GONE
                    mAdapter.setData(response.body()?.mArticles ?: listOf())
                }
                onItemsLoadComplete()
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTModel>?, t: Throwable?) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}// Required empty public constructor