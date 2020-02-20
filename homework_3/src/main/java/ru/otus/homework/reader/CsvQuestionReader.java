package ru.otus.homework.reader;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.homework.question.MultipleChoiceQuestion;
import ru.otus.homework.question.Question;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            File csv = getCsvFile(locale);
            return new CsvToBeanBuilder<Question>(new FileReader(csv))
                    .withSeparator(';')
                    .withType(MultipleChoiceQuestion.class)
                    .build().parse();
        } catch (Exception e) {
            LOGGER.error("Can't read or parse csv file", e);
        }
        return null;
    }

    private File getCsvFile(Locale locale) throws IOException {
        File csv = null;
        if (locale != null) {
            ClassPathResource csvResource = new ClassPathResource(getCsvPath(locale));
            if (csvResource.exists()) {
                csv = csvResource.getFile();
            }
        }
        if (csv == null) {
            csv = new ClassPathResource(getDefaultCsvPath()).getFile();
        }
        return csv;
    }

    private String getCsvPath(Locale locale) {
        return String.format(csvPathTemplate, locale.getLanguage());
    }

    private String getDefaultCsvPath() {
        return String.format(csvPathTemplate, defaultLanguage);
    }
}
