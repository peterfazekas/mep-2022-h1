package com.epam.training.toto;

import com.epam.training.toto.data.DataParser;
import com.epam.training.toto.data.FileBasedDataStore;
import com.epam.training.toto.data.FileReader;
import com.epam.training.toto.service.FormatService;
import com.epam.training.toto.service.TotoFacade;

import java.util.NoSuchElementException;


public class App {

    private final TotoFacade facade;

    private App() {
        facade = new TotoFacade(new FileBasedDataStore("toto.txt", new FileReader(), new DataParser()));
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println(facade.printInformation());
        System.out.print("Enter date (" + TotoFacade.DATE_PATTERN + "): ");
        var userDate = facade.askUserDate();
        System.out.print("Enter Outcomes: ");
        var userOutcomes = facade.askUserOutcome();
        try {
            var userResult = facade.getResult(userDate, userOutcomes);
            System.out.format("Result: hits: %d, amount: %s",
                    userResult.getHitCount(), FormatService.formattedCurrency(userResult.getPrize()));
        } catch (NoSuchElementException e) {
            System.out.print("There was no such round");
        }
    }
}
