package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

public interface CardService {
    public String getRandomNumberCard();
    public int getRandomCvv();
    public void createCardToAccount(String email, CardType cardType, CardColor cardColor);
}
