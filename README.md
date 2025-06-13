# VerteilteAnwendungen
Repository zur Abgabe Thema 1 Bank
https://app.diagrams.net/#HJaSc2912%2FVerteilteAnwendungen%2Fmain%2FDatenmodell.drawio#%7B%22pageId%22%3A%22R3NhpwloNoWIdbwCTrZ_%22%7D


📦 1) Überblick
Diese drei Klassen bilden zusammen ein klassisches JPA (Java Persistence API)-Muster mit einer Entitätsklasse, einer Domänenklasse und einer DAO-Schicht (Data Access Object).

Aufgabenverteilung:

Klasse	Aufgabe
Admin	Domänen- oder Transfer-Objekt (Business-Logik)
AdminEntity	Persistente Entität (DB-Tabelle)
AdminDAO	Zugriffsschicht zur DB

✅ 2) Die Klassen im Detail
🔹 a) Admin (Domänenobjekt)
Paket: de.hsnr.lexikonserver.core.entities

Aufgabe:
Repräsentiert einen Admin im Anwendungskern (Business-Logik).
Dieses Objekt wird nicht direkt gespeichert, sondern nur in der Anwendung verwendet oder über REST übertragen.

Attribute:

kennung (Anmeldename)

secret (z.B. Passwort-Hash)

Nutzung:
Wird vom AdminEntity erzeugt (toAdmin()) oder in AdminEntity gewandelt.

🔹 b) AdminEntity (Persistente Entität)
Paket: de.hsnr.lexikonserver.dataaccess

Aufgabe:
Repräsentiert die DB-Tabelle Admin.
Wird von JPA verwaltet — d.h. diese Klasse wird vom EntityManager in die Datenbank geschrieben oder daraus geladen.

Annotationen:

@Entity markiert die Klasse als persistente Entität.

@Id markiert den Primärschlüssel (kennung).

@NotNull stellt sicher, dass secret nicht null ist.

Besonderheit:
Hat Methoden zum Mapping zwischen Domänenobjekt Admin und DB-Entität:

Konstruktor AdminEntity(Admin admin)

Methode toAdmin()

🔹 c) AdminDAO (Data Access Object)
Paket: de.hsnr.lexikonserver.dataaccess

Aufgabe:
Kapselt den Zugriff auf die Datenbank.
Stellt Methoden bereit, um Admins zu suchen, speichern usw.

Annotation:

@Singleton = EJB Singleton (es gibt nur eine Instanz, die DB-Zugriffe zentral verwaltet).

@PersistenceContext injiziert den EntityManager.

Beispiel:
Die Methode suchen(String kennung) lädt eine AdminEntity per Primärschlüssel und wandelt sie in ein Admin-Objekt um:

java
Kopieren
Bearbeiten
AdminEntity adminEntity = em.find(AdminEntity.class, kennung);
return adminEntity != null ? adminEntity.toAdmin() : null;
🔄 3) Zusammenspiel
1️⃣ Speichern:
Wenn du einen Admin speichern willst:

Du erstellst ein Admin-Objekt in der Business-Logik.

Du übergibst es dem DAO.

Das DAO wandelt es in eine AdminEntity um und speichert es über EntityManager.

2️⃣ Laden:
Wenn du einen Admin laden willst:

Das DAO sucht mit EntityManager in der DB (AdminEntity).

Danach wird AdminEntity in Admin umgewandelt und zurückgegeben.

Die Anwendung arbeitet nur mit Admin.

✅ 4) Abhängigkeiten
Klasse	Abhängigkeit zu
Admin	(keine Abhängigkeit)
AdminEntity	kennt Admin (für Konstruktor + Mapping)
AdminDAO	kennt AdminEntity und Admin

🗝️ Zusammengefasst
Trennung von Logik & Persistenz:
Admin ist reiner Anwendungscode, AdminEntity kümmert sich um Datenhaltung.

Flexibles Mapping:
Änderungen an der DB-Struktur erfordern nur Anpassungen in AdminEntity, nicht im Rest der Anwendung.

DAO-Schicht:
Entkoppelt Datenbanklogik von der Geschäftslogik — die App muss keine SQL-Queries selbst schreiben.
