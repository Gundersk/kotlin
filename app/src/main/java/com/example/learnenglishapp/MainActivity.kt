package com.example.learnenglishapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.learnenglishapp.databinding.ActivityLearnWordBinding
import test.LearnWordsTrainer
import test.Question

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLearnWordBinding.inflate((layoutInflater))

        enableEdgeToEdge()
        setContentView(binding.root)

        val  trainer = LearnWordsTrainer()

        with(binding)
        {
            showNextQuestion(trainer)
            layoutAnswer1.setOnClickListener {
                if (trainer.checkAnswer(0)) {
                    markAnswerCorrect(
                        layoutAnswer1,
                        tvVariantValue1,
                        tvFirstAnswer
                    )
                    showResultMessage(true)
                } else {
                    markAnswerWrong(
                        layoutAnswer1,
                        tvVariantValue1,
                        tvFirstAnswer
                    )
                    showResultMessage(false)
                }

            }

            layoutAnswer2.setOnClickListener {
                if (trainer.checkAnswer(1)) {
                    markAnswerCorrect(
                        layoutAnswer2,
                        tvVariantValue2,
                        tvSecondtAnswer
                    )
                    showResultMessage(true)
                } else {
                    markAnswerWrong(
                        layoutAnswer2,
                        tvVariantValue2,
                        tvSecondtAnswer
                    )
                    showResultMessage(false)
                }

            }

            layoutAnswer3.setOnClickListener {
                if (trainer.checkAnswer(2)) {
                    markAnswerCorrect(
                        layoutAnswer3,
                        tvVariantValue3,
                        tvThirdAnswer
                    )
                    showResultMessage(true)
                } else {
                    markAnswerWrong(
                        layoutAnswer3,
                        tvVariantValue3,
                        tvThirdAnswer
                    )
                    showResultMessage(false)
                }
            }

            layoutAnswer4.setOnClickListener {
                if (trainer.checkAnswer(3)) {
                    markAnswerCorrect(
                        layoutAnswer4,
                        tvVariantValue4,
                        tvFourthAnswer
                    )
                    showResultMessage(true)
                } else {
                    markAnswerWrong(
                        layoutAnswer4,
                        tvVariantValue4,
                        tvFourthAnswer
                    )
                    showResultMessage(false)
                }

            }

            closeButton.setOnClickListener {
                finish()
            }



            btnContinue.setOnClickListener {
                markAnswerNeutral(
                    layoutAnswer1,
                    tvVariantValue1,
                    tvFirstAnswer
                )
                markAnswerNeutral(
                    layoutAnswer2,
                    tvVariantValue2,
                    tvSecondtAnswer
                )

                markAnswerNeutral(
                    layoutAnswer3,
                    tvVariantValue3,
                    tvThirdAnswer
                )
                markAnswerNeutral(
                    layoutAnswer4,
                    tvVariantValue4,
                    tvFourthAnswer
                )

                showNextQuestion(trainer)
            }

            btnSkip.setOnClickListener {
                showNextQuestion(trainer)
            }

            btnExit.setOnClickListener {
                finish()
            }

        }

    }

    private fun updateProgress(trainer: LearnWordsTrainer) = with(binding) {
        val learned = trainer.getLearnedCount()
        val total = trainer.getTotalCount()
        val percent = trainer.getLearnedPercent()

        tvProgress.text = "$learned/$total"
        progressWords.setProgress(percent, true)
    }

    private fun showNextQuestion(trainer: LearnWordsTrainer){
        val firstQuestion: Question? = trainer.getNextQuestion()
        updateProgress(trainer)

        with(binding){
            //Если все выучили
            if(firstQuestion == null){
                tvQuestionWord.text = getString(R.string.congratulations)
                tvFirstAnswer.isVisible = false
                tvSecondtAnswer.isVisible = false
                tvThirdAnswer.isVisible = false
                tvFourthAnswer.isVisible = false

                layoutAnswer1.isVisible = false
                layoutAnswer2.isVisible = false
                layoutAnswer3.isVisible = false
                layoutAnswer4.isVisible = false

                btnExit.text = getString(R.string.finish_button)
                btnExit.isVisible = true
            }else{
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true
                tvQuestionWord.text = firstQuestion.correctAnswer.original
                tvFirstAnswer.text = firstQuestion.variants[0].translate
                tvSecondtAnswer.text = firstQuestion.variants[1].translate
                tvThirdAnswer.text = firstQuestion.variants[2].translate
                tvFourthAnswer.text = firstQuestion.variants[3].translate
            }

        }
    }

    private fun showResultMessage(isCorrect: Boolean){
        val color: Int
        val messageText: String
        val resultIconResource: Int

        if(isCorrect){
            color = ContextCompat.getColor(this@MainActivity, R.color.correctAnswerColor)
            resultIconResource = R.drawable.ic_correct
            messageText = resources.getString(R.string.correct)
        }
        else{
            color = ContextCompat.getColor(this@MainActivity, R.color.wrongAnswerColor)
            resultIconResource = R.drawable.ic_wrong
            messageText = resources.getString(R.string.wrong_text)
        }

        with(binding){
            btnSkip.isVisible = false
            layoutResult.isVisible = true
            btnContinue.setTextColor(color)
            layoutResult.setBackgroundColor(color)
            tvResultMessage.text = messageText
            ivResultIcon.setImageResource(resultIconResource)
        }
    }

    private fun markAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ){
        with(binding){
            //Фон вариантов в исходный

            layoutAnswer.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_containers
            )


            //Текст в исходный

            tvVariantValue.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textVariantsColor
                )
            )


            //Цифры в исходное
            tvVariantNumber.apply {
                background = ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.shape_rounded_variants
                )

                setTextColor(ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textVariantsColor
                ))

        }

            layoutResult.isVisible = false
            btnSkip.isVisible = true
        }
    }
    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView

    ){
        //Обводка контейнера
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_wrong
        )

        //Фон цифры
        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_wrong
        )

        //Цвет цифры
        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )

        //Цвет значения слова
        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswerColor
            )
        )

    }

    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ){
        //обводка контейнера
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_correct
        )


        //Цвет цифры
        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )
        //Фон цифры
        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_correct
        )


        //Цвет значения варианта ответа
        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            )
        )

    }
}