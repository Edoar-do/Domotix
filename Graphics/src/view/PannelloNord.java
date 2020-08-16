package view;

import domotix.controller.*;
import view.listeners.*;
import view.listeners.file_listeners.AperturaUnitaListener;
import view.listeners.file_listeners.importa.ImportaListener;
import view.listeners.file_listeners.nuovo.NuovoCategoriaAttuatorilistener;
import view.listeners.file_listeners.nuovo.NuovoCategoriaSensoriListener;
import view.listeners.file_listeners.nuovo.NuovoUnitaListener;
import view.listeners.view_listeners.DettagliUnitaListener;
import view.listeners.view_listeners.ViewCategorieListener;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PannelloNord extends JPanel {

    public static final String UNITA = "Unità Immobiliari";
    public static final String CAT_SENS = "Categorie di Sensori";
    public static final String CAT_ATT = "Categorie di Attuatori";

    private String unitaCorrente;
    private PannelloCentro pannelloCentro;

    private JMenuBar barraMenu;
    private JMenu file, edit, view, help, //menu
            file_nuovo, file_importa, view_dettagliUnita; //sotto menu
    private JMenuItem file_apriUnita, file_categorieSensori, file_categorieAttuatori, file_unita,
            view_descrizioneUnita, view_stanze, view_artefatti, view_categorieSensori, view_categorieAttuatori,
            importa_unita, importa_categorieSensori, importa_categorieAttuatori, help_errori, help_glossario, help_importazione;
    private JRadioButton radioManutentore, radioFruitore;
    private ButtonGroup radiogroup;
    private JLabel tagUnita;

    private ActionListener nuovoCategoriaAttuatoriListener, nuovoCategoriaSensoriListener, nuovoUnitaListener, importaListener, dettagliUnitaListener,
            viewCategorieListener, radioButtonListener, aperturaUnitaListener, helpListener;

    public PannelloNord(PannelloCentro pannelloCentro, Interpretatore inter, Rappresentatore rapp, Importatore imp, Verificatore ver){
        super();
        unitaCorrente = "";
        this.pannelloCentro = pannelloCentro;
        tagUnita = new JLabel("   Unità corrente: ");
        barraMenu = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit"); edit.setEnabled(false); //finché non viene selezioanta un'unità
        view = new JMenu("View");
        help = new JMenu("Help");
        radiogroup = new ButtonGroup();
        radioManutentore = new JRadioButton("Manutentore", false);
        radioFruitore = new JRadioButton("Fruitore", false);
        popolaFile();
        popolaView();
        popolaHelp();
        componiBarra();
        attaccoListeners(inter, rapp, imp, ver); //qui vanno passati i controllers come params e poi verranno passati ai listener vari in base alle loro esigenze
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        this.add(barraMenu);
    }

    private void popolaHelp(){
        help_errori = new JMenuItem("Errori");
        help_glossario = new JMenuItem("Glossario");
        help_importazione = new JMenuItem("Importazione");
        help.add(help_errori);
        help.add(help_glossario);
        help.add(help_importazione);
    }

    private void popolaFile(){
        componiFile_Nuovo();
        file.add(file_nuovo);
        componiFile_Importa();
        file.add(file_importa);
        file_apriUnita = new JMenuItem("Apri Unità Immobiliare");
        file.add(file_apriUnita);
    }

    private void componiFile_Nuovo(){
        file_nuovo = new JMenu("Nuovo...");
        file_unita = new JMenuItem(UNITA);
        file_categorieSensori = new JMenuItem(CAT_SENS);
        file_categorieAttuatori = new JMenuItem(CAT_ATT);
        file_nuovo.add(file_unita);
        file_nuovo.add(file_categorieSensori);
        file_nuovo.add(file_categorieAttuatori);
    }

    private void componiFile_Importa(){
        file_importa = new JMenu("Importa...");
        importa_unita = new JMenuItem(UNITA);
        importa_categorieSensori = new JMenuItem(CAT_SENS);
        importa_categorieAttuatori = new JMenuItem(CAT_ATT);
        file_importa.add(importa_unita);
        file_importa.add(importa_categorieSensori);
        file_importa.add(importa_categorieAttuatori);
    }

    private void popolaView(){
        componi_viewDettagliUnita();
        view.add(view_dettagliUnita);
        view_categorieSensori = new JMenuItem(CAT_SENS);
        view_categorieAttuatori = new JMenuItem(CAT_ATT);
        view.add(view_categorieSensori);
        view.add(view_categorieAttuatori);
    }

    private void componi_viewDettagliUnita(){
        view_dettagliUnita = new JMenu("Dettagli Unità"); view_dettagliUnita.setEnabled(false); //finché non c'è un'unità selezionata
        view_descrizioneUnita = new JMenuItem("Descrizione Completa Unità");
        view_stanze = new JMenuItem("Stanze");
        view_artefatti = new JMenuItem("Artefatti");
        view_dettagliUnita.add(view_descrizioneUnita);
        view_dettagliUnita.add(view_stanze);
        view_dettagliUnita.add(view_artefatti);
    }

    private void componiBarra(){
        barraMenu.add(file);
        barraMenu.add(edit);
        barraMenu.add(view);
        barraMenu.add(help);
        radiogroup.add(radioManutentore);
        radiogroup.add(radioFruitore);
        barraMenu.add(radioManutentore);
        barraMenu.add(radioFruitore);
        barraMenu.add(tagUnita);
    }

    private void attaccoListeners(Interpretatore inter, Rappresentatore rapp, Importatore imp, Verificatore ver){
        //ATTACCO LISTENER AGLI ITEM DI NUOVO
        nuovoUnitaListener = new NuovoUnitaListener(this, inter,rapp ,pannelloCentro);
        file_unita.addActionListener(nuovoUnitaListener);
        nuovoCategoriaSensoriListener = new NuovoCategoriaSensoriListener(inter,rapp, pannelloCentro);
        file_categorieSensori.addActionListener(nuovoCategoriaSensoriListener);
        nuovoCategoriaAttuatoriListener = new NuovoCategoriaAttuatorilistener(inter,rapp, pannelloCentro);
        file_categorieAttuatori.addActionListener(nuovoCategoriaAttuatoriListener);
        //ATTACCO LISTENER AGLI ITEM DI IMPORTA
        importaListener = new ImportaListener(imp);
        for(int i=0; i < file_importa.getItemCount(); i++){ file_importa.getItem(i).addActionListener(importaListener); }
        //ATTACCO LISTENER ALL'ITEM APRI UNITA
        aperturaUnitaListener = new AperturaUnitaListener(this, rapp, pannelloCentro);
        file_apriUnita.addActionListener(aperturaUnitaListener);
        //ATTACCO LISTENER DETTAGLI UNITA (descrizione completa, stanze, artefatti)
        dettagliUnitaListener = new DettagliUnitaListener(this.pannelloCentro, this, rapp );
        for(int i=0; i < view_dettagliUnita.getItemCount(); i++){ view_dettagliUnita.getItem(i).addActionListener(dettagliUnitaListener); }
        //ATTACCO LISTENER VIEW-CATEGORIE SENSORI E ATTUATORI
        viewCategorieListener = new ViewCategorieListener(this.pannelloCentro, rapp);
        view_categorieSensori.addActionListener(viewCategorieListener);
        view_categorieAttuatori.addActionListener(viewCategorieListener);
        //ATTACCO LISTENER RADIO BUTTON
        radioButtonListener = new RadioButtonListener(rapp, ver, inter, this, pannelloCentro);
        radioManutentore.addActionListener(radioButtonListener);
        radioFruitore.addActionListener(radioButtonListener);
        //ATTACCO LISTENER HELP
        helpListener = new HelpListener();
        help_errori.addActionListener(helpListener);
        help_glossario.addActionListener(helpListener);
        help_importazione.addActionListener(helpListener);
    }

    public JMenu getEdit(){return edit; }

    public String getUnitaCorrente(){ return unitaCorrente; }

    public JMenu getFile_importa(){
        return file_importa;
    }

    public JMenu getFile_nuovo() {
        return file_nuovo;
    }

    /**
     * setta la nuova unità corrente e al tempo stesso abilita i menu edit e dettagli unità
     * @param unitaCorrente
     */
    public void setUnitaCorrente(String unitaCorrente){
        this.unitaCorrente = unitaCorrente;
        this.tagUnita.setText("   Unità corrente: " + unitaCorrente);
        view_dettagliUnita.setEnabled(true);
        edit.setEnabled(true);
    }

    /**
     * pulisce l'unità corrente e al tempo stesso disabilita i menu edit e dettagli unità
     * Dovrà essere selezionata con Open o New una nuova unità immobiliare corrente
     */
    public void pulisciUnitaCorrente(){
        this.unitaCorrente = "";
        tagUnita.setText("   Unità corrente: ");
        view_dettagliUnita.setEnabled(false);
        edit.setEnabled(false);
    }

}
