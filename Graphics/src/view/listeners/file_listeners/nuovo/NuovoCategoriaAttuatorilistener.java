package view.listeners.file_listeners.nuovo;

import domotix.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class NuovoCategoriaAttuatorilistener implements ActionListener {

    private Interpretatore interpretatore;
    public NuovoCategoriaAttuatorilistener(Interpretatore interpretatore){
        this.interpretatore = interpretatore;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //CREAZIONE CAMPO NOME CATEGORIA
        JTextField campoNome = new JTextField(20);
        campoNome.setEnabled(true);
        campoNome.setToolTipText("Nome nuova categoria di attuatori");
        //CREAZIONE AREA TESTO LIBERO
        JTextField areaTesto = new JTextField(20);
        areaTesto.setEnabled(true);
        areaTesto.setToolTipText("Spazio per il testo libero");
        //CREAZIONE BOTTONE CREA CATEGORIA VUOTA
        JButton crea = new JButton("Crea");
        crea.setEnabled(true);
        //CREAZIONE CAMPO MODALITA OPERATIVA
        JTextField campoModalita = new JTextField(20);
        campoModalita.setEnabled(false);
        campoModalita.setToolTipText("Nome modalità operativa");
        //CREAZIONE CHECK BOX PARAMETRICA
        JCheckBox parametrica = new JCheckBox("E' parametrica? ");
        parametrica.setEnabled(false);
        //CREAZIONE BOTTONE INSERISCI MODALITA OPERATIVA + PARAMETRICA
        JButton inserisci = new JButton("Inserisci modalità");
        inserisci.setEnabled(false);
        //CREAZIONE CAMPO NOME PARAMETRO
        JTextField campoParametro = new JTextField(20);
        campoParametro.setEnabled(false);
        campoParametro.setToolTipText("Nome parametro se modalità parametrica");
        //CREAZIONE CAMPO VALORE PARAMETRO
        NumberFormat format = DecimalFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setMaximumIntegerDigits(2);
        JFormattedTextField campoValore = new JFormattedTextField(format);
        campoValore.setEnabled(false);
        campoValore.setToolTipText("Valore numerico di riferimento");
        //CREAZIONE BOTTONE AGGIUNGI PARAMETRO
        JButton aggiungiParametro = new JButton("Aggiungi parametro");
        aggiungiParametro.setEnabled(false);
        //CREAZIONE BOTTONE FATTO
        JButton fatto = new JButton("Fatto");
        fatto.setEnabled(false);
        //CREAZIONE DIALOG
        JDialog dialog = new JDialog();
        dialog.setTitle("Inserimento categoria di attuatori");
        dialog.setLayout(new BorderLayout());
        //CREAZIONE PANNELLO ALTO PER CREAZIONE CATEGORIA VUOTA
        JPanel alto = new JPanel(new GridLayout(1,3,1,1));
        alto.add(campoNome);
        alto.add(areaTesto);
        alto.add(crea);
        alto.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
        dialog.getContentPane().add(alto, BorderLayout.NORTH);
        //CREAZIONE PANNELLO MEDIO PER INFO + NUMERICA
        JPanel medio = new JPanel(new GridLayout(1,3));
        medio.add(campoModalita);
        medio.add(parametrica);
        medio.add(inserisci);
        medio.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
        dialog.getContentPane().add(medio, BorderLayout.CENTER);
        //CREAZIONE PANNELLO BASSO E PANNELLO BASSO PER PARAMETRO
        JPanel basso = new JPanel(new BorderLayout());
        JPanel bassoParametro = new JPanel(new GridLayout(1,4));
        JButton fineParametri = new JButton("Fine Parametri");
        fineParametri.setEnabled(false);
        bassoParametro.add(campoParametro);
        bassoParametro.add(campoValore);
        bassoParametro.add(aggiungiParametro);
        bassoParametro.add(fineParametri);
        basso.add(bassoParametro, BorderLayout.NORTH);
        basso.add(fatto, BorderLayout.CENTER);
        dialog.getContentPane().add(basso, BorderLayout.SOUTH);
        //ROBA FINALE DIALOG
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //LISTENER DEI BOTTONI
        crea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(interpretatore.aggiungiCategoriaAttuatore(campoNome.getText(), areaTesto.getText())){
                    //DISATTIVO ALTO
                    campoNome.setEnabled(false);
                    areaTesto.setEnabled(false);
                    crea.setEnabled(false);
                    //ATTIVO MEDIO
                    campoModalita.setEnabled(true);
                    parametrica.setEnabled(true);
                    inserisci.setEnabled(true);
                }else{
                    JOptionPane.showOptionDialog(null, "Creazione Categoria Attuatori fallita", "Fallimento Operazione", -1, 0, null, null, null);
                }
            }
        });
        inserisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(interpretatore.aggiungiModalitaCategoriaAttuatore(campoNome.getText(), campoModalita.getText())){
                    fineParametri.setEnabled(false); //ridondante?
                    if(parametrica.isSelected()){
                        //disattiva medio + fatto
                        fatto.setEnabled(false);
                        campoModalita.setEnabled(false);
                        parametrica.setEnabled(false);
                        inserisci.setEnabled(false);
                        //attiva basso parametro ma non fineParametri
                        campoParametro.setEnabled(true);
                        campoValore.setEnabled(true);
                        aggiungiParametro.setEnabled(true);
                    }else{
                        fatto.setEnabled(true);
                        campoModalita.setText("");
                    }
                }
            }
        });
        aggiungiParametro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double valore = 0.0;
                try {
                    valore = format.parse(campoValore.getText()).doubleValue();
                }catch (ParseException ex){
                    JOptionPane.showOptionDialog(null, "Ouch! Si è verificata un'eccezione", "Eccezione inserimento parametro", -1, 0, null, null,  null);
                    interpretatore.rimuoviCategoriaAttuatore(campoNome.getText());
                    dialog.dispose();
                    return;
                }
                if(interpretatore.aggiungiParametro(campoNome.getText(), campoModalita.getText(), campoParametro.getText(), valore)){
                    fineParametri.setEnabled(true);
                    campoParametro.setText("");
                    campoValore.setValue(null);
                }
            }
        });
        fineParametri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //disattivazione basso parametro + fine
                campoParametro.setEnabled(false);
                campoParametro.setText("");
                campoValore.setEnabled(false);
                campoValore.setValue(null);
                aggiungiParametro.setEnabled(false);
                fineParametri.setEnabled(false);
                //attivazione medio + fatto
                fatto.setEnabled(true); //se hai finito dopo la mod. param
                campoModalita.setEnabled(true);
                campoModalita.setText("");
                parametrica.setEnabled(true);
                inserisci.setEnabled(true); //gli ultmi tre se se ne vuole inserire un'altra di modalità
            }
        });
        fatto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null, "Categoria di attuatori inserita correttamente", "Successo operazione", -1, 1);
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }
}
