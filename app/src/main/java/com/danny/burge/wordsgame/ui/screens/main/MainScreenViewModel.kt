package com.danny.burge.wordsgame.ui.screens.main

import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danny.burge.wordsgame.AppState
import com.danny.burge.wordsgame.WordsGameApp
import com.danny.burge.wordsgame.constants.DEBUG_LOG_TAG
import com.danny.burge.wordsgame.constants.LETTER_ON_SPOT_CODE
import com.danny.burge.wordsgame.database.models.Word
import com.danny.burge.wordsgame.interaction.NetworkRepository
import com.danny.burge.wordsgame.ui.model.Answer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainScreenViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    private lateinit var wordsDataBase: List<Word>

    fun startGame() {
        WordsGameApp.state = AppState()
        getRandomWordByLength(WordsGameApp.gameDifficulty)
    }


    private fun getRandomWordByLength(length: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(DEBUG_LOG_TAG, "getRandomWordByLength")

            wordsDataBase =
                WordsGameApp.wordsDatabase?.wordDAO()?.readAllWithLength(length) ?: listOf()

            if (wordsDataBase.isNotEmpty()) {
                val secretWord = wordsDataBase.shuffled().first()
                WordsGameApp.state.secretWord.value = secretWord
                Log.d(DEBUG_LOG_TAG, secretWord.word_letters)
                getWikiArticle(secretWord.word_letters)
            }
        }
    }

    private fun getWikiArticle(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val article = networkRepository.getFirstArticleParagraph(title) ?: return@launch
            val keyArticle = article.query?.pages?.keys?.first()
            val paragraph = article.query?.pages?.get(keyArticle)?.extract
            if (paragraph != null) {
                WordsGameApp.state.secretWordDefinition.value =
                    HtmlCompat.fromHtml(
                        paragraph,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
            }
        }
    }


    fun checkWord(secretWordAnswer: String) {
        with(WordsGameApp.state) {
            if (secretWordAnswer !in wordsDataBase.map { it.word_letters }) return

            val wordMask = getWordMask(secretWordAnswer)
            answers.add(
                Answer(
                    attempt = attempt.value,
                    word = secretWordAnswer,
                    colorMask = wordMask,
                    isCompletelyOpen = wordMask.minOrNull() == LETTER_ON_SPOT_CODE
                )
            )
        }
    }

    private fun getWordMask(word: String): MutableList<Int> {
        Log.d(DEBUG_LOG_TAG, "getWordMask")
        val resultMask = word.map { 0 }.toMutableList()
        var tmpWord = word
        var tmpAnswer = WordsGameApp.state.secretWord.value.word_letters
        tmpWord.forEachIndexed { index, letter ->
            if (letter == tmpAnswer[index]) {
                tmpWord = StringBuilder(tmpWord).replace(index, index + 1, "_").toString()
                tmpAnswer = StringBuilder(tmpAnswer).replace(index, index + 1, "*").toString()
                resultMask[index] += 2
            }
        }
        if (tmpWord.isNotBlank()) {
            tmpWord.forEachIndexed { index, letter ->
                if (letter in tmpAnswer) {
                    tmpWord = StringBuilder(tmpWord).replaceFirst(letter.toString().toRegex(), "_")
                    tmpAnswer =
                        StringBuilder(tmpAnswer).replaceFirst(letter.toString().toRegex(), "*")
                    resultMask[index] += 1
                }
            }
        }

        Log.d(DEBUG_LOG_TAG, "resultMask is $resultMask")
        return resultMask
    }
}