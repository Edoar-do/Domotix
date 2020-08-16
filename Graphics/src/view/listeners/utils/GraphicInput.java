package view.listeners.utils;

import javax.swing.*;
import java.util.InputMismatchException;

public class GraphicInput {

    private final static String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto" ;
    private final static String ERRORE_MINIMO= "Attenzione: e' richiesto un valore maggiore o uguale a ";

    public static double leggiDouble (String messaggio, String chiarificatore){
        boolean finito = false;
        double valoreLetto = 0;
        JTextField campoTesto = new JTextField(20);
        campoTesto.setToolTipText(chiarificatore);
        do{
            JOptionPane.showOptionDialog(null, campoTesto, messaggio, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null );
            try
            {
                valoreLetto = Double.parseDouble(campoTesto.getText());
                finito = true;
            }
            catch (InputMismatchException | NumberFormatException e)
            {
                JOptionPane.showOptionDialog(null, ERRORE_FORMATO, "Formato errato", -1, 0, null, null,  null);
            }
        } while (!finito);
        return valoreLetto;
    }

    public static double leggiDoubleConMinimo (String messaggio, String chiarificatore,  double minimo){
        boolean finito = false;
        double valoreLetto = 0;
        do{
            valoreLetto = leggiDouble(messaggio, chiarificatore);
            if (valoreLetto >= minimo)
                finito = true;
            else
                JOptionPane.showOptionDialog(null, ERRORE_FORMATO, "Errore minimo valore richiesto", -1, 0, null, null,  null);
        } while (!finito);

        return valoreLetto;
    }

    public static boolean yesOrNo(String messaggio, String titolo){
        int valoreLetto = JOptionPane.showOptionDialog(null, messaggio, titolo,
                0, 3, null, new String[]{"SI", "NO"}, null);

        if (valoreLetto == 0)
            return true;
        else
            return false;
    }

    public static String leggiStringa (String messaggio, String suggerimento){
        JTextField campoTesto = new JTextField(20);
        campoTesto.setToolTipText(suggerimento);
        JOptionPane.showOptionDialog(null, campoTesto, messaggio, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        return campoTesto.getText();
    }

    public static String leggiStringaNonVuota(String messaggio, String suggerimento){
        boolean finito=false;
        String lettura = null;
        do {
            lettura = leggiStringa(messaggio, suggerimento);
            lettura = lettura.trim();
            if (lettura.length() > 0)
                finito=true;
            else
                JOptionPane.showOptionDialog(null, "Attenzione! E' richiesta una stringa non vuota", "Stringa vuota", -1, 0, null, null, null);
        } while (!finito);

        return lettura;
    }


}
