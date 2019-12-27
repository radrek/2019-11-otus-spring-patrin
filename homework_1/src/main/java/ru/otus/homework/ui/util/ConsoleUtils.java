package ru.otus.homework.ui.util;

import org.apache.logging.log4j.util.Strings;

import java.util.Scanner;

public class ConsoleUtils {

    public String askUserAndGetNotBlankAnswer(String question, Scanner in) {
        String result;
        while(true) {
            System.out.print(question);
            result = in.nextLine();
            if (Strings.isNotBlank(result)) {
                return result;
            }
            System.out.println("Empty string is not allowed.");
        }
    }
}
