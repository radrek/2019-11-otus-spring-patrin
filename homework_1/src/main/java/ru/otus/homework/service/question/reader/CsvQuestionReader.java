package ru.otus.homework.service.question.reader;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import ru.otus.homework.domain.question.Question;
import ru.otus.homework.domain.question.MultipleChoiceQuestion;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class CsvQuestionReader implements QuestionReader {

    private String csvPath;

    public CsvQuestionReader(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public List<Question> readQuestions() {
        try {
            File csv = new ClassPathResource(csvPath).getFile();
            if (!csv.exists()) {
                //todo: Logger
                return null;
            }
            return new CsvToBeanBuilder<Question>(new FileReader(csv))
                    .withSeparator(';')
                    .withType(MultipleChoiceQuestion.class)
                    .build().parse();
        } catch (Exception e) {
            //todo: Logger
        }
        return null;
    }
}
