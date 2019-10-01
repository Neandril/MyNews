package com.neandril.mynews

import android.provider.CallLog
import com.neandril.mynews.api.ApiInterface
import com.neandril.mynews.models.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call

/**
 * Theses test verify the return of the API call
 */

class
UnitTest {

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
        val service = mock(ApiInterface::class.java)

        repo.getTopStoriesData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {
                // verify(repo, times(1).description("TopStories"))
                verify(service).topStories()
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
            override fun onError(message: String) {

            }
            override fun onResponse(model: NYTSearchResultsModel?) {
                verify(searchRepo, times(1).description("Search"))
            }
        })
    }
}