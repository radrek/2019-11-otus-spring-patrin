package ru.otus.homework.service.question.reader;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.question.Question;
import ru.otus.homework.domain.question.MultipleChoiceQuestion;

import java.io.File;
import java.io.FileReader;
import java.util.List;

@Service
public class CsvQuestionReader implements QuestionReader {

    private static final Logger LOGGER = LogManager.getLogger(CsvQuestionReader.class);

    private final String csvPath;

    public CsvQuestionReader(@Value("${csv.path}")String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public List<Question> readQuestions() {
        LOGGER.info("Read questions from csv");
        try {
            File csv = new ClassPathResource(csvPath).getFile();
            return new CsvToBeanBuilder<Question>(new FileReader(csv))
                    .withSeparator(';')
                    .withType(MultipleChoiceQuestion.class)
                    .build().parse();
        } catch (Exception e) {
            LOGGER.error("Can't read or parse csv file", e);
        }
        return null;
    }
}
