package com.neandril.mynews.models

/**
 * Main models
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.Url
import java.util.*

/**
 * Main Model
 */
class NYTModel {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("num_results")
    @Expose
    var numResults: Int? = null
    @SerializedName(value = "results")
    @Expose
    var mArticles: List<Article> = listOf()

    fun setArticles(articles: ArrayList<Article>) {
        this.mArticles = articles
    }

}

/**
 * Model about articles
 */
class Article(
    @field:SerializedName("title")
    @field:Expose
    var title: String?, @field:SerializedName("published_date")
    @field:Expose
    var publishedDate: String?, @field:SerializedName(value = "section", alternate = ["section_name"])
    @field:Expose
    var section: String?
) {

    @SerializedName(value = "url", alternate = ["web_url"])
    @Expose
    var url: String? = null

    @SerializedName(value = "subsection", alternate = ["subsection_name"])
    @Expose
    var subsection: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("source")
    @Expose
    var source: String? = null

    @SerializedName(value = "media", alternate = ["multimedia"])
    @Expose
    var media: List<Medium>? = null

}

/**
 * Model for media informations (i.e. thumbnails)
 */
class Medium {

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("subtype")
    @Expose
    var subtype: String? = null
    @SerializedName("caption")
    @Expose
    var caption: String? = null
    @SerializedName("copyright")
    @Expose
    var copyright: String? = null
    @SerializedName("approved_for_syndication")
    @Expose
    var approvedForSyndication: Int? = null

    @SerializedName("media-metadata")
    @Expose
    var mediaMetadata: List<MediaMetadata>? = null

    constructor() {
        this.mediaMetadata = emptyList()
    }

    constructor(medium: List<MediaMetadata>) {
        this.mediaMetadata = medium
    }
}

/**
 * Model for media metadatas
 */
class MediaMetadata(
    @field:SerializedName("url")
    @field:Expose
    var url: String?
) {
    @SerializedName("format")
    @Expose
    var format: String? = null
    @SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("width")
    @Expose
    var width: Int? = null
}