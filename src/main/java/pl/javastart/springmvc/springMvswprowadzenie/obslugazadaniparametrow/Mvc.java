package pl.javastart.springmvc.springMvswprowadzenie.obslugazadaniparametrow;

import org.springframework.web.bind.annotation.*;

public class Mvc {
    //https://javastart.pl/kurs/spring/spring-spring-mvc/lekcja/spring-obs%C5%82uga-%C5%BC%C4%85da%C5%84-i-parametr%C3%B3w


    // Jeśli chcemy obsługiwać wyłącznie żądania GET kierowane do strony głównej, zapiszemy:

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    //Jeśli chcemy obsłużyć żądania GET i POST, ale nie pozostałe typu PUT, DELETE itp. możemy także przekazać tablicę wartości typu RequestMethod.

    @RequestMapping(path = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String home1() {
        return "home1";
    }

//    Najczęściej będziemy obsługiwali jednak żądania jednego, konkretnego typu i wtedy adnotacja @RequestMapping może nie być aż tak czytelna na pierwszy rzut oka. Zamiast niej możemy wykorzystać jedną z wygodnych alternatyw:
//
//    @GetMapping
//    @PostMapping
//    @PutMapping
//    @DeleteMapping
//    @PatchMapping

    //Oznaczając metodę co najmniej jedną z tych adnotacji wskazujemy, że obsługuje ona żądanie danego typu, np.:
    @GetMapping("/")
    public String home2() {
        return "home2";
    }


    //Parametry, atrybuty i nagłówki

//    Korzystając z kilku dodatkowych adnotacji możemy oznaczyć w odpowiedni sposób parametry metod kontrolera a Spring wyciągnie z żądania potrzebne nam informacje.
//
//    @RequestParam - parametr żądania
//    @RequestHeader - nagłówek
//    @RequestAttribute - atrybut zapisany na poziomie żądania
//    @SessionAttribute - atrybut zapisany na poziomie sesji

    //  Przykład wykorzystania:

    @GetMapping("/")
    public String home3(@RequestParam(name = "username") String username) {
        System.out.println("Hello " + username);
        return "home3";
    }

    //  Możemy dodatkowo oznaczyć parametr jako wymagany oraz ustawić jego domyślną wartość, która zostanie wykorzystana, gdy użytkownik nie doda parametru do zapytania.

    @GetMapping("/")
    public String home4(@RequestParam(name = "username",
            defaultValue = "Nieznajomy",
            required = true) String username) {
        System.out.println("Hello " + username);
        return "home4";
    }

    // Jeśli ustawimy parametr jako wymagany i nie ustawimy jego wartości domyślnej to przy próbie odwołania się do takiej metody Spring wyśle w odpowiedzi komunikat 400 - Bad Request.

    // Jeśli nazwa parametru metody jest identyczna z nazwą parametru żądania, możemy pominąć określenie atrybutu name w adnotacji.
    @GetMapping("/")
    public String home5(@RequestParam(defaultValue = "Nieznajomy") String username) {
        System.out.println("Hello " + username);
        return "home5";
    }

    //   Podobne zasady dotyczą wyciągania wartości nagłówków. Jeśli chcielibyśmy się dowiedzieć z jakiej przeglądarki korzysta użytkownik możemy dodać do naszej metody kolejny parametr oznaczony adnotacją @RequestHeader.

    @GetMapping("/")
    public String home6(
            @RequestParam(defaultValue = "Nieznajomy") String username,
            @RequestHeader("user-agent") String userAgent) {
        System.out.println("Hello " + username);
        System.out.println("Web browser: " + userAgent);
        return "home6";
    }

    //Dodatkowym plusem korzystania z parametrów oznaczonych odpowiednimi adnotacjami jest to, że możemy od razu określić ich odpowiedni typ. Przy wyciąganiu parametrów z obiektu żądania typu HttpServletRequest, czyli np. request.getParameter("username") w wyniku zawsze uzyskujemy wartość typu String. Jednak parametr często będzie jakąś liczbą i musielibyśmy wtedy ręcznie parsować taką wartość korzystając np. z Integer.parseInt(). W przypadku parametrów oznaczonych @RequestParam Spring zrobi to za nas.
    //
    @GetMapping("/")
    public String home7(
            @RequestParam(defaultValue = "Nieznajomy") String username,
            @RequestParam(defaultValue = "0") int age,
            @RequestHeader("user-agent") String userAgent) {
        System.out.println("Hello " + username);
        if (age > 0) {
            System.out.println("Your age is: " + age);
        }
        System.out.println("Web browser: " + userAgent);
        return "home7";
    }

    //Przechodząc w przeglądarce do adresu http://localhost:8080/nazwa-aplikacji/?username=Jurek&age=20 zobaczymy w konsoli komunikat typu:
    //
    //Hello Jurek
    //Your age is: 20
    //Web browser: Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/7.0)
    //
    //Jak widać parsowanie parametru z wiekiem Spring wykonał w tle.


    //Przykład
    //
    //Napiszmy dla przykładu prostą aplikację kalkulatora. Na stronie głównej zdefiniujemy formularz z polami na dwie liczby oraz przycisk po wciśnięciu którego w konsoli powinien wyświetlić się wynik dodawania.
    //
    //Zaczynamy od projektu Spring Starter Project z pakowaniem ustawionym na war i dołączając starter-web. Spring generuje dla nas dwie klasy startowe, które zostawiamy w spokoju. Dodajemy kontroler strony głównej, stronę widoku strony głównej i konfigurujemy ViewResolvera ustawiając odpowiedni prefix i suffix w pliku application.properties.


    //Jeśli aplikację chcesz uruchamiać poprzez wywołanie metody main, pamiętaj o dodaniu zależności mavena:
    //
    //<dependency>
    //	<groupId>org.apache.tomcat.embed</groupId>
    //	<artifactId>tomcat-embed-jasper</artifactId>
    //	<scope>provided</scope>
    //</dependency>

    //Przejdźmy do zdefiniowania widoku.
    //
    ///WEB-INF/index.jsp
    //
    //<%@ page language="java" contentType="text/html; charset=UTF-8"
    //    pageEncoding="UTF-8"%>
    //<!DOCTYPE html>
    //<html>
    //<head>
    //<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    //<title>Spring calculator</title>
    //</head>
    //<body>
    //    <h1>Spring calculator</h1>
    //    <form method="post">
    //        <input name="valueA" type="number" /> <br />
    //        <input name="valueB" type="number" /> <br />
    //        <input value="Add" type="submit" />
    //    </form>
    //</body>
    //</html>

//na stronie głównej definiujemy prosty formularz z dwoma polami na liczby. Określamy, że formularz będzie wysyłany metodą POST. Nie dodając atrybutu action sprawimy, że formularz zostanie wysłany "sam do siebie", czyli na stronę główną. Dzięki temu, że w kontrolerze możemy zdefiniować osobne metody do obsługi żądań GET i POST nie stanowi to problemu.
//
//HomeController.java
//
//package pl.javastart.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class HomeController {
//
//    @GetMapping("/")
//    public String home() {
//        return "index";
//    }
//
//    @PostMapping("/")
//    public String calculate(@RequestParam(defaultValue = "0") int valueA,
//                            @RequestParam(defaultValue = "0") int valueB) {
//        int result = valueA + valueB;
//        System.out.printf("%d + %d = %d\n", valueA, valueB, result);
//        return "index";
//    }
//}
    //W kontrolerze definiujemy dwie metody. Pierwsza home() odpowiada za wyświetlenie strony glównej przy zwykłym zapytaniu typu GET. Druga calculate() służy do obsługi formularza i metody POST. Definiujemy w niej dwa parametry, które stanowią parametry zapytania. Aby zabezpieczyć się przed sytuacją, gdzie użytkownik nie poda którejś z wartości valueA lub valueB ustawiamy ich domyślną wartość na 0.
    //
    //Na razie nie wiemy jeszcze jak przekazać wynik do widoku, który zostanie wysłany do użytkownika, dlatego wynik obliczamy i wyświetlamy w konsoli.
    //
    //Na koniec nie zapominamy o ustawieniu prefixu i sufixu w konfigruacji spring boota.
    //
    //application.properties
    //
    //spring.mvc.view.prefix=/WEB-INF/
    //spring.mvc.view.suffix=.jsp

    //
    //Uruchamiamy aplikację na Tomkacie. Po wpisaniu dowolnych wartości całkowitoliczbowych w konsoli zobaczymy obliczony wynik.


}
