package com.neandril.mynews

import com.neandril.mynews.models.Article
import com.neandril.mynews.models.NYTModel
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MocksTest {

    @Mock
    lateinit var article : NYTModel
    lateinit var tested : Article

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        tested = Article("Titre","20193008", "politics")
    }

    @Test
    fun test1() {
        val expected = Article("Titre", "20193008", "politics")
    }
}