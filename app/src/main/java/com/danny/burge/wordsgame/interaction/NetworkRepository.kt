package com.danny.burge.wordsgame.interaction

import android.util.Log
import androidx.annotation.WorkerThread
import com.danny.burge.wordsgame.constants.DEBUG_LOG_TAG
import com.danny.burge.wordsgame.interaction.model.WikipediaFirstParagraph
import org.koin.core.component.KoinComponent

class NetworkRepository(private val serverApi: WikipediaApi) : KoinComponent {
    @WorkerThread
    fun getFirstArticleParagraph(titles: String): WikipediaFirstParagraph? =
        runCatching {
            val response = serverApi.getFirstArticleParagraph(
                titles = titles
            ).execute().body()
            Log.d(DEBUG_LOG_TAG, response.toString())
            return response
        }.getOrElse {
            Log.d(DEBUG_LOG_TAG, "response ${it.message}")
            return null
        }
}