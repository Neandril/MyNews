package com.neandril.mynews

import com.neandril.mynews.models.*
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Theses test verify the return of the API call
 */

class
ModelsTests {

    @Test
    fun articleModelTest() {
        val article = Article("Title", "yyyy/MM/dd", "politics")

        assertEquals("Title", article.title)
        assertEquals("yyyy/MM/dd", article.publishedDate)
        assertEquals("politics", article.section)
    }

    @Test
    fun docModelTest() {
        val docResult = Doc("testWebUrl@domain.com", "title", "test_lead_paragraph",
            "abstract_description","1")

        assertEquals("testWebUrl@domain.com", docResult.webUrl)
        assertEquals("title", docResult.snippet)
        assertEquals("test_lead_paragraph", docResult.leadParagraph)
        assertEquals("abstract_description", docResult.abstract)
        assertEquals("1", docResult.printPage)
    }

    @Test
    fun multimediumModelTest() {
        val multimedium = Multimedium(0, "xlarge", null, null, "image",
            "images/yyyy/mm/dd/folder/theImage.jpg",359,533, null)

        assertEquals(0, multimedium.rank)
        assertEquals("xlarge", multimedium.subtype)
        assertEquals(null, multimedium.caption)
        assertEquals(null, multimedium.credit)
        assertEquals("image", multimedium.type)
        assertEquals("images/yyyy/mm/dd/folder/theImage.jpg", multimedium.url)
        assertEquals(359, multimedium.height)
        assertEquals(533, multimedium.width)
        assertEquals(null, multimedium.legacy)
    }

}