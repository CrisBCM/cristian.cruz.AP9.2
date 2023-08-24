package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor,
                                             Authentication authentication)
    {
        if(clientRepository.findByEmail(authentication.getName()).getCards().size() >= 3)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Client autenticatedClient = clientRepository.findByEmail(authentication.getName());

        int numberCvv;
        String cardNumber = "";

        do{
            numberCvv = (int)Math.floor(Math.random() * (999 - 100 + 1) + 100);
        }while(cardRepository.existsByCvv(numberCvv));

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



        Card newCard = new Card(autenticatedClient.getFirstName() + " " + autenticatedClient.getLastName(), cardType, cardColor, cardNumber, numberCvv, LocalDate.now(), LocalDate.now().plusYears(5));
        autenticatedClient.addCard(newCard);

        cardRepository.save(newCard);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
