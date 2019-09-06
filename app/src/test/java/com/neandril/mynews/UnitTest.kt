package com.neandril.mynews

import com.neandril.mynews.models.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class UnitTest {

    @Mock
    lateinit var repo: ArticleRepositoryInt

    @Mock
    lateinit var searchRepo: SearchRepositoryInt

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getTopStories() {
        repo.getTopStoriesData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {
                verify(repo, times(1).description("TopStories"))
            }
        })
    }

    @Test
    fun getMostPopular() {
        repo.getMostPopularData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {
                verify(repo, times(1).description("MostPopular"))
            }
        })
    }

    @Test
    fun getScience() {
        repo.getScienceData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {
                verify(repo, times(1).description("Science"))
            }
        })
    }

    @Test
    fun getTechnology() {
        repo.getTechnologyData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {
                verify(repo, times(1).description("Science"))
            }
        })
    }

    @Test
    fun search() {
        val query = ArrayList(listOf("trump", "20190801", "20190830", "politics", "0"))
        searchRepo.getSearchData(query, object : SearchCallback {
            /** Display error */
            override fun onError(message: String) {

            }
            /** Proceed */
            override fun onResponse(model: NYTSearchResultsModel?) {
                verify(searchRepo, times(1).description("Search"))
            }
        })
    }
}