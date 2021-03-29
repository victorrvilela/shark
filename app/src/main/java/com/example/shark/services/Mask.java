package com.example.shark.services;

public class Mask {

    public static String addMask(final String textoAFormatar, final String mask) {
        String formatado = "";
        int i = 0;
        // vamos iterar a mascara, para descobrir quais caracteres vamos adicionar e quando...
        for (char m : mask.toCharArray()) {
            if (m != '#') { // se não for um #, vamos colocar o caracter informado na máscara
                formatado += m;
                continue;
            }
            // Senão colocamos o valor que será formatado
            try {
                formatado += textoAFormatar.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        return formatado;
    }
}
