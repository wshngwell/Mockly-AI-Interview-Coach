package com.example.interviewaicoach.data.mappers

import com.example.interviewaicoach.data.mappers.MapperConsts.AI_MODEL_NAME
import com.example.interviewaicoach.data.mappers.MapperConsts.SYSTEM_ROLE_NAME
import com.example.interviewaicoach.data.mappers.MapperConsts.USER_ROLE_NAME
import com.example.interviewaicoach.data.remote.dto.ChatMessageDto
import com.example.interviewaicoach.data.remote.dto.ChatRequestDto
import com.example.interviewaicoach.data.remote.dto.ChatResponseDto
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.GradeEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

fun ChatResponseDto.mapToQuestionEntity(
    categoryName: String,
    gradeEntity: GradeEntity,
) =
    runCatching {
        parseSingleQuestion(
            gradeEntity,
            categoryName,
            choices[0].message!!.content!!
        )
    }.getOrElse {
        QuestionEntity(
            questionContent = "",
            categoryName = categoryName,
            gradeEntity = gradeEntity
        )
    }

fun mapCategoryWithGradeToChatRequestForQuestion(
    categoryName: String,
    gradeEntity: GradeEntity,
    savedBySystemQuestions: List<String>,
) = ChatRequestDto(
    model = AI_MODEL_NAME,
    messages = listOf(
        ChatMessageDto(
            role = SYSTEM_ROLE_NAME,
            content = makeSystemPromptOneQuestion(
                directionInIT = categoryName,
                grade = gradeEntity.name
            )
        ),

        ChatMessageDto(
            role = USER_ROLE_NAME,
            content = makeUserPromptForNewQuestion(savedBySystemQuestions)
        )
    )
)

fun parseSingleQuestion(
    gradeEntity: GradeEntity,
    categoryName: String,
    text: String
): QuestionEntity {

    val regex = Regex("""Вопрос:\s*(.+)$""", setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))

    return when (val match = regex.find(text.trim())) {
        null -> QuestionEntity(
            questionContent = "",
            categoryName = categoryName,
            gradeEntity = gradeEntity
        )

        else -> QuestionEntity(
            questionContent = match.groupValues[1].trim(),
            categoryName = categoryName,
            gradeEntity = gradeEntity
        )
    }
}

fun makeSystemPromptOneQuestion(directionInIT: String, grade: String) = """
    Ты — эксперт по подготовке к собеседованиям в IT.
    Пользователь выбрал направление: $directionInIT.
    Уровень позиции (грейд): $grade.
    
    Твоя задача — сгенерировать РОВНО ОДИН реальный, типичный вопрос с собеседований для уровня $grade в направлении $directionInIT.
    Вопрос должен точно соответствовать сложности и тематике этого грейда.
    
    КРИТИЧЕСКИ ВАЖНЫЕ ПРАВИЛА (НАРУШЕНИЕ ЛЮБОГО — НЕДОПУСТИМО):
    - ВЕСЬ текст вопроса должен быть ИСКЛЮЧИТЕЛЬНО на русском языке.
    - Запрещено использовать английский, китайский, любой другой язык или иероглифы в формулировке вопроса.
    - Общепринятые IT-термины (Kotlin Coroutines, launch, async, Dispatchers.IO, REST API, SOLID, Docker и т.д.) оставляй на английском — это разрешено.
    - Все объяснения, связки, глаголы и остальной текст — только по-русски.
    - Ни в коем случае не вставляй фразы на других языках, даже частично.
    
    Выводи СТРОГО в следующем формате и больше абсолютно ничего:
    Вопрос: [текст вопроса на русском языке]
    
    Запрещено:
    - Добавлять ответы, пояснения, примеры кода, нумерацию.
    - Использовать markdown, жирный шрифт, кавычки или другой форматинг.
    - Писать введение, заключение или любой дополнительный текст.
    - Генерировать больше одной строки с вопросом.
    - Повторять или частично переводить вопрос на другой язык.
    
    Пример правильного вывода:
    Вопрос: Объясните разницу между launch и async в Kotlin Coroutines и в каких случаях вы используете каждый из них?
    
    Ещё один пример:
    Вопрос: Какой Dispatcher вы выбираете для операций ввода-вывода в Android и почему?
    
    Генерируй ТОЛЬКО одну строку в точном формате выше.
    Учитывай грейд $grade при выборе сложности вопроса.
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
        Предыдущие вопросы, которые уже были заданы (НЕ ПОВТОРЯЙ ни один из них — ни дословно, ни по смыслу, ни с похожей формулировкой. Выбирай совершенно другую тему или аспект):
        $formattedPrevious

        Сгенерируй новый уникальный вопрос, полностью отличающийся от всех перечисленных выше.
        Учитывай направление и грейд из системного промпта.
        Выводи строго в формате: Вопрос: [текст вопроса]
    """.trimIndent()
}


