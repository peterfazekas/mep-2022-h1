package com.epam.training.toto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Round {

    private final int year;
    private final int week;
    private final int roundOfWeek;
    private final LocalDate date;
    private final List<Outcome> outcomes;
    private final List<Hit> hits;

}
