package com.neandril.mynews.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.neandril.mynews.R
import com.neandril.mynews.api.ApiCall
import com.neandril.mynews.models.Doc
import com.neandril.mynews.models.NYTSearchResultsModel
import com.neandril.mynews.models.SearchCallback
import com.neandril.mynews.models.SearchRepositoryImplement
import com.neandril.mynews.utils.PaginationScrollListener
import com.neandril.mynews.views.adapter.ResultsAdapter
import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity: AppCompatActivity(), ResultsAdapter.ClickListener {

    override fun onClick(doc: Doc) {
        val docUrl = doc.webUrl
        val docTitle = doc.sectionName

        val intent = Intent(this, WebviewActivity::class.java)

        intent.putExtra("url", docUrl)
        intent.putExtra("title", docTitle)
        startActivity(intent)
    }

    private var dataList : MutableList<Doc> = mutableListOf()

    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: ResultsAdapter

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    private val repository: SearchRepositoryImplement by lazy {
        /*
        object : ArticleRepositoryInt {
        override fun getTopStoriesData(callback: ArticleCallback) {
            val model = NYTModel()
            model.setArticles(arrayListOf(Article("Il fait chaud à Paris", "2019-07-20T05:10:00-04:00", "global")))
            callback.onResponse(model)
        }}
        */
        SearchRepositoryImplement(ApiCall.getInstance())
    }

    private val query : ArrayList<String> by lazy {
        intent.getStringArrayListExtra("query")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.results_recyclerView)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
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

        mAdapter = ResultsAdapter(dataList, this)
        mAdapter.setOnItemClickListener(this)
        recyclerView.adapter = mAdapter

        getData()
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
                //Log.e("Result", "results : " + model?.results)
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