package view.listeners.file_listeners.nuovo;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import view.ModifySignal;
import view.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuovoCategoriaSensoriListener implements ActionListener, ModifySignal {

    private Interpretatore interpretatore;
    private Presenter presenter;
    private Rappresentatore rappresentatore;

    public NuovoCategoriaSensoriListener(Interpretatore interpretatore, Rappresentatore rappresentatore, Presenter presenter){
        this.interpretatore = interpretatore;
        this.presenter = presenter;
        this.rappresentatore = rappresentatore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //CREAZIONE CAMPO NOME CATEGORIA
        JTextField campoNome = new JTextField(20);
        campoNome.setEditable(true);
        campoNome.setToolTipText("Nome nuova categoria di sensori");
        //CREAZIONE AREA TESTO LIBERO
        JTextField areaTesto = new JTextField(20);
        areaTesto.setEditable(true);
        areaTesto.setToolTipText("Spazio per il testo libero");
        //CREAZIONE BOTTONE CREA CATEGORIA VUOTA
        JButton crea = new JButton("Crea");
        crea.setEnabled(true);
        //CREAZIONE CAMPO INFO RILEVABILE
        JTextField campoInfo = new JTextField(20);
        campoInfo.setEditable(false);
        campoInfo.setToolTipText("Nome informazione rilevabile");
        //CREAZIONE CHECK BOX NUMERICA
        JCheckBox numerica = new JCheckBox("E' numerica? ");
        numerica.setEnabled(false);
        //CREAZIONE BOTTONE INSERISCI INFO RILEVABILE + NUMERICA
        JButton inserisci = new JButton("Inserisci");
        inserisci.setEnabled(false);
        //CREAZIONE BOTTONE FATTO
        JButton fatto = new JButton("Fatto");
        fatto.setEnabled(false);
        //CREAZIONE DIALOG
        JDialog dialog = new JDialog((Dialog) null, "Inserimento Categoria Sensori", true);
        dialog.setLayout(new BorderLayout());
        //CREAZIONE PANNELLO ALTO PER CREAZIONE CATEGORIA VUOTA
        JPanel alto = new JPanel(new GridLayout(1,5,1,1));
        alto.add(new JLabel("Nome nuova categoria sensori:"));
        alto.add(campoNome);
        alto.add(new JLabel("Testo libero:"));
        alto.add(areaTesto);
        alto.add(crea);
        alto.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
        dialog.getContentPane().add(alto, BorderLayout.NORTH);
        //CREAZIONE PANNELLO MEDIO PER INFO + NUMERICA
        JPanel medio = new JPanel(new GridLayout(1,4));
        medio.add(new JLabel("Nome informazione rilevabile:"));
        medio.add(campoInfo);
        medio.add(numerica);
        medio.add(inserisci);
        medio.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f)));
        dialog.getContentPane().add(medio, BorderLayout.CENTER);
        //CREAZIONE PANNELLO BASSO PER BOTTONE FATTTO
        JPanel basso = new JPanel(new FlowLayout(FlowLayout.CENTER));
        basso.add(fatto);
        dialog.getContentPane().add(basso, BorderLayout.SOUTH);
        //ROBA FINALE DIALOG
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //LISTENER DEI BOTTONI
        crea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(interpretatore.aggiungiCategoriaSensore(campoNome.getText(), areaTesto.getText())){
                    //DISATTIVO ALTO
                    campoNome.setEditable(false);
                    areaTesto.setEditable(false);
                    crea.setEnabled(false);
                    //ATTIVO MEDIO
                    campoInfo.setEditable(true);
                    numerica.setEnabled(true);
                    inserisci.setEnabled(true);
                }
            }
        });
        inserisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(interpretatore.aggiungiInfoRilevabile(campoNome.getText(), campoInfo.getText(), numerica.isSelected())){
                    //INSERITA ALMENO UN'INFO RILEVABILE CON SUCCESSO -> ATTIVO FATTO PER USCIRE + PULISCO INFO E NUMERICA PER SUCCESSIVI INSERIMENTI
                    fatto.setEnabled(true);
                    campoInfo.setText("");
                    numerica.setSelected(false);
                }
            }
        });
        fatto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null, "Categoria di sensori inserita correttamente", "Successo operazione", -1, 1);
                segnalaModifica(rappresentatore.getDescrizioneCategoriaSensori(campoNome.getText()));
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}
