package com.epam.training.toto.service;

import com.epam.training.toto.domain.Statistics;

public class StatisticService {

    private final TotoService totoService;

    public StatisticService(TotoService totoService) {
        this.totoService = totoService;
    }

    public String printInformation() {
        Statistics stats = totoService.calculateStatistics();
        return String.format("The largest prize ever recorded: %s\nStatistics: team #1 won: %s %%, team #2 won: %s %%, draw: %s %%",
                FormatService.formattedCurrency(totoService.getLargestPrize()),
                FormatService.formattedNumber(stats.getFirstTeamWinPercentage()),
                FormatService.formattedNumber(stats.getSecondTeamWinPercentage()),
                FormatService.formattedNumber(stats.getDrawPercentage()));
    }
}
