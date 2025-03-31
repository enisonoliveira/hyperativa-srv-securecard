package com.hyperativa.payment.securecard.application.util;

import java.util.regex.Pattern;

public class CardNumberValidate {
    

    public boolean isValidCardNumber(String cardNumber) {
        if (!Pattern.matches("\\d{13,19}", cardNumber)) {
            return false;
        }
    
        return luhnCheck(cardNumber);
    }
    
    public boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(cardNumber.charAt(i));
            
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9; 
                }
            }
            
            sum += n;
            alternate = !alternate;
        }
        
        return (sum % 10 == 0);
    }
}
