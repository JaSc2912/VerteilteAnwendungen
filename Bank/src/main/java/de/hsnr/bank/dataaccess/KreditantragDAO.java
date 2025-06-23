package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.entities.Transaktion;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class KreditantragDAO {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private BankkontoDAO bankkontoDAO;

    public Kreditantrag suchen(Long kreditantragsNummer) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, kreditantragsNummer);
        return entity != null ? entity.toKreditantrag() : null;
    }

    public void addKreditantrag(Kreditantrag kreditantrag) {
        KreditantragEntity entity = new KreditantragEntity(kreditantrag);
        if (kreditantrag.getAntragssteller() != null) {
            KundeEntity antragsstellerEntity = em.find(KundeEntity.class,
                    kreditantrag.getAntragssteller().getKundennummer());
            entity.antragssteller = antragsstellerEntity;
        }
        em.persist(entity);
    }

    public void deleteKreditantrag(Long kreditantragsNummer) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, kreditantragsNummer);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public void editKreditantrag(Kreditantrag kreditantrag) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, kreditantrag.getKreditantragsNummer());
        if (entity != null) {
            entity.kreditsumme = kreditantrag.getKreditsumme();
            entity.laufzeit = kreditantrag.getLaufzeit();
            entity.zins = kreditantrag.getZins();
            entity.status = kreditantrag.getStatus();
            if (kreditantrag.getAntragssteller() != null) {
                KundeEntity antragsstellerEntity = em.find(KundeEntity.class,
                        kreditantrag.getAntragssteller().getKundennummer());
                entity.antragssteller = antragsstellerEntity;
            } else {
                entity.antragssteller = null;
            }
            em.merge(entity);
        }
    }

    public List<Kreditantrag> alleLesen() {
        TypedQuery<KreditantragEntity> query = em.createQuery("SELECT k FROM KreditantragEntity k",
                KreditantragEntity.class);
        return query.getResultList().stream()
                .map(KreditantragEntity::toKreditantrag)
                .collect(Collectors.toList());
    }

    // Suche nach Kreditanträgen eines Kunden (antragssteller)
    public List<Kreditantrag> kreditantragSuchenNachKunde(Kunde kunde) {
        TypedQuery<KreditantragEntity> query = em.createQuery(
                "SELECT k FROM KreditantragEntity k WHERE k.antragssteller.kundeId = :kundeId",
                KreditantragEntity.class);
        query.setParameter("kundeId", kunde.getKundennummer());
        return query.getResultList().stream()
                .map(KreditantragEntity::toKreditantrag)
                .collect(Collectors.toList());
    }

    // Suche nach Kreditanträgen über IBAN (Join auf Bankkonto)
    public List<Kreditantrag> kreditantragSuchenNachIban(String iban) {
        TypedQuery<KreditantragEntity> query = em.createQuery(
                "SELECT k FROM KreditantragEntity k JOIN k.antragssteller.bankenkonten b WHERE b.iban = :iban",
                KreditantragEntity.class);
        query.setParameter("iban", iban);
        return query.getResultList().stream()
                .map(KreditantragEntity::toKreditantrag)
                .collect(Collectors.toList());
    }

    // Auswertung für KundeAuswerten: Summe Ein-/Ausgänge im aktuellen Monat und
    // negative Monate in den letzten 5 Jahren
    public KundeKontoAuswertung auswertungFuerKunde(Kunde kunde) {
        List<Bankkonto> konten = bankkontoDAO.alleLesen().stream()
                .filter(k -> k.getBesitzer() != null
                        && k.getBesitzer().getKundennummer().equals(kunde.getKundennummer()))
                .collect(Collectors.toList());

        if (konten.isEmpty()) {
            return new KundeKontoAuswertung(0.0, 0.0, 0);
        }

        List<String> ibans = konten.stream().map(Bankkonto::getIban).collect(Collectors.toList());

        LocalDate now = LocalDate.now();
        Date anfangAktuellerMonat = Date.from(now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        TypedQuery<TransaktionEntity> txQuery = em.createQuery(
                "SELECT t FROM TransaktionEntity t WHERE t.bankkonto.iban IN :ibans AND t.transaktionsdatum >= :startDatum",
                TransaktionEntity.class);
        txQuery.setParameter("ibans", ibans);
        txQuery.setParameter("startDatum", anfangAktuellerMonat);

        double summeEingehend = 0.0;
        double summeAusgehend = 0.0;

        for (TransaktionEntity t : txQuery.getResultList()) {
            if ("EINGANG".equalsIgnoreCase(t.transaktionsart)) {
                summeEingehend += t.betrag;
            } else if ("AUSGANG".equalsIgnoreCase(t.transaktionsart)) {
                summeAusgehend += t.betrag;
            }
        }

        // Die Logik für negative Monate ist sehr komplex und datenbankabhängig.
        // Eine vereinfachte Annahme oder eine dedizierte Prozedur wäre hier besser.
        // Fürs Erste wird dies als 0 zurückgegeben.
        int negativeMonate = 0;

        return new KundeKontoAuswertung(summeEingehend, summeAusgehend, negativeMonate);
    }

    // Suche nach Kreditanträgen anhand eines Suchbegriffs in verschiedenen Feldern
    public List<Kreditantrag> kreditantragSuchen(String suchbegriff) {
        String suchbegriffLower = suchbegriff.toLowerCase();
        return alleLesen().stream()
                .filter(k -> (k.getKreditantragsNummer() != null
                        && k.getKreditantragsNummer().toString().contains(suchbegriff)) ||
                        (k.getKreditsumme() != null && k.getKreditsumme().toString().contains(suchbegriff)) ||
                        (k.getLaufzeit() != null && k.getLaufzeit().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getZins() != null && k.getZins().toString().contains(suchbegriff)) ||
                        (k.getStatus() != null && k.getStatus().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getAntragssteller() != null && ((k.getAntragssteller().getName() != null
                                && k.getAntragssteller().getName().toLowerCase().contains(suchbegriffLower)) ||
                                (k.getAntragssteller().getKundennummer() != null
                                        && k.getAntragssteller().getKundennummer().contains(suchbegriff)))))
                .collect(Collectors.toList());
    }

    // Hilfsklasse für Auswertungsergebnis
    public static class KundeKontoAuswertung {
        public final Double summeEingehend;
        public final Double summeAusgehend;
        public final int negativeMonate;

        public KundeKontoAuswertung(Double summeEingehend, Double summeAusgehend, int negativeMonate) {
            this.summeEingehend = summeEingehend;
            this.summeAusgehend = summeAusgehend;
            this.negativeMonate = negativeMonate;
        }
    }
}