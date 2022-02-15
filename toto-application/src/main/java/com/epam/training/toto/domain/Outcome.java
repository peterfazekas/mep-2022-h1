package com.epam.training.toto.domain;

import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;

public enum Outcome {
    _1("1"),
    _2("2"),
    X("X");

    private final String id;

    Outcome(String id) {
        this.id = id;
    }

    public static Outcome getOutcomeById(String id) {
        return Arrays.stream(Outcome.values())
                .filter(i -> i.id.toUpperCase(Locale.ROOT).equals(id.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No such outcome exist with value: " + id));
    }
}
