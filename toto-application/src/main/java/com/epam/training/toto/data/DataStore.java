package com.epam.training.toto.data;

import com.epam.training.toto.domain.Round;

import java.util.List;

public interface DataStore {
    List<Round> getRounds();
}
