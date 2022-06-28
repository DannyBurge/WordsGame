package com.danny.burge.wordsgame.ui.screens.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danny.burge.wordsgame.WordsGameApp
import com.danny.burge.wordsgame.constants.DEBUG_LOG_TAG
import com.danny.burge.wordsgame.constants.LETTER_ON_SPOT_CODE
import com.danny.burge.wordsgame.database.models.Word
import com.danny.burge.wordsgame.ui.model.Answer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainScreenViewModel : ViewModel() {

    private lateinit var wordsDataBase: List<Word>

    private val _currentSecretWord = MutableLiveData<String?>()
    val currentSecretWord: LiveData<String?> get() = _currentSecretWord

    private val _answers = MutableLiveData<MutableList<Answer>?>()
    val answers: LiveData<MutableList<Answer>?> get() = _answers

    fun startGame() {
        _currentSecretWord.value = ""
        generateEmptyAnswerList()
        getRandomWordByLength(WordsGameApp.gameDifficulty)
    }

    private fun generateEmptyAnswerList() {
        val emptyAnswerList: MutableList<Answer> = mutableListOf()
        val emptyColorMask = (0 until WordsGameApp.gameDifficulty).map { 0 }
        val emptyString = (0 until WordsGameApp.gameDifficulty).joinToString("") { " " }
        for (index in 0 until WordsGameApp.attemptNumber) {
            emptyAnswerList.add(
                Answer(
                    word = emptyString,
                    colorMask = emptyColorMask,
                    isCompletelyOpen = false,
                    attempt = index
                )
            )
        }
        _answers.value = emptyAnswerList
    }

    private fun getRandomWordByLength(length: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(DEBUG_LOG_TAG, "getRandomWordByLength")

            wordsDataBase =
                WordsGameApp.wordsDatabase?.wordDAO()?.readAllWithLength(length) ?: listOf()

            if (wordsDataBase.isNotEmpty()) {
                val secretWord = wordsDataBase.shuffled().first()
                _currentSecretWord.postValue(secretWord.word_letters)
                Log.d(DEBUG_LOG_TAG, secretWord.word_letters)
            }
        }
    }

    fun checkWord(wordForCheck: String, attempt: Int): Int {
        if (wordForCheck !in wordsDataBase.map { it.word_letters }) return attempt

        val wordMask = getWordMask(wordForCheck)
        val valueToPost = mutableListOf<Answer>()
        valueToPost.addAll(_answers.value!!)

        valueToPost[attempt].apply {
            word = wordForCheck
            colorMask = wordMask
            isCompletelyOpen = wordMask.minOrNull() == LETTER_ON_SPOT_CODE
        }

        _answers.value!!.clear()
        _answers.value = valueToPost
        return attempt + 1
    }

    private fun getWordMask(word: String): MutableList<Int> {
        Log.d(DEBUG_LOG_TAG, "getWordMask")
        val resultMask = word.map { 0 }.toMutableList()
        var tmpWord = word
        var tmpAnswer = currentSecretWord.value!!
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