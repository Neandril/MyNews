package com.neandril.mynews.utils

import com.neandril.mynews.api.ApiCall
import com.neandril.mynews.models.*
import org.koin.dsl.module

val applicationModule = module {
    single<ArticleRepositoryInt> { ArticleRepositoryImplement(ApiCall.getInstance()) }
}

val testModule = module {
    single<ArticleRepositoryInt> {
        object : ArticleRepositoryInt {
            override fun getTopStoriesData(callback: ArticleCallback) {
                val model = NYTModel()
                model.setArticles(arrayListOf(Article("Il fait chaud à Paris", "2019-07-20T05:10:00-04:00", "global")))
                callback.onResponse(model)
            }

            override fun getMostPopularData(callback: ArticleCallback) {

            }

            override fun getScienceData(callback: ArticleCallback) {

            }

            override fun getTechnologyData(callback: ArticleCallback) {

            }

        }
    }
}