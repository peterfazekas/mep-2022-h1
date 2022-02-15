package com.epam.training.toto.service;

import com.epam.training.toto.data.DataParser;
import com.epam.training.toto.data.DataStore;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Result;

import java.time.LocalDate;
import java.util.List;

public class TotoFacade {

    public static final String DATE_PATTERN = "yyyy.MM.dd.";

    private final TotoService totoService;
    private final UserInputService userInputService;
    private final StatisticService statisticService;

    public TotoFacade(DataStore dataStore) {
        DataParser dataParser = new DataParser();
        totoService = new TotoService(dataStore);
        statisticService = new StatisticService(totoService);
        userInputService = new UserInputService(dataParser);
    }

    public LocalDate askUserDate() {
        return userInputService.askUserDate();
    }

    public List<Outcome> askUserOutcome() {
        return userInputService.askUserOutcome();
    }

    public String printInformation() {
        return statisticService.printInformation();
    }

    public Result getResult(LocalDate date, List<Outcome> outcomes) {
        return totoService.getResult(date, outcomes);
    }

}
