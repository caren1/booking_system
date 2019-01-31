# booking_system

Nasz projekt jest to webservice dla firmy swiadczacej usługi pomocy w prowadzeniu działalności (uslugi prawne, ksiegowe), 
wynajmu wirtualnego biura. Webservice bedzie umozliwiał rezerwacje oraz zarzadanie biurem. Rozwiązanie dla osob prywatnych oraz firm.

Technologie używane:
-Java
-JS
-HTML
-SQL
-Spring Boot
-CSS
-Rest
-Angular

Kto bedzie miał dostęp?
Osobami odpowiedzialnymi za projekt beda: Wojciech C oraz Piotr O
Na strone bedzie mógł wejść każdy, natomiast wszelkie funkcjonalnosci beda dostępne tylko dla zarejestrowanych firm/osob.
Bedzie istniało konto Admin z możliwosciami opisanymi poniżej.

Etapy realizacji projektu:
1. Rozpisanie etapów, podzielenie zadań etapami, sporzadzenie dokumentacji projektu.
2. -Stworzenie projektu Springowego w IntelJ wraz ze wszystkimi potrzebnymi zaleznosciami
   -Realizacja podstaw projektu: konfiguracja Spring, stworzenie modeli, service'ow, controller'ow, repository,
    stworzenie bazy danych SQL oraz relacji miedzy modelami. 
3. Implementacja wszystkich metod CRUD dla kazdego z modeli (w odpowiadajacym mu Service), dodanie Mappingow oraz metod w odpowiednich Controllerach.
   Implementacja logowania wraz z "Security Manager".
4.Tworzenie prymitywnych widokow oraz widoku tabeli spotkan (Angular) wraz z implementacja wszelkich metod/funkcjonalnosci. (juz sie boje ;/)
5.Tworzenie docelowych widoków na stronie wraz z CSS i JS (front end)
6.?????


Wszystko bedzie testowane przez Postmana oraz MySQLWorkBench.
Bedzie realizował takie funkcjonalnosci jak:

Użytkownik/Firma:
- logowanie/rejestracja uzytkownika.
- mozliwosc rezerwacji spotkania/usługi poprzez serwis.
- mozliwosc przeglądania oraz edytowanie umowionych /spotkan w biurze.
- edytowanie danych swojego konta (zmiana hasła, maila, telefon etc.)
- wgrywanie plików poprzez panel uzytkownika (bezpieczne rozwiazanie przechowywania plikow w chmurze) + mailowe powiadomienie do admina
  o dodaniu pliku na chmure przez uzytkownika. Nazwy plików generowałyby się automatycznie podczas uploadu.
-*ewentualny wariant z abonamentem rocznym dla firm (wtedy niektore badz wszystkie usługi beda w ramach jednej opłaty rocznej)
- osoba prywatna po dokonaniu wstepnej rezerwacji spotkania/usługi musi w ciagu godziny dokonać płatności na wskazane konto (ID usługi w tytule przelewu).
  Jeżeli osoba nie dokona płatności w ciagu godziny, wizyta przepada (nie zostaje zatwierdzona przez osobę z recepcji)/ ewentualna implementacja PayU na stronie.
- kazdy uzytkownik ma przypisanego konkretnego opiekuna z danej dziedziny (czyli prawnika, ksiegowa etc).

Admin:
- wyswietlenie wszystkich spotkan ktore odbeda sie danego w dnia (sposob wyswietlania - widok dzien po dniu).
- mozliwosc podgladu danego spotkania (wszystkie info: dane firmy, przedział czasowy, rodzaj uslugi, wykonawca, cena, ilosc osob, status płatnosci).
- mozliwosc rezerwacji, edycji spotkan oraz danych uzytkowników.
- mozliwosc edytowania i dodawania uslug (cena, rodzaj uslugi osoba wykonująca usluge)*. 
*ewentualnie edycja kodu przez twórce bez implementacji tej funkcjonalnosci na stronie.
- dostęp do chmury z plikami uzytkowników


Schemat przechodzenia:
- Navigationbar widoczny na wszystkich zakladkach (zablokowany u gory strony podczas scrollowania)
- Footer (kontakt + mapka + dane adresowe) na dole kazdej zakladki

Wygląd: 
- Home (landing page): Opis działalności, modelu biznesowego oraz usług jakie świadczy firma. (slider ze zdjeciami).
  Ponizej avatary pracownikow wraz ze zdjeciem i opisem pod avatarem (moze bez przeladowywania strony*).

- Usługi: rozpiska uslug (cena, nazwa, czas trwania) opis konkretnych sal.

- Zarezerwuj - jezeli ktos wchodzi na strone niezalogowany ma mozliwosc przeglądania dostepnych wizyt/wybrania terminu 
 (w zakladce "rezerwuj wizyte") ale w finalnym kroku zatwierdzania wizyty przekierowuje go do logowania.
  W zaleznosci od typu konta weryfikacja naleznosci (abomnament czy jednorazowa usługa).
 
- Logowanie/rejestracja (User - widok uzytkownika): 

		- po wejsciu w zakladke pokazuje sie panel logowania (pod nim link do rejestracji oraz odzyskania konta / nie pamietam hasla)
		- po udanym zalogowaniu na pasku navbar bedzie dodawała sie nowa pozycja (avatar/nazwa uzytkownika) 
		- po kliknieciu w Avatar pojawia sie drop-down menu z funkacjami Rezerwacje, Płatnosci, Pliki, Konto, Wyloguj 
			
			Rezerwacje:
			- Wyswietlenie twoich przyszłych rezerwacji (Avatar i imie osoby swiadczacej usluge, opis usługi wraz z cena, czasem trwania i godzina rozpoczecia, sala itp) 
			 obok przyciski do anulowania oraz ew. edycji wizyty.
			 
			Konto:
			- Wypisane wszystkie pola uzytkownika wraz z przyciskami obok "edytuj", ktore przenosza nas na strone danego formularza.
			
			Wyloguj:
			- Wylogowuje oraz przekierowywuje na Home (index).
		
- Galeria (albumy + zdjecia + miniatury + animacje).
 
- Kontakt (wraz z mapka jako footer). 

  
- Logowanie/rejestracja (Admin - widok admina):

		- wszystkie funkcjonalnosci, ktore byly zawarte w widoku uzytkownika.
		
			Rezerwacje:
			- po wejsciu w rezerwacje z drop-down menu wyswietla sie kalendarz/tabela na dany dzien
			
			- edycja wizyty: po wejsciu w rezerwacje, wyswietala sie chmurka/box ze szczegolami oraz przycisk edytuj
				(przekierowanie na osobny formularz edycji / edycja w chmurce). Po zakończeniu edycji i zapisaniu zmian,
					przekierowuje spowrotem na widok 'Rezerwacje'.
			
			- dodawanie wizyty: wybierasz sale dla ktorej chcesz sprawdzic terminarz rezerwacji wklikujesz sie w wolny termin i przekierowuje Cie
			  do formularza (+ dynamiczne wyszukiwanie klienta z bazy danych po: NIP lub email - (autopodpowiadanie klientow z bazy danych)*
			  (?) + przycisk zarezerwuj. -> powoduje to: dodanie wizyty do kalendarza, dodanie sppotkania do listy spotkan klienta 
			  + po zarezerwowaniu spotkania osoba swiadczaca uslugi otrzymuje maila potwierdzajacego wraz z wszelkimi danymi.
			
			- usuwanie wizyty: przycisk -> dodatkowe potwierdzenie (yes or no)
			
			 *dwie z trzech opcji ( 'edycja' / 'usuwanie' ) pojawia sie jako przyciski pod 
			  sczegolami spotkania, natomiast ( 'dodawanie' ) bedzie pojawiac sie tylko
			  w momencie wybrania pustego terminu ( lacznie trzy przyciski ).
	
			
			
Nice to have:
- mozliwosc wyslania maila ze strony do recepcji/czat?
- mozliwosc wystawienia opini przez klienta (za dana wizyte).
- implementacja PayU
- mozliwosc dodawania pracownikow poprzez konto admina
PYTANIA:
- dynamiczne wyszukwanie uzytkownika z bazy po dowolnym kryterium (bez przeladowania strony) 	
