package ru.otus.homework.reader;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.homework.question.MultipleChoiceQuestion;
import ru.otus.homework.question.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
            Reader csv = getCsvFile(locale);
            return new CsvToBeanBuilder<Question>(csv)
                    .withSeparator(';')
                    .withType(MultipleChoiceQuestion.class)
                    .build().parse();
        } catch (Exception e) {
            LOGGER.error("Can't read or parse csv file", e);
        }
        return null;
    }

    private Reader getCsvFile(Locale locale) throws IOException {
        Reader csv = null;
        if (locale != null) {
            ClassPathResource csvResource = new ClassPathResource(getCsvPath(locale));
            if (csvResource.exists() && csvResource.isFile()) {
                csv = new InputStreamReader(csvResource.getInputStream());
            }
        }
        if (csv == null) {
            ClassPathResource defaultCsvResource = new ClassPathResource(getDefaultCsvPath());
            csv = new InputStreamReader(defaultCsvResource.getInputStream());
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
