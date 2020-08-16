package view.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HelpListener implements ActionListener {
    private static final String PERCORSO_ERRORI = "C:\\Users\\edoko\\Downloads\\ING_UNIBS\\TESI_TRIENNALE\\Domotix_with_GUI\\Documentazione\\Glossario degli Errori.pdf";
    private static final String PERCORSO_GLOSSARIO = "C:\\Users\\edoko\\Downloads\\ING_UNIBS\\TESI_TRIENNALE\\Domotix_with_GUI\\Documentazione\\Glossario.pdf";
    private static final String PERCORSO_IMPORTAZIONE = "C:\\Users\\edoko\\Downloads\\ING_UNIBS\\TESI_TRIENNALE\\Domotix_with_GUI\\Documentazione\\Librerie e importazione.pdf";
    private static final String ERRORE_APERTURA = "Errori Apertura ";
    private static final String ECCEZIONE = "Si è verificata un'eccezione che rende impossibile l'apertura del file";
    private static final String DESKTOP_NON_SUPP = "Desktop AWT non supportato";
    private static final String FILE_NON_ESISTE = "Il file cercato non esiste. Scaricalo da: \n " + "https://github.com/Edoar-do/Domotix/tree/master/Documentazione";
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        File pdfFile;

        if(actionCommand.equalsIgnoreCase("errori")){
            pdfFile = new File(PERCORSO_ERRORI);
            try {
                if (pdfFile.exists()) {
                    if(Desktop.isDesktopSupported())
                        Desktop.getDesktop().open(pdfFile);
                    else
                        JOptionPane.showOptionDialog(null, DESKTOP_NON_SUPP , ERRORE_APERTURA + "Glossario degli errori", -1,0, null, null, null);
                } else
                    JOptionPane.showOptionDialog(null,FILE_NON_ESISTE, ERRORE_APERTURA + "Glossario degli errori", -1,0, null, null, null);
            } catch (IOException ex) {
                JOptionPane.showOptionDialog(null, ECCEZIONE, ERRORE_APERTURA + "Glossario degli errori", -1,0, null, null, null);
            }
        }else if(actionCommand.equalsIgnoreCase("glossario")){
            pdfFile = new File(PERCORSO_GLOSSARIO);
            try {
                if (pdfFile.exists()) {
                    if(Desktop.isDesktopSupported())
                        Desktop.getDesktop().open(pdfFile);
                    else
                        JOptionPane.showOptionDialog(null, DESKTOP_NON_SUPP , ERRORE_APERTURA + "Glossario informativo", -1,0, null, null, null);
                } else
                    JOptionPane.showOptionDialog(null,FILE_NON_ESISTE, ERRORE_APERTURA + "Glossario informativo", -1,0, null, null, null);
            } catch (IOException ex) {
                JOptionPane.showOptionDialog(null, ECCEZIONE, ERRORE_APERTURA + "Glossario informativo", -1,0, null, null, null);
            }
        }else{ //IMPORTAZIONE
            pdfFile = new File(PERCORSO_IMPORTAZIONE);
            try {
                if (pdfFile.exists()) {
                    if(Desktop.isDesktopSupported())
                        Desktop.getDesktop().open(pdfFile);
                    else
                        JOptionPane.showOptionDialog(null, DESKTOP_NON_SUPP , ERRORE_APERTURA + "Librerie ed Importazione", -1,0, null, null, null);
                } else
                    JOptionPane.showOptionDialog(null,FILE_NON_ESISTE, ERRORE_APERTURA + "Librerie ed Importazione", -1,0, null, null, null);
            } catch (IOException ex) {
                JOptionPane.showOptionDialog(null, ECCEZIONE, ERRORE_APERTURA + "Librerie ed Importazione", -1,0, null, null, null);
            }
        }

    }
}
