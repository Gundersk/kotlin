package test

const val NUMBER_OF_ANSWER: Int = 4

class LearnWordsTrainer(
    private val dictionary: List<Word> = listOf(
        Word(original = "Artificial intelligence", translate = "Искусственный интеллект"),
        Word(original = "Blockchain", translate = "Блокчейн"),
        Word(original = "Quantum computing", translate = "Квантовые вычисления"),
        Word(original = "Cybersecurity", translate = "Кибербезопасность"),
        Word(original = "Augmented reality", translate = "Дополненная реальность"),
        Word(original = "Cryptocurrency", translate = "Криптовалюта"),
        Word(original = "Data Analytics", translate = "Анализ данных"),
        Word(original = "Machine Learning", translate = "Машинное обучение"),
        Word(original = "Internet of Things", translate = "Интернет вещей"),
        Word(original = "Cloud Computing", translate = "Облачные вычисления"),
        Word(original = "Robotics", translate = "Робототехника"),
        Word(original = "Biotech", translate = "Биотехнологии"),
        Word(original = "Nanotechnology", translate = "Нанотехнологии"),
        Word(original = "Digital Transformation", translate = "Цифровая трансформация"),
        Word(original = "GreenTech", translate = "Каберданс"),
        Word(original = "Server Development", translate = "Разработка ПО"),
        Word(original = "Big Data", translate = "Большие данные"),
        Word(original = "Data encryption", translate = "Шифрование данных"),
        Word(original = "Smart Devices", translate = "Умные устройства")
    ),


) {
    private var currentQuestion: Question? = null
    fun getNextQuestion(): Question?{
        val notLearnedList =  dictionary.filter { !it.learned }

        if(notLearnedList.isEmpty()){
            return null
        }

        val variants: MutableList<Word> = notLearnedList.shuffled().take(NUMBER_OF_ANSWER).toMutableList()
        val correct = variants.random()
        if(variants.size < NUMBER_OF_ANSWER) {
            val learnedList = dictionary.filter { it.learned }.shuffled().toMutableList()
            val need = NUMBER_OF_ANSWER - variants.size
            if(learnedList.size < need){
                return null
            }
            variants.addAll(learnedList.take(need))
        }
        currentQuestion = Question(variants, correct)
        return currentQuestion
    }

    fun checkAnswer(userAnswerIndex: Int): Boolean{
        val question = currentQuestion ?: return false
        if (question.variants[userAnswerIndex] == question.correctAnswer){
            question.correctAnswer.learned = true
            return true
        }
        return false
    }
}