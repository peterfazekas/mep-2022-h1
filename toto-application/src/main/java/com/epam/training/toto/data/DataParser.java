package com.epam.training.toto.data;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.TotoFacade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataParser {

    private static final String CURRENCY_FT = " Ft";
    private static final String DATE_PATTERN = TotoFacade.DATE_PATTERN;
    private static final String SEPARATOR = ";";
    private static final String EMPTY_STRING = "";
    private static final String TWO = "2";

    public List<Round> parse(List<String> lines) {
        return lines.stream()
                .map(this::createRound)
                .collect(Collectors.toList());
    }

    private Round createRound(String line) {
        var splitLine = line.split(SEPARATOR);
        var year = getValue(splitLine[0]);
        var week = getValue(splitLine[1]);
        var roundOfTheWeek = TWO.equals(splitLine[2]) ? 2 : 1;
        var date = getLocalDate(splitLine[3], year, week, roundOfTheWeek);
        var hits = getHits(subList(splitLine, 4, 14));
        var outcomes = getOutcomes(subList(splitLine, 14));
        return new Round(year, week, roundOfTheWeek, date, outcomes, hits);
    }

    private List<String> subList(String[] array, int startInclusive) {
        return subList(array, startInclusive, array.length);
    }

    private List<String> subList(String[] array, int startInclusive, int endExclusive) {
        return Arrays.stream(array, startInclusive, endExclusive)
                .collect(Collectors.toList());
    }

    private LocalDate getLocalDate(String date, int year, int week, int roundOfTheWeek) {
        return EMPTY_STRING.equals(date)
                ? calculateDate(year, week, roundOfTheWeek)
                : LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    private LocalDate calculateDate(int year, int week, int roundOfWeek) {
        var weekFields = WeekFields.of(Locale.getDefault());
        return LocalDateTime.now().withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), roundOfWeek)
                .toLocalDate();
    }

    public List<Outcome> getOutcomes(String outcomes) {
        return outcomes.chars()
                .mapToObj(i -> String.valueOf((char) i))
                .map(Outcome::getOutcomeById)
                .collect(Collectors.toList());
    }


    private List<Outcome> getOutcomes(List<String> splitLine) {
        return splitLine.stream()
                .map(Outcome::getOutcomeById)
                .collect(Collectors.toList());
    }

    private List<Hit> getHits(List<String> values) {
        return IntStream.iterate(0, i -> i + 2).limit(values.size() / 2)
                .mapToObj(index -> createHit(index, values.get(index), values.get(index + 1)))
                .collect(Collectors.toList());
    }

    private Hit createHit(int index, String numberOfWagers, String prize) {
        var prizeNumber = prize.substring(0, prize.indexOf(CURRENCY_FT));
        int hitCount = 14 - (index / 2);
        return new Hit(hitCount,
                getValue(numberOfWagers),
                getValue(prizeNumber.replaceAll("\\s", EMPTY_STRING))
        );
    }

    private int getValue(String string) {
        return Integer.parseInt(string);
    }
}
