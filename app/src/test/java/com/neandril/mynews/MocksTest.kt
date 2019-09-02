package com.neandril.mynews

import com.neandril.mynews.models.Article
import com.neandril.mynews.models.NYTModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MocksTest {

    @Mock
    lateinit var article : NYTModel
    lateinit var tested : Article

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        tested = Article("Titre","2019-07-20T05:10:00-04:00", "politics")
    }

    @Test
    fun test1() {
        val expected = Article("Titre", "2019-07-20T05:10:00-04:00", "politics")

    }
}