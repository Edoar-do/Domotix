package view.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HelpListener implements ActionListener {
    private static final String PERCORSO_GLOSSARIO = "C:\\Users\\edoko\\Downloads\\ING_UNIBS\\TESI_TRIENNALE\\Domotix_with_GUI\\Documentazione\\Glossario degli Errori.pdf";
    private static final String ERRORI_GLOSSARIO = "Errori Apertura Glossario Errori";
    private static final String ECCEZIONE = "Si è verificata un'eccezione che rende impossibile l'apertura del file";
    private static final String DESKTOP_NON_SUPP = "Desktop AWT non supportato";
    private static final String FILE_NON_ESISTE = "Il file PDF specificato non esiste";
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            File pdfFile = new File(PERCORSO_GLOSSARIO);
            if (pdfFile.exists()) {
                if(Desktop.isDesktopSupported())
                    Desktop.getDesktop().open(pdfFile);
                else
                    JOptionPane.showOptionDialog(null, DESKTOP_NON_SUPP , ERRORI_GLOSSARIO, -1,0, null, null, null);
            } else
                JOptionPane.showOptionDialog(null,FILE_NON_ESISTE, ERRORI_GLOSSARIO, -1,0, null, null, null);
        } catch (IOException ex) {
            JOptionPane.showOptionDialog(null, ECCEZIONE, ERRORI_GLOSSARIO, -1,0, null, null, null);
        }
    }
}
