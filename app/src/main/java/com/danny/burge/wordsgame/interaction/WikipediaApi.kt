package com.danny.burge.wordsgame.interaction

import com.danny.burge.wordsgame.interaction.model.WikipediaFirstParagraph
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WikipediaApi {
    @GET("api.php")
    fun getFirstArticleParagraph(
        @Query("titles") titles: String,
        @Query("action") action: String = "query",
        @Query("prop") prop: String = "extracts",
        @Query("format") format: String = "json",
        @Query("exintro") exintro: Int = 1,
    ): Call<WikipediaFirstParagraph>
}