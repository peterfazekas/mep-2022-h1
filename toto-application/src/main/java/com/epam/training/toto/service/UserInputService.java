package com.epam.training.toto.service;

import com.epam.training.toto.data.DataParser;
import com.epam.training.toto.domain.Outcome;

import java.io.Console;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInputService {

    private static final String DATE_PATTERN = TotoFacade.DATE_PATTERN;

    private final Console console;
    private final Scanner scanner = new Scanner(System.in);
    private final DataParser dataParser;

    public UserInputService(DataParser dataParser) {
        this.console = System.console();
        this.dataParser = dataParser;
    }

    private String read() {
        return scanner.next();
    }

    public LocalDate askUserDate() {
        LocalDate date = LocalDate.now();
        boolean isDateCorrect = false;
        do {
            try {
                date = LocalDate.parse(read(), DateTimeFormatter.ofPattern(DATE_PATTERN));
                isDateCorrect = true;
            } catch (DateTimeException e) {
                System.out.println("Invalid date format, Enter Date again (" + DATE_PATTERN + "): ");
            }
        } while (!isDateCorrect);
        return date;
    }

    public List<Outcome> askUserOutcome() {
        List<Outcome> outcomes = new ArrayList<>();
        boolean isInputCorrect = false;
        do {
            String line = read();
            if (line.length() == 14) {
                try {
                    outcomes = dataParser.getOutcomes(line);
                    isInputCorrect = true;
                } catch (NoSuchElementException e) {
                    System.out.println("Invalid input, provide outcomes again (1,2,X): ");
                }
            } else System.out.print("Invalid number of outcomes, provide exactly 14 outcomes: ");
        } while (!isInputCorrect);
        return outcomes;
    }

}
