package com.neandril.mynews.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.neandril.mynews.R
import com.neandril.mynews.models.Doc
import com.neandril.mynews.models.NYTSearchResultsModel
import com.neandril.mynews.models.SearchCallback
import com.neandril.mynews.models.SearchRepositoryInt
import com.neandril.mynews.utils.PaginationScrollListener
import com.neandril.mynews.views.adapter.ResultsAdapter
import kotlinx.android.synthetic.main.activity_results.*
import org.koin.android.ext.android.inject

/**
 * Activity for search results
 */
class ResultsActivity: AppCompatActivity(), ResultsAdapter.ClickListener {

    /** Variables */
    private var dataList : MutableList<Doc> = mutableListOf()
    private var query : ArrayList<String> = arrayListOf()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: ResultsAdapter

    /** Pagination booleans initializations */
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    /** Inject the search repository */
    private val repository : SearchRepositoryInt by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        query = intent.getStringArrayListExtra("query")

        linearLayoutManager = LinearLayoutManager(this)
        results_recyclerView.layoutManager = linearLayoutManager

        configurePagination()

        mAdapter = ResultsAdapter(dataList, this)
        mAdapter.setOnItemClickListener(this)
        results_recyclerView.adapter = mAdapter

        getData()
    }

    private fun configurePagination() {
        results_recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                query[4] = (query[4].toInt() + 1).toString()
                getData()
                isLoading = true
            }
        })
    }

    /**
     * Handle click on an result item
     */
    override fun onClick(doc: Doc) {
        val docUrl = doc.webUrl
        val docTitle = doc.sectionName

        val intent = Intent(this, WebviewActivity::class.java)

        intent.putExtra("url", docUrl)
        intent.putExtra("title", docTitle)
        startActivity(intent)
    }

    /**
     * Fetch data from the API
     */
    private fun getData() {
        repository.getSearchData(query, object : SearchCallback {
            /** Display error */
            override fun onError(message: String) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }

            /** Proceed */
            override fun onResponse(model: NYTSearchResultsModel?) {
                if (model == null) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                } else {
                    /** If at least one item is received, populate the list */
                    loadingPanel.visibility = View.GONE
                    mAdapter.setData(model.results.docs)
                }
                isLoading = false
            }
        })
    }

    /** Method terminating current activity */
    fun finishMe() {
        finish()
    }
}