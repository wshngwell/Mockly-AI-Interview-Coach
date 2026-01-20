package com.example.interviewaicoach.data.mappers

import com.example.interviewaicoach.data.mappers.MapperConsts.AI_MODEL_NAME_FOR_TEXT
import com.example.interviewaicoach.data.mappers.MapperConsts.SYSTEM_ROLE_NAME
import com.example.interviewaicoach.data.mappers.MapperConsts.USER_ROLE_NAME
import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatMessageForTextDto
import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatRequestTextDto
import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatStreamChunkDto
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.presentation.GsonUtil.fromJson

fun String.mapToQuestionEntity(
    categoryName: String,
    gradeName: String,
) =
    runCatching {
        parseSingleQuestion(
            gradeName,
            categoryName,
            this
        )
    }.getOrElse {
        QuestionEntity(
            questionContent = "",
            categoryName = categoryName,
            gradeName = gradeName,
            questionTopic = ""
        )
    }


fun mapCategoryWithGradeToChatRequestForQuestion(
    categoryName: String,
    gradeName: String,
    savedBySystemQuestions: List<String>,
) = ChatRequestTextDto(
    model = AI_MODEL_NAME_FOR_TEXT,
    messages = listOf(
        ChatMessageForTextDto(
            role = SYSTEM_ROLE_NAME,
            content = makeSystemPromptOneQuestion(
                directionInIT = categoryName,
                grade = gradeName
            )
        ),

        ChatMessageForTextDto(
            role = USER_ROLE_NAME,
            content = makeUserPromptForNewQuestion(savedBySystemQuestions)
        )
    )
)

fun parseSingleQuestion(
    gradeName: String,
    categoryName: String,
    text: String
): QuestionEntity {
    val lines = text.split("\n").map { it.trim() }
    val topicIndex = lines.indexOfFirst { it.isNotEmpty() }

    if (topicIndex == -1) {
        return QuestionEntity(
            questionTopic = "",
            questionContent = "",
            categoryName = categoryName,
            gradeName = gradeName
        )
    }

    val topic = lines[topicIndex]
    val contentLines = lines.drop(topicIndex + 1).filter { it.isNotEmpty() }

    val content = if (contentLines.isEmpty()) {
        ""
    } else {
        contentLines.joinToString(" ")
    }

    return QuestionEntity(
        questionTopic = topic,
        questionContent = content,
        categoryName = categoryName,
        gradeName = gradeName
    )
}

fun makeSystemPromptOneQuestion(directionInIT: String, grade: String) = """
    Ты — эксперт по подготовке к собеседованиям в выбранной профессиональной сфере.
    Выбранное направление: $directionInIT.
    Уровень позиции (грейд): $grade.
    Текущая дата: декабрь 2025 года.

    Твоя задача — сгенерировать РОВНО ОДИН реальный и типичный вопрос с собеседований, который ТОЧНО соответствует сложности и ожиданиям для грейда $grade в направлении $directionInIT.

    ОБЯЗАТЕЛЬНОЕ СООТВЕТСТВИЕ ГРЕЙДУ:
    - Junior: базовые понятия, фундамент, простые объяснения.
    - Middle: практика, реальные сценарии, нюансы работы.
    - Senior: глубокое понимание, оптимизация, архитектура, лучшие практики.
    - Lead: системный дизайн, масштабирование, стратегии, trade-offs.

    Запрещено генерировать вопрос сложнее или проще указанного грейда. Вопрос должен быть узким и касаться одной темы.

    СТРОЖАЙШЕЕ ПРАВИЛО ФОРМАТИРОВАНИЯ:
    Используй только обычный текст и стандартные знаки препинания. 
    ЗАПРЕЩЕНО использовать любой Markdown: никаких жирных шрифтов (**), курсива (*), решеток (#), списков или обратных кавычек.
    Только буквы, цифры и пунктуация.

    ФОРМАТ ВЫВОДА — ровно две строки:
    Topic Name
    текст вопроса на русском

    Правила строк:
    - Первая строка: короткое название темы (2–4 слова, только латиница).
    - Вторая строка: Текст вопроса на русском языке.
    - Никаких префиксов (Вопрос:, Тема:), никаких спецсимволов разметки.
    - Между строками только один перенос строки.
    - Никакого лишнего текста.
""".trimIndent()

fun makeUserPromptForNewQuestion(previousQuestions: List<String>): String {
    val formattedPrevious = if (previousQuestions.isEmpty()) {
        "Пока никаких вопросов не было задано."
    } else {
        previousQuestions
            .mapIndexed { index, question -> "${index + 1}. $question" }
            .joinToString("\n")
    }

    return """
        Предыдущие вопросы (НЕ ПОВТОРЯЙ ИХ):
        $formattedPrevious

        Сгенерируй новый уникальный вопрос.

        НАПОМИНАНИЕ ПО ФОРМАТУ:
        - Никакого Markdown (никаких **, #, ` и прочего).
        - Только две строки текста.
        - Первая строка: Topic Name (латиница).
        - Вторая строка: Вопрос на русском.
    """.trimIndent()
}

fun retrievingContentFromString(line: String): String {
    if (line.isBlank() || line.startsWith(":") || line.contains("[DONE]")) return ""

    return if (line.startsWith("data: ") && !line.contains("[DONE]")) {
        val jsonString = line.removePrefix("data: ")

        val chunk = jsonString.fromJson<ChatStreamChunkDto>()
        chunk.choices[0].delta.content ?: ""
    } else ""
}
