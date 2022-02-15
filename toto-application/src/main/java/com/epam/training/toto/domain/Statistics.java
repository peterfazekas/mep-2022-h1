package com.epam.training.toto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Statistics {
    private final double firstTeamWinPercentage;
    private final double secondTeamWinPercentage;
    private final double drawPercentage;

}
