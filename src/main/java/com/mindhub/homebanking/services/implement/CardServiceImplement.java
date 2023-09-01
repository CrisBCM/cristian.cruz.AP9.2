package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientService clientService;

    @Override
    public String getRandomNumberCard() {

        String cardNumber = "";

        do {
            for (int i = 1; i <= 4; i++){

                int numberSection = (int)Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);

                String section = "";

                if(i < 4) {
                    section = Integer.toString(numberSection) + "-";
                } else{
                    section = Integer.toString(numberSection);
                }

                cardNumber += section;

            }
        }while(cardRepository.existsByNumber(cardNumber));

        return cardNumber;
    }

    @Override
    public int getRandomCvv() {
        int numberCvv;

        do{
            numberCvv = (int)Math.floor(Math.random() * (999 - 100 + 1) + 100);
        }while(cardRepository.existsByCvv(numberCvv));

        return numberCvv;
    }

    @Override
    public void createCardToAccount(String email, CardType cardType, CardColor cardColor) {

        Client autenticatedClient = clientService.getClientByEmail(email);

        Card newCard = new Card(autenticatedClient.getFirstName() + " " + autenticatedClient.getLastName(), cardType, cardColor, getRandomNumberCard(), getRandomCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
        autenticatedClient.addCard(newCard);
        cardRepository.save(newCard);
    }
}
