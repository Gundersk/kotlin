package com.example.learnenglishapp

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.learnenglishapp.databinding.ActivityLearnWordBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLearnWordBinding.inflate((layoutInflater))

        enableEdgeToEdge()
        setContentView(binding.root)


        binding.layoutAnswer3.setOnClickListener{
            markAnswerCorrect(
                binding.layoutAnswer3,
                binding.tvVariantValue3,
                binding.tvThirdAnswer
            )
            showResultMessage(true)
        }

        binding.layoutAnswer1.setOnClickListener {
            markAnswerWrong(
                binding.layoutAnswer1,
                binding.tvVariantValue1,
                binding.tvFirstAnswer
            )
            showResultMessage(false)
        }

        binding.btnContinue.setOnClickListener {
            markAnswerNeutral(
                binding.layoutAnswer3,
                binding.tvVariantValue3,
                binding.tvThirdAnswer
            )
            markAnswerNeutral(
                binding.layoutAnswer1,
                binding.tvVariantValue1,
                binding.tvFirstAnswer
            )
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