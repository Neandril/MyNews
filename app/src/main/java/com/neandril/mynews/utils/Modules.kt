package com.neandril.mynews.utils

import com.neandril.mynews.api.ApiCall
import com.neandril.mynews.models.*
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val applicationModule = module(override = true) {
    single<ArticleRepositoryInt> { ArticleRepositoryImplement(ApiCall.getInstance()) }

    single<SearchRepositoryInt> { SearchRepositoryImplement(ApiCall.getInstance()) }
}