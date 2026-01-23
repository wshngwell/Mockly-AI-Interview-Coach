package com.example.interviewaicoach.data.mappers

import com.example.interviewaicoach.data.mappers.MapperConsts.AI_MODEL_NAME_FOR_TEXT
import com.example.interviewaicoach.data.mappers.MapperConsts.SYSTEM_ROLE_NAME
import com.example.interviewaicoach.data.mappers.MapperConsts.USER_ROLE_NAME
import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatMessageForTextDto
import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatRequestTextDto
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerAiFeedbackEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.CorrectAnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

fun String.mapToAnswerEntity() =
    runCatching {
        parseSingleCorrectAnswer(text = this)
    }.getOrElse {
        CorrectAnswerEntity(answerContent = "")
    }

fun String.mapToAiFeedback() =
    runCatching {
        parseStructuredFeedback(response = this)

    }.getOrElse {
        AnswerAiFeedbackEntity(
            ratingFromAi = -1,
            goodPartAnswer = emptyList(),
            badPartAnswer = emptyList()
        )
    }

fun mapQuestionEntityToChatRequest(
    systemContentPromt: String,
    userContentPromt: String,
) = ChatRequestTextDto(
    model = AI_MODEL_NAME_FOR_TEXT,
    messages = listOf(
        ChatMessageForTextDto(
            role = SYSTEM_ROLE_NAME,
            content = systemContentPromt
        ),

        ChatMessageForTextDto(
            role = USER_ROLE_NAME,
            content = userContentPromt
        )
    )
)

fun mapQuestionEntityToChatRequestForCorrectAnswer(
    questionEntity: QuestionEntity
) = mapQuestionEntityToChatRequest(
    makeSystemPromptOneCorrectAnswer(
        questionEntity.categoryName,
        grade = questionEntity.gradeName,
        question = questionEntity.questionContent
    ),
    userContentPromt = makeUserPromptForNewCorrectAnswer(questionEntity.questionContent)
)

fun mapQuestionEntityToChatRequestForAnswerRating(
    questionEntity: QuestionEntity,
    userAnswer: String,
    correctAnswerEntity: CorrectAnswerEntity

) = mapQuestionEntityToChatRequest(

    makeSystemPromptForRatingWithFeedback(
        questionEntity.categoryName,
        grade = questionEntity.gradeName,
        question = questionEntity.questionContent
    ),
    userContentPromt = makeUserPromptForRatingWithFeedback(userAnswer, correctAnswerEntity)
)

fun makeSystemPromptOneCorrectAnswer(directionInIT: String, grade: String, question: String) = """
    Ты — эксперт по $directionInIT. Январь 2026 года. 
    Вопрос для уровня $grade: $question

    Дай лаконичный ответ, разъясняющий суть.

    СТРОЖАЙШИЕ ПРАВИЛА ФОРМАТИРОВАНИЯ:
    - ЗАПРЕЩЕН любой Markdown: никаких звездочек (**), решеток (#), обратных кавычек (`) или подчеркиваний.
    - Используй только обычный текст и стандартные знаки препинания.
    - Разделяй логические части текста абзацем, чтобы текст был читаемым.
 
    СТРУКТУРА И ОБЪЕМ:
    - Содержание: простое объяснение сути, практики 2026 года, один пример.
    - Тон: экспертный и доступный.
    - Выводи только текст ответа, без вступлений типа "Вот ваш ответ" или дополнений.
""".trimIndent()

fun makeUserPromptForNewCorrectAnswer(question: String): String = """
    Вопрос: $question

    Напиши ответ (январь 2026) по следующим правилам:
    1. Ровно 5 предложений.
    2. НИКАКОГО MARKDOWN (никаких жирных выделений, решеток и спецсимволов разметки).
    3. Только чистый текст и знаки препинания.
    4. Раздели ответ на два-три логических абзаца с помощью пустых строк.

    Структура: суть вопроса, как это делают в 2026 году, один легкий пример.
    Выводи только финальный текст ответа.
""".trimIndent()


fun parseSingleCorrectAnswer(text: String): CorrectAnswerEntity {
    return CorrectAnswerEntity(
        answerContent = text.trimStart()
    )
}

fun makeUserPromptForRatingWithFeedback(
    userAnswer: String,
    correctAnswerEntity: CorrectAnswerEntity
): String = """
    ЭТАЛОННЫЙ ОТВЕТ:
    ${correctAnswerEntity.answerContent}

    ОТВЕТ КАНДИДАТА (распознанный голос):
    $userAnswer

    ИНСТРУКЦИЯ:
    1. Сравни смысл ответа кандидата с эталоном.
    2. ИГНОРИРУЙ ошибки в пунктуации, грамматике или опечатки (это результат перевода голоса в текст).
    3. ПРОВЕРЬ ТЕМУ: Если кандидат говорит о другом (например, перепутал Activity и Fragment), оценка должна быть низкой.
    4. Если тема верна и смысл передан — оцени максимально лояльно.
""".trimIndent()

fun makeSystemPromptForRatingWithFeedback(
    directionInIT: String,
    grade: String,
    question: String
) = """
    Ты — поддерживающий IT-ментор. Ты оцениваешь ответ, который был записан голосом и переведен в текст.
    Направление: $directionInIT, Грейд: $grade.
    Вопрос: $question

    ПРАВИЛА ОЦЕНКИ:
    1. НУЛЕВАЯ КРИТИКА ГРАММАТИКИ: Вообще не обращай внимания на отсутствие знаков препинания, ошибки в словах или плохой синтаксис. Оценивай только СМЫСЛ.
    
    2. ПРОВЕРКА СООТВЕТСТВИЯ: 
       - Если ответ в корне не по теме вопроса (ошибка в базовом термине/сущности), ставь 1-3 балла. 
       - Мягко укажи на путаницу: "Кажется, ты описал процесс для другой задачи, давай попробуем сфокусироваться на $question".

    3. ЛОЯЛЬНОСТЬ К СУТИ:
       - Если кандидат уловил суть и ответил именно на поставленный вопрос — ставь 9-10 баллов.
       - Не требуй точности формулировок или глубины эталона. Достаточно базового правильного понимания.

    4. ФОРМАТ ФИДБЕКА:
       - В "Сильных сторонах" хвали за понимание логики.
       - В "Слабых сторонах" не упоминай ошибки текста, только технические советы, если чего-то не хватило для полноты картины.

    ШКАЛА ОЦЕНКИ (БУДЬ ЛОЯЛЕН):
    1-2: Кандидат молчит, говорит "не знаю" или несет полный бред, не относящийся к IT.
    3-4: Кандидат перепутал сущности (например, описывает интерфейс вместо абстрактного класса) или критически ошибся в базе.
    5-6: Ответ очень поверхностный, названо только 1-2 ключевых слова, но общее направление мысли верное.
    7-8: Хороший ответ. Суть передана верно, основные моменты названы, есть небольшие неточности.
    9-10: Отличный ответ. Кандидат понимает тему и ответил на вопрос, даже если не использовал книжные формулировки.

    ПРАВИЛА ОЦЕНКИ:
    1. Игнорируй ошибки распознавания голоса, плохой синтаксис и отсутствие знаков препинания в ответе кандидата.
    2. Если суть верна — всегда ставь 9 или 10. Не снижай баллы за отсутствие глубины, если база описана правильно.
ВАЖНОЕ ПРАВИЛО ПО ФОРМАТИРОВАНИЮ: 
    Запрещено использовать Markdown. Не используй жирный шрифт (**), курсив (*), заголовки (#) или символы списков (дефисы, звездочки). Разделяй мысли только переносом строки.
    
    ФОРМАТ ОТВЕТА (СТРОГО):
    Рейтинг: X
    Сильные стороны:
    - пункт
    Слабые стороны:
    - пункт
""".trimIndent()



private fun parseStructuredFeedback(response: String): AnswerAiFeedbackEntity {
    val cleaned = response.trim()

    val ratingRegex = Regex("""Рейтинг:\s*(\d+)""", RegexOption.IGNORE_CASE)
    val rating = ratingRegex.find(cleaned)
        ?.groupValues?.get(1)
        ?.toIntOrNull()
        ?.coerceIn(1, 10) ?: -1

    val goodParts = extractSectionBulletPoints(cleaned, "Сильные стороны:", "Слабые стороны:")
    val badParts = extractSectionBulletPoints(cleaned, "Слабые стороны:", null)

    return AnswerAiFeedbackEntity(
        ratingFromAi = rating,
        goodPartAnswer = goodParts,
        badPartAnswer = badParts
    )
}

private fun extractSectionBulletPoints(
    text: String,
    startKeyword: String,
    endKeyword: String?
): List<String> {
    val result = mutableListOf<String>()

    // Гибкий поиск начала раздела (игнорируем лишние пробелы, жирный шрифт и т.п.)
    val startRegex = Regex("""${Regex.escape(startKeyword)}\s*""", RegexOption.IGNORE_CASE)
    val startMatch = startRegex.find(text) ?: return result
    val startIndex = startMatch.range.last + 1

    // Поиск конца
    val endIndex = endKeyword?.let {
        val endRegex = Regex("""${Regex.escape(it)}\s*""", RegexOption.IGNORE_CASE)
        endRegex.find(text, startIndex)?.range?.first ?: text.length
    } ?: text.length

    val sectionText = text.substring(startIndex, endIndex)

    val lines = sectionText.lines()
    var currentPoint: StringBuilder? = null

    for (line in lines) {
        val trimmed = line.trim()
        if (trimmed.isEmpty()) continue

        // Это пункт списка?
        if (trimmed.startsWith("- ") || trimmed.startsWith("• ") || trimmed.startsWith("* ") || trimmed.matches(
                Regex("""^\d+\.\s+.*""")
            )
        ) {
            currentPoint?.let { if (it.isNotBlank()) result.add(it.toString().trim()) }
            currentPoint = StringBuilder(
                trimmed.removePrefix("-").removePrefix("•").removePrefix("*")
                    .removePrefix("""\d+\.""").trim()
            )
        }
        // Продолжение предыдущего пункта
        else if (currentPoint != null && (trimmed.firstOrNull()
                ?.isLetterOrDigit() == true || trimmed.startsWith(","))
        ) {
            currentPoint.append(" $trimmed")
        }
        // Это "Нет" как ответ
        else if (trimmed.equals("Нет", ignoreCase = true)) {
            result.add("Нет")
            break
        }
    }

    currentPoint?.let { if (it.isNotBlank()) result.add(it.toString().trim()) }

    return if (result.isEmpty()) listOf("Нет") else result
}