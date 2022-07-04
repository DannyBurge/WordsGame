package com.danny.burge.wordsgame.interaction.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class WikipediaFirstParagraph(
    @Json(name = "query") val query: Query?,
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Query(
    @Json(name = "pages") val pages: Map<String, Page>?,
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Page(
    @Json(name = "pageid") val pageId: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "extract") val extract: String?
) : Parcelable