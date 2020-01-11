package ru.otus.homework.service.question.reader;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.question.Question;
import ru.otus.homework.domain.question.MultipleChoiceQuestion;
import ru.otus.homework.service.question.additional.AllowedLanguage;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Locale;

@Service
public class CsvQuestionReader implements QuestionReader {

    private static final Logger LOGGER = LogManager.getLogger(CsvQuestionReader.class);

    private final String csvPathTemplate;
    private final String defaultLanguage;

    public CsvQuestionReader(@Value("${csv.path.template}") String csvPathTemplate,
                             @Value("default.language") String defaultLanguage) {
        this.csvPathTemplate = csvPathTemplate;
        this.defaultLanguage = defaultLanguage;
    }

    @Override
    public List<Question> readQuestions(Locale locale) {
        LOGGER.info("Read questions from csv");
        try {
            File csv = new ClassPathResource(getCsvPath(locale)).getFile();
            return new CsvToBeanBuilder<Question>(new FileReader(csv))
                    .withSeparator(';')
                    .withType(MultipleChoiceQuestion.class)
                    .build().parse();
        } catch (Exception e) {
            LOGGER.error("Can't read or parse csv file", e);
        }
        return null;
    }

    private String getCsvPath(Locale locale) {
        String path;
        AllowedLanguage language = EnumUtils.getEnumIgnoreCase(AllowedLanguage.class, locale.getLanguage());
        if (language != null) {
            path = String.format(csvPathTemplate, language.name());
        } else {
            path = String.format(csvPathTemplate, defaultLanguage);
        }
        return path;
    }
}
