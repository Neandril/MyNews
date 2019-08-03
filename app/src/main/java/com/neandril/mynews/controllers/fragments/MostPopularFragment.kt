package com.neandril.mynews.controllers.fragments

import android.content.Intent
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
import com.neandril.mynews.controllers.activities.WebviewActivity
import com.neandril.mynews.models.*
import com.neandril.mynews.utils.inflate
import com.neandril.mynews.views.adapter.MostPopularAdapter

class MostPopularFragment : Fragment(), MostPopularAdapter.ClickListener {

    override fun onClick(articles: Article) {
        val articleUrl = articles.url
        val articleTitle = articles.title

        val intent = Intent(context, WebviewActivity::class.java)

        intent.putExtra("url", articleUrl)
        intent.putExtra("title", articleTitle)
        activity?.startActivity(intent)
    }

    /** Array of news */
    private var dataList: MutableList<Article> = mutableListOf()
    /** Defining the RecyclerView */
    lateinit var recyclerView: RecyclerView
    lateinit var loadingPanel: RelativeLayout
    lateinit var mAdapter: MostPopularAdapter
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private val repository: ArticleRepositoryInt by lazy {
        ArticleRepositoryImplement(ApiCall.getInstance())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container?.inflate(R.layout.fragment_mostpopular)

        loadingPanel = view!!.findViewById(R.id.loadingPanel)
        loadingPanel.visibility = View.VISIBLE
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        recyclerView = view.findViewById(R.id.mostPopular_RecyclerView)
        recyclerView.setHasFixedSize(true) // For improve performances
        mAdapter = MostPopularAdapter(dataList, activity!!.applicationContext)
        mAdapter.setOnItemClickListener(this)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getData()

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
        repository.getMostPopularData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {
                if (model == null) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                } else {
                    /** If at least one item is received, populate the list */
                    loadingPanel.visibility = View.GONE
                    mAdapter.setData(model.mArticles)
                    onItemsLoadComplete()
                }
            }
        })
    }
}