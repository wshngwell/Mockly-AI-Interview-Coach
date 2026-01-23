package com.example.interviewaicoach.presentation.utils

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerAiFeedbackEntity

val mockQuesstionText: String = "Объясни разницу между head и stack?"
val mockTopicOfAuestionText: String = "#01 Heap and stack"
val mockAnswerText: String =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla quis pharetra erat, quis blandit ligula. Cras eget arcu lorem. Duis et ipsum molestie, cursus ligula non, porttitor nisl. Cras eget mollis augue. Mauris lobortis, magna quis pharetra facilisis, purus nisi lobortis erat, vitae gravida eros nibh eu odio. Nam iaculis dui id pretium interdum. Pellentesque id orci sit amet nisi molestie hendrerit sit amet vel ligula. Nulla elementum dictum purus, ut bibendum libero tincidunt id. Ut ut congue nisl. Sed at nibh et lectus commodo elementum. Curabitur ut lectus nec enim semper semper.\n" +
            "\n" +
            "Sed condimentum diam non lorem viverra, quis auctor arcu mattis. Curabitur vitae nisl sit amet lectus sodales gravida quis nec tellus. Aenean non volutpat lectus. Nullam sed egestas leo. Ut auctor orci enim, nec venenatis sem viverra quis. Vestibulum molestie mauris at semper scelerisque. Sed tempor mi tortor, in mollis magna pellentesque at. Sed hendrerit, magna nec aliquet vulputate, elit lorem suscipit odio, vel vestibulum neque dui in neque. Pellentesque tristique pellentesque interdum. Pellentesque pretium est eu elit rutrum dapibus. In bibendum hendrerit gravida. Suspendisse commodo, diam commodo commodo rhoncus, tortor enim imperdiet ante, vel fermentum neque leo dignissim sapien.\n" +
            "\n" +
            "Nulla sit amet magna maximus, congue arcu eget, viverra quam. Aenean vitae quam at lacus dapibus malesuada. Praesent cursus orci eget risus efficitur, ac dapibus nulla efficitur. Maecenas vel quam et magna eleifend eleifend id ac turpis. Vestibulum consequat, velit et porttitor sodales, nunc erat tristique lacus, quis pretium lorem purus sit amet velit. Nunc quis urna et felis dapibus euismod. Etiam varius mattis lacus quis venenatis. Mauris auctor diam id lacus varius, a sodales mi lacinia.\n" +
            "\n" +
            "Suspendisse tempus finibus egestas. Vivamus placerat pellentesque magna, ac vestibulum felis lacinia quis. Aenean posuere augue elementum pellentesque lobortis. In mollis posuere tortor nec faucibus. Donec fringilla tincidunt rhoncus. Nulla ut tempus erat. Aenean vulputate sodales ipsum, ac aliquam ex mattis a. Sed congue, lorem quis vehicula posuere, metus nisi luctus neque, eget pellentesque tortor magna sed tellus. Curabitur tortor ante, bibendum tincidunt convallis eget, lacinia a massa. Aenean a sodales nisl. Suspendisse potenti.\n" +
            "\n" +
            "Vestibulum vel nibh convallis, ornare ipsum at, maximus ante. Morbi mattis leo ac consequat euismod. Phasellus consequat suscipit lectus non scelerisque. Nulla eget efficitur nulla. Praesent vestibulum rhoncus nulla, nec pellentesque lectus luctus vitae. In accumsan, sem sit amet faucibus facilisis, eros lacus interdum risus, sed ultrices lectus ipsum ac leo. Sed mattis, lacus vitae condimentum tempus, arcu nulla finibus ante, id fermentum ipsum elit sit amet diam. Curabitur condimentum mauris semper convallis commodo. Nulla consequat dictum ligula, faucibus pulvinar nunc rhoncus id."

val mockListOfAiGoodAnswers = listOf<String>(
    "Strong answer structure",
    "Good technical knowledge",
    "Deep understanding about «how computer memory works»"
)
val mockAnswerAiFeedbackEntity = AnswerAiFeedbackEntity(
    ratingFromAi = 1,
    goodPartAnswer = mockListOfAiGoodAnswers,
    badPartAnswer = mockListOfAiGoodAnswers
)