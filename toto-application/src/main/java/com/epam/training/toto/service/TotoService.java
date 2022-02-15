package com.epam.training.toto.service;

import com.epam.training.toto.data.DataStore;
import com.epam.training.toto.domain.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TotoService {

    private final List<Round> rounds;

    public TotoService(DataStore dataStore) {
        this.rounds = dataStore.getRounds();
    }

    public Result getResult(LocalDate date, List<Outcome> outcomes) throws NoSuchElementException {
        var roundByDate = getRoundByDate(date);
        var outcomesByDate = roundByDate.getOutcomes();
        long hitCount = getHitCount(outcomes, outcomesByDate);
        var prize = getPrize(roundByDate.getHits(), hitCount);
        return new Result((int) hitCount, prize);
    }

    private Round getRoundByDate(LocalDate date) {
        return rounds.stream()
                .filter(round -> round.getDate().equals(date))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private long getHitCount(List<Outcome> outcomes, List<Outcome> outcomesByDate) {
        return IntStream.range(0, outcomesByDate.size())
                .filter(i -> outcomes.get(i) == outcomesByDate.get(i))
                .count();
    }

    private Integer getPrize(List<Hit> hits, long hitCount) {
        return hits.stream()
                .filter(i -> hitCount > 9 && i.getHitCount() == hitCount)
                .map(Hit::getPrize)
                .findFirst()
                .orElse(0);
    }

    public Statistics calculateStatistics() {
        double firstTeamWinPercentage = 0, secondTeamWinPercentage = 0, drawPercentage = 0;
        var total = rounds.stream()
                .mapToLong(i -> i.getOutcomes().size())
                .sum();
        var collect = rounds.stream()
                .flatMap(i -> i.getOutcomes().stream())
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        if (total != 0) {
            firstTeamWinPercentage = 100.0 * collect.get(Outcome._1) / total;
            secondTeamWinPercentage = 100.0 * collect.get(Outcome._2) / total;
            drawPercentage = 100.0 * collect.get(Outcome.X) / total;
        }
        return new Statistics(firstTeamWinPercentage, secondTeamWinPercentage, drawPercentage);
    }

    public int getLargestPrize() {
        return rounds.stream()
                .flatMap(i -> i.getHits().stream())
                .map(Hit::getPrize)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}
