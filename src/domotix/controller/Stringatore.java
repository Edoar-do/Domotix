package domotix.controller;

import domotix.controller.io.visitor.AbstractVisitor;
import domotix.controller.io.visitor.Visitable;
import domotix.controller.util.StringUtil;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Sistema;
import domotix.model.bean.system.Stanza;
import domotix.model.util.ElencoDispositivi;
import domotix.model.util.SommarioDispositivi;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**@author andrea */

public class Stringatore extends AbstractVisitor {
    private static final DateTimeFormatter TIME_FORMATTER =  DateTimeFormatter.ofPattern("HH.mm");
    private static final String NO_ARTEFATTI = "Non e' presente alcun artefatto";
    private static final String NO_REGOLE = "Non e' presente alcuna regola";
    private static final String NO_SENSORI = "Non e' presente alcun sensore";
    private static final String NO_ATTUATORI = "Non e' presente alcun attuatore";
    public static final String NOME_INFO_RILEVABILE_OROLOGIO = SensoreOrologio.NOME_INFO_RILEVABILE_OROLOGIO;

    private static String getValoreStampabileTempo(LocalTime time) {
        return TIME_FORMATTER.format(time);
    }

    private String getStringaDispositivi(Dispositivo[] dispositivi) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < dispositivi.length; i++) {
            buffer.append(rappresenta(dispositivi[i]) + (i < dispositivi.length - 1 ? "\n" : ""));
        }
        return buffer.toString();
    }

    private String getStringaSistema(Sistema s) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(s.getNome() + ":" + "\n");
        buffer.append("\tSENSORI:");
        if (s.getSensori().length > 0) {
            String stringaSensori = getStringaDispositivi(s.getSensori());
            buffer.append(StringUtil.indent("\n" + stringaSensori, 2) + "\n");
        } else {
            buffer.append(StringUtil.indent("\n" + NO_SENSORI, 2) + "\n");
        }
        buffer.append("\tATTUATORI:");
        if (s.getAttuatori().length > 0) {
            String stringaAttuatori = "\n" + getStringaDispositivi(s.getAttuatori());
            buffer.append(StringUtil.indent(stringaAttuatori, 2));
        } else {
            buffer.append(StringUtil.indent("\n" + NO_ATTUATORI, 2));
        }
        return buffer.toString();
    }

    private String rappresentaLista(List<Visitable> lv) {
        return lv.stream().map(v -> this.rappresenta(v)).collect(Collectors.joining(","));
    }

    public String rappresenta(Visitable vis) {
        return (String) vis.fattiVisitare(this);
    }

    @Override
    public Object visitaArtefatto(Visitable visitabileArtefatto) {
        Artefatto v = (Artefatto) visitabileArtefatto;
        return getStringaSistema(v);
    }

    @Override
    public Object visitaStanza(Visitable visitabileStanza) {
        Stanza v = (Stanza) visitabileStanza;
        StringBuffer buffer = new StringBuffer();
        buffer.append(getStringaSistema(v) + "\n");
        buffer.append("\tARTEFATTI:");
        if (v.getArtefatti().length > 0) {
            for (Artefatto artefatto : v.getArtefatti()) {
                String stringaArtefatto = "\n" + artefatto.toString();
                buffer.append(StringUtil.indent(stringaArtefatto, 2));
            }
        } else {
            buffer.append(StringUtil.indent("\n" + NO_ARTEFATTI, 2));
        }
        return buffer.toString();
    }

    @Override
    public Object visitaParametro(Visitable visitabileParametro) {
        Parametro v = (Parametro) visitabileParametro;
        return v.getNome() + ": " + String.format("%.2f", v.getValore());
    }

    @Override
    public Object visitaCategoriaAttuatore(Visitable visitabileCategoriaAttuatore) {
        CategoriaAttuatore v = (CategoriaAttuatore) visitabileCategoriaAttuatore;
        StringBuffer buffer = new StringBuffer();
        buffer.append(v.getNome() + ":\n");
        buffer.append("\tTESTO LIBERO:\n");
        buffer.append(StringUtil.indent(v.getTestoLibero() + "\n", 2));
        buffer.append("\tELENCO MODALITA':");
        v.getElencoModalita().forEach((e) -> {
            buffer.append(StringUtil.indent("\n" + rappresenta(e), 2));
        });
        return buffer.toString();
    }

    @Override
    public Object visitaAttuatore(Visitable visitabileAttuatore) {
        Attuatore v = (Attuatore) visitabileAttuatore;
        return String.format("%s [%s]: %s", v.getNome(), (v.getStato() ? "ON" : "OFF"), v.getModoOp());
    }

    @Override
    public Object visitaModalita(Visitable visitabileModalita) {
        Modalita v = (Modalita) visitabileModalita;
        String str = "" + v.getNome();
        if (!v.getParametri().isEmpty()) {
            str += "\n" + StringUtil.indent("PARAMETRI:", 1);
            for (Parametro p : v.getParametri()) {
                str += "\n" + StringUtil.indent(rappresenta(p), 2);
            }
        }
        return str;
    }

    @Override
    public Object visitaCategoriaSensore(Visitable visitabileCategoriaSensore) {
        CategoriaSensore v = (CategoriaSensore) visitabileCategoriaSensore;
        StringBuffer buffer = new StringBuffer();
        buffer.append(v.getNome() + ":\n");
        buffer.append("\tTESTO LIBERO:\n");
        buffer.append(StringUtil.indent(v.getTestoLibero() + "\n", 2));
        buffer.append("\tINFORMAZIONI RILEVABILI:");
        for(InfoRilevabile i : v.getInformazioniRilevabili())
            buffer.append(StringUtil.indent("\n" + rappresenta(i), 2));
        return buffer.toString();
    }

    @Override
    public Object visitaSensore(Visitable visitabileSensore) {
        Sensore v = (Sensore) visitabileSensore;
        StringBuffer buffer = new StringBuffer();
        buffer.append(v.getNome());
        buffer.append("[" + (v.getStato() ? "ON" : "OFF") + "]");
        v.getValori().forEach((key, val) -> buffer.append("\n" + StringUtil.indent(key + " = " + val, 1)));
        return buffer.toString();
    }

    @Override
    public Object visitaSensoreOrologio(Visitable visitabileSensoreOrologio) {
        SensoreOrologio v = (SensoreOrologio) visitabileSensoreOrologio;
        StringBuffer buffer = new StringBuffer();
        buffer.append(v.getNome());
        buffer.append("\n" + StringUtil.indent(NOME_INFO_RILEVABILE_OROLOGIO + " = " + getValoreStampabileTempo(v.getValoreTempo())));
        return buffer.toString();
    }

    @Override
    public Object visitaInfoRilevabile(Visitable visitabileInfoRilevabile) {
        InfoRilevabile v = (InfoRilevabile) visitabileInfoRilevabile;
        return v.getNome() + ":" + (v.isNumerica() ? " " : " non") + " numerica";
    }

    @Override
    public Object visitaUnitaImmobiliare(Visitable visitabileUnitaImmobiliare) {
        UnitaImmobiliare v = (UnitaImmobiliare) visitabileUnitaImmobiliare;
        StringBuffer buffer = new StringBuffer();
        buffer.append(v.getNome() + ":\n");
        buffer.append("\tSTANZE:");
        for (Stanza stanza : v.getStanze()) {
            String stringaStanza = "\n" + rappresenta(stanza);
            buffer.append(StringUtil.indent(stringaStanza, 2));
        }
        buffer.append("\n\tREGOLE:");
        if(v.getRegole().length > 0) {
            for (Regola regola : v.getRegole()) {
                String stringaRegola = "\n" + rappresenta(regola);
                buffer.append(StringUtil.indent(stringaRegola, 2));
            }
        }else
            buffer.append(StringUtil.indent("\n" + NO_REGOLE, 2));
        return buffer.toString();
    }

    @Override
    public Object visitaAntecedente(Visitable visitabileAntecedente) {
        Antecedente v = (Antecedente) visitabileAntecedente;
        String lhs = rappresenta(v.getCondizione());
        String opstr = v.getOperatoreLogico() == null ? "" : " " + v.getOperatoreLogico() + " ";
        String rhs = v.getProssimoAntecedente() == null ? "" : rappresenta(v.getProssimoAntecedente());
        return lhs + opstr + rhs;
    }

    @Override
    public Object visitaCondizione(Visitable visitabileCondizione) {
        Condizione v = (Condizione) visitabileCondizione;
        return rappresenta(v.getSinistra()) + " " + v.getOperatore() + " " + rappresenta(v.getDestra());
    }

    @Override
    public Object visitaInfoCostante(Visitable visitabileInfoCostante) {
        InfoCostante v = (InfoCostante) visitabileInfoCostante;
        return v.getInfo().toString();
    }

    @Override
    public Object visitaInfoTemporale(Visitable visitabileInfoTemporale) {
        InfoTemporale v = (InfoTemporale) visitabileInfoTemporale;
        return getValoreStampabileTempo(v.getTempo());
    }

    @Override
    public Object visitaRegola(Visitable visitabileRegola) {
        Regola v = (Regola) visitabileRegola;
        String antstr = v.getAntecedente() == null ? "true" : rappresenta(v.getAntecedente());
        String consstr = rappresenta(v.getConseguente());
        String statostr = "; Stato -> [" + v.getStato().toString() + "]";
        return "if " + antstr + " then " + consstr + statostr;
    }

    @Override
    public Object visitaConseguente(Visitable visitabileConseguente) {
        Conseguente v = (Conseguente) visitabileConseguente;
        return v.getAzioni().stream().map(a -> rappresenta(a)).collect(Collectors.joining(", "));
    }
    @Override
    public Object visitaInfoVariabile(Visitable visitabileInfoVariabile) {
        InfoVariabile v = (InfoVariabile) visitabileInfoVariabile;
        return v.getSensore().getNome() + "." + v.getNomeInfo();
    }
    @Override
    public Object visitaAzione(Visitable visitabileAzione) {
        Azione v = (Azione) visitabileAzione;
        String parstr = v.getParametri().size() > 0 ? v.getParametri().stream().map(e -> this.rappresenta(e)).collect(Collectors.joining(",")) : "";
        String timestr = v.getStart() == null ? "" : ", start := " + v.getStart().toString(); // todo
        return v.getAttuatore().getNome() + " := " + v.getModalita().getNome() + parstr + timestr;
    }

    @Override
    public Object visitaElencoDispositivi(Visitable visitabileElencoDispositivi) {
        ElencoDispositivi v = (ElencoDispositivi) visitabileElencoDispositivi;
        StringBuffer buffer = new StringBuffer();
        for (Dispositivo dispositivo : v.getDispositivi()) {
            buffer.append(rappresenta(dispositivo) + "\n");
        }
        return buffer.toString();
    }

    @Override
    public Object visitaSommarioDispositivi(Visitable visitabileSommarioDispositivi) {
        SommarioDispositivi v = (SommarioDispositivi) visitabileSommarioDispositivi;
        StringBuffer buffer = new StringBuffer();
        Stream.of(v.getDispositivi()).forEach(val -> {
            buffer.append(rappresenta(val) + "\n");
        });
        return StringUtil.removeLast(buffer.toString());
    }

    @Override
    public Object visitaElencoUnitaImmobiliari(Visitable visitabileElencoUnitaImmobiliari) {
        ElencoUnitaImmobiliari v = (ElencoUnitaImmobiliari) visitabileElencoUnitaImmobiliari;
        StringBuffer buffer = new StringBuffer();
        v.getUnita().forEach(val -> {
            buffer.append(rappresenta(val) + "\n");
        });
        return StringUtil.removeLast(buffer.toString());
    }
}
