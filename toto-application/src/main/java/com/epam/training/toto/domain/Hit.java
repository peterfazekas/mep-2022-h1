package com.epam.training.toto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Hit {
    private final int hitCount;
    private final int numberOfWagers;
    private final int prize;
}
