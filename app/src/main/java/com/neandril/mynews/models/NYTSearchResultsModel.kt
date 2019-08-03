package com.neandril.mynews.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NYTSearchResultsModel(

    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("copyright")
    @Expose
    val copyright: String? = null,
    @SerializedName("response")
    @Expose
    val results: DocDatas)

data class DocDatas(val docs: List<Doc>)

data class Byline (

    @SerializedName("original")
    @Expose
    var original: String? = null,
    @SerializedName("person")
    @Expose
    var person: List<Person>? = null,
    @SerializedName("organization")
    @Expose
    var organization: String? = null

)

data class Doc (

    @SerializedName("web_url")
    @Expose
    var webUrl: String? = null,
    @SerializedName("snippet")
    @Expose
    var snippet: String? = null,
    @SerializedName("lead_paragraph")
    @Expose
    var leadParagraph: String? = null,
    @SerializedName("abstract")
    @Expose
    var abstract: String? = null,
    @SerializedName("print_page")
    @Expose
    var printPage: String? = null,
    @SerializedName("multimedia")
    @Expose
    var multimedia: List<Multimedium>? = null,
    @SerializedName("headline")
    @Expose
    var headline: Headline? = null,
    @SerializedName("keywords")
    @Expose
    var keywords: List<Keyword>? = null,
    @SerializedName("pub_date")
    @Expose
    var pubDate: String? = null,
    @SerializedName("document_type")
    @Expose
    var documentType: String? = null,
    @SerializedName("news_desk")
    @Expose
    var newsDesk: String? = null,
    @SerializedName("section_name")
    @Expose
    var sectionName: String? = null,
    @SerializedName("byline")
    @Expose
    var byline: Byline? = null,
    @SerializedName("type_of_material")
    @Expose
    var typeOfMaterial: String? = null,
    @SerializedName("_id")
    @Expose
    var id: String? = null,
    @SerializedName("word_count")
    @Expose
    var wordCount: Int? = null,
    @SerializedName("uri")
    @Expose
    var uri: String? = null
)

data class Headline (

    @SerializedName("main")
    @Expose
    var main: String? = null,
    @SerializedName("kicker")
    @Expose
    var kicker: String? = null,
    @SerializedName("content_kicker")
    @Expose
    var contentKicker: String? = null,
    @SerializedName("print_headline")
    @Expose
    var printHeadline: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("seo")
    @Expose
    var seo: String? = null,
    @SerializedName("sub")
    @Expose
    var sub: String? = null

)

data class Keyword (

    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("value")
    @Expose
    var value: String? = null,
    @SerializedName("rank")
    @Expose
    var rank: Int? = null,
    @SerializedName("major")
    @Expose
    var major: String? = null

)

data class Legacy (

    @SerializedName("xlarge")
    @Expose
    var xlarge: String? = null,
    @SerializedName("xlargewidth")
    @Expose
    var xlargewidth: Int? = null,
    @SerializedName("xlargeheight")
    @Expose
    var xlargeheight: Int? = null,
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null,
    @SerializedName("thumbnailwidth")
    @Expose
    var thumbnailwidth: Int? = null,
    @SerializedName("thumbnailheight")
    @Expose
    var thumbnailheight: Int? = null,
    @SerializedName("widewidth")
    @Expose
    var widewidth: Int? = null,
    @SerializedName("wideheight")
    @Expose
    var wideheight: Int? = null,
    @SerializedName("wide")
    @Expose
    var wide: String? = null

)

data class Meta (

    @SerializedName("hits")
    @Expose
    var hits: Int? = null,
    @SerializedName("offset")
    @Expose
    var offset: Int? = null,
    @SerializedName("time")
    @Expose
    var time: Int? = null

)

data class Multimedium (

    @SerializedName("rank")
    @Expose
    var rank: Int? = null,
    @SerializedName("subtype")
    @Expose
    var subtype: String? = null,
    @SerializedName("caption")
    @Expose
    var caption: String? = null,
    @SerializedName("credit")
    @Expose
    var credit: String? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null,
    @SerializedName("height")
    @Expose
    var height: Int? = null,
    @SerializedName("width")
    @Expose
    var width: Int? = null,
    @SerializedName("legacy")
    @Expose
    var legacy: Legacy? = null,
    @SerializedName("subType")
    @Expose
    var subType: String? = null,
    @SerializedName("crop_name")
    @Expose
    var cropName: String? = null

)

data class Person (

    @SerializedName("firstname")
    @Expose
    var firstname: String? = null,
    @SerializedName("middlename")
    @Expose
    var middlename: String? = null,
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null,
    @SerializedName("qualifier")
    @Expose
    var qualifier: String? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("role")
    @Expose
    var role: String? = null,
    @SerializedName("organization")
    @Expose
    var organization: String? = null,
    @SerializedName("rank")
    @Expose
    var rank: Int? = null

)

data class Response (

    @SerializedName("docs")
    @Expose
    var docs: List<Doc>? = null,
    @SerializedName("meta")
    @Expose
    var meta: Meta? = null

)