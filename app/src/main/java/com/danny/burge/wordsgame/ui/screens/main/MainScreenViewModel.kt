package com.danny.burge.wordsgame.ui.screens.main

import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.DEBUG_LOG_TAG
import com.danny.burge.wordsgame.database.models.Word
import com.danny.burge.wordsgame.helpers.extention.replaceAt
import com.danny.burge.wordsgame.interaction.NetworkRepository
import com.danny.burge.wordsgame.ui.model.Answer
import com.danny.burge.wordsgame.ui.model.ColorMask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainScreenViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    private lateinit var wordsDataBase: List<Word>

    fun startGame() {
        WordsGameApp.state.clear()
        getRandomWordByLength(WordsGameApp.settings.gameDifficulty)
    }

    private fun getRandomWordByLength(length: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(DEBUG_LOG_TAG, "getRandomWordByLength")
            getDataBase(length)
            if (wordsDataBase.isNotEmpty()) {
                val secretWord = wordsDataBase.shuffled().first()
                WordsGameApp.state.secretWord.value = secretWord
                Log.d(DEBUG_LOG_TAG, secretWord.word_letters)
                getWikiArticle(secretWord.word_letters)
            }
        }
    }

    private fun getDataBase(length: Int) {
        wordsDataBase = WordsGameApp.wordsDatabase?.wordDAO()?.readAllWithLength(length) ?: listOf()
        Log.d(DEBUG_LOG_TAG, "DataBase updated size is ${wordsDataBase.size}")
    }

    fun updateDataBase(length: Int = WordsGameApp.settings.gameDifficulty) {
        viewModelScope.launch(Dispatchers.IO) {
            getDataBase(length)
        }
    }

    fun addWordToDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val newWord = WordsGameApp.state.secretWordAnswer.joinToString("").lowercase()
            WordsGameApp.wordsDatabase?.wordDAO()?.addWord(
                Word(
                    word_letters = newWord,
                    word_size = newWord.length
                )
            )
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


    fun checkWord() {
        with(WordsGameApp.state) {
            val localAnswer = WordsGameApp.state.secretWordAnswer.joinToString("").lowercase()
            if (localAnswer in wordsDataBase.map { it.word_letters }) {
                val wordMask = getWordMask(localAnswer)
                val isCompletelyOpen = wordMask.all { it == ColorMask.LETTER_ON_SPOT }
                Log.d(DEBUG_LOG_TAG, "isCompletelyOpen is $isCompletelyOpen")
                answers.add(
                    Answer(
                        attempt = attempt.value,
                        word = localAnswer,
                        colorMask = wordMask,
                        isCompletelyOpen = isCompletelyOpen
                    )
                )
                secretWordAnswerApplied = true
            } else {
                secretWordAnswerApplied = false
            }
        }
    }

    private fun getWordMask(word: String): MutableList<ColorMask> {
        Log.d(DEBUG_LOG_TAG, "getWordMask")
        val resultMask = word.map { ColorMask.LETTER_NOT_IN_WORD }.toMutableList()
        var tmpWord = word
        var tmpAnswer = WordsGameApp.state.secretWord.value.word_letters
        tmpWord.forEachIndexed { index, letter ->
            if (letter == tmpAnswer[index]) {
                tmpWord = tmpWord.replaceAt(index, "_")
                tmpAnswer = tmpAnswer.replaceAt(index, "*")
                resultMask[index] = ColorMask.LETTER_ON_SPOT
            }
        }
        if (tmpWord.isNotBlank()) {
            tmpWord.forEachIndexed { index, letter ->
                if (letter in tmpAnswer) {
                    tmpWord =
                        StringBuilder(tmpWord).replaceFirst(letter.toString().toRegex(), "_")
                    tmpAnswer =
                        StringBuilder(tmpAnswer).replaceFirst(letter.toString().toRegex(), "*")
                    resultMask[index] = ColorMask.LETTER_IN_WORD
                }
            }
        }

        Log.d(DEBUG_LOG_TAG, "resultMask is $resultMask")
        return resultMask
    }
}