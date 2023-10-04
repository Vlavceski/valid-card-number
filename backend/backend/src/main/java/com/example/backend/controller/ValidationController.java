package com.example.backend.controller;

import com.example.backend.entity.CreditCardData;
import com.example.backend.entity.CreditCardEntity;
import com.example.backend.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class ValidationController {
    @Autowired
    private CreditCardRepository cardRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/api/validate")
    public ValidationResponse validateCreditCard(@RequestBody CreditCardData cardData) {

        boolean isValid = validateCreditCardData(cardData);

        return new ValidationResponse(isValid);
    }

    private boolean validateCreditCardData(CreditCardData cardData) {


        if (!isValidExpiryDate(cardData.getExpiryDate())) {
            System.out.println("expiryDate "+ cardData.getExpiryDate());
            return false;
        }

        if (!isValidCvv(cardData.getCardNumber(), cardData.getCvv())) {
            System.out.println("ValidCvv "+ cardData.getCvv());
            return false;
        }

        if (!isValidPan(cardData.getCardNumber())) {
            System.out.println("ValidPAN " +cardData.getCardNumber());
            return false;
        }

        String number = cardData.getCardNumber().replaceAll("\\D", "");

        CreditCardEntity cardEntity = new CreditCardEntity();
        cardEntity.setCardNumber(number);
        cardEntity.setExpiryDate(cardData.getExpiryDate());
        cardEntity.setCvv(cardData.getCvv());
        cardRepository.save(cardEntity);

        return true;
    }

    private boolean isValidExpiryDate(String expiryDate) {
        if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }

        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);


        int currentMonth = getCurrentMonthUsingCalendar();
        int currentYear = getCurrentYearUsingLocalDate();


        if (year < (currentYear % 100) || (year == (currentYear % 100) && month < currentMonth)) {
            return false;
        }

        return true;
    }
    public static int getCurrentMonthUsingCalendar() {

//        Calendar calendar = Calendar.getInstance();
//        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        return currentMonth;
    }
    public static int getCurrentYearUsingLocalDate() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        return currentYear;
    }

    private boolean isValidCvv(String cardNumber, String cvv) {

        boolean isAmex = cardNumber.startsWith("34") || cardNumber.startsWith("37");
        int expectedCvvLength = isAmex ? 4 : 3;

        return cvv != null && cvv.length() == expectedCvvLength && cvv.matches("\\d+");
    }

    private boolean isValidPan(String cardNumber) {
        String CardNumber = cardNumber.replaceAll("\\D", "");

        if (CardNumber.length() < 16 || CardNumber.length() > 19) {
            return false;
        }

        // ???
        int sum = 0;
        boolean doubleDigit = false;

        for (int i = CardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(CardNumber.charAt(i));

            if (doubleDigit) {
                digit *= 2;
            }
            sum += digit / 10;
            sum += digit % 10;
            doubleDigit = !doubleDigit;
        }

        return (sum % 10 == 0);
    }

    private class ValidationResponse {

        private final boolean success;

        ValidationResponse(boolean success) {

            this.success = success;
        }

    }
}
