package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.entities.Kunde;
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

public class KreditantragDAO {

    @PersistenceContext
    private EntityManager em;

    public Kreditantrag suchen(Long kreditantragsNummer) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, kreditantragsNummer);
        return entity != null ? entity.toKreditantrag() : null;
    }

    public void addKreditantrag(Kreditantrag kreditantrag) {
        KreditantragEntity entity = new KreditantragEntity(kreditantrag);
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public void deleteKreditantrag(Long kreditantragsNummer) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, kreditantragsNummer);
        if (entity != null) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

    public void editKreditantrag(Kreditantrag kreditantrag) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, kreditantrag.getKreditantragsNummer());
        if (entity != null) {
            em.getTransaction().begin();
            entity.kreditsumme = kreditantrag.getKreditsumme();
            entity.laufzeit = kreditantrag.getLaufzeit();
            entity.zins = kreditantrag.getZins();
            entity.status = kreditantrag.getStatus();
            entity.antragssteller = new KundeEntity(kreditantrag.getAntragssteller());
            em.merge(entity);
            em.getTransaction().commit();
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
        BankkontoDAO bankkontoDAO = new BankkontoDAO();
        List<Bankkonto> konten = bankkontoDAO.alleLesen().stream()
                .filter(k -> k.getBesitzer() != null
                        && k.getBesitzer().getKundennummer().equals(kunde.getKundennummer()))
                .collect(Collectors.toList());

        Double summeEingehend = 0.0;
        Double summeAusgehend = 0.0;
        Set<String> negativeMonate = new HashSet<>();

        LocalDate now = LocalDate.now();
        int aktuellesJahr = now.getYear();
        int aktuellerMonat = now.getMonthValue();

        // Für alle Konten des Kunden
        for (Bankkonto konto : konten) {
            // Summiere Ein- und Ausgänge im aktuellen Monat
            Query query = em.createQuery(
                    "SELECT t FROM TransaktionEntity t WHERE t.Bankkonto.iban = :iban AND FUNCTION('MONTH', t.Transaktionsdatum) = :monat AND FUNCTION('YEAR', t.Transaktionsdatum) = :jahr");
            query.setParameter("iban", konto.getIban());
            query.setParameter("monat", aktuellerMonat);
            query.setParameter("jahr", aktuellesJahr);

            List<?> transaktionen = query.getResultList();
            for (Object obj : transaktionen) {
                de.hsnr.bank.dataaccess.TransaktionEntity t = (de.hsnr.bank.dataaccess.TransaktionEntity) obj;
                if ("EINGANG".equalsIgnoreCase(t.getTransaktionsart())) {
                    summeEingehend += t.getBetrag();
                } else if ("AUSGANG".equalsIgnoreCase(t.getTransaktionsart())) {
                    summeAusgehend += t.getBetrag();
                }
            }

            // Negative Monate der letzten 5 Jahre
            LocalDate start = now.minusYears(5).withDayOfMonth(1);
            LocalDate end = now.withDayOfMonth(1);

            Query saldoQuery = em.createQuery(
                    "SELECT FUNCTION('YEAR', t.Transaktionsdatum), FUNCTION('MONTH', t.Transaktionsdatum), SUM(t.Betrag) "
                            +
                            "FROM TransaktionEntity t WHERE t.Bankkonto.iban = :iban AND t.Transaktionsdatum >= :start AND t.Transaktionsdatum <= :end "
                            +
                            "GROUP BY FUNCTION('YEAR', t.Transaktionsdatum), FUNCTION('MONTH', t.Transaktionsdatum)");
            saldoQuery.setParameter("iban", konto.getIban());
            saldoQuery.setParameter("start", Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            saldoQuery.setParameter("end", Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            List<?> salden = saldoQuery.getResultList();
            for (Object o : salden) {
                Object[] arr = (Object[]) o;
                int jahr = ((Number) arr[0]).intValue();
                int monat = ((Number) arr[1]).intValue();
                double saldo = ((Number) arr[2]).doubleValue();
                if (saldo < 0) {
                    negativeMonate.add(jahr + "-" + monat);
                }
            }
        }

        return new KundeKontoAuswertung(summeEingehend, summeAusgehend, negativeMonate.size());
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