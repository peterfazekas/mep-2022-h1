package com.epam.training.toto.data;

import com.epam.training.toto.domain.Round;

import java.util.List;

public class FileBasedDataStore implements DataStore {

    private final List<Round> rounds;
    private final DataReader dataReader;
    private final DataParser dataParser;

    public FileBasedDataStore(String filename, DataReader dataReader, DataParser dataParser) {
        this.dataReader = dataReader;
        this.dataParser = dataParser;
        this.rounds = init(filename);
    }

    @Override
    public List<Round> getRounds() {
        return rounds;
    }

    private List<Round> init(String filename) {
        return dataParser.parse(dataReader.read(filename));
    }
}
