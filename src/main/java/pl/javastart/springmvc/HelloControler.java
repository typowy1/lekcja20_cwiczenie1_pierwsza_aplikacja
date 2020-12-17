package pl.javastart.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller // w tej klasie będą metody które będa mapowane na jakieś adresy
public class HelloControler {

    // kontroler służy do obsługi rządań

//    @ResponseBody //da możliwość wyświetlenia tekstu bezpośrednio na stronie bez konieczności tworzenia pliku html
//    //@RequestMapping("/hello") // wskazuje adres gdzi ewyswietli się hello localhost:8080/hello, wskazuje ż ejakas strona ma być przypieta do adresu url
//    @GetMapping("/hello") // jest to to samo co wyżej plus metoda requestmaping get
//    public String hello() {
//        return "Cześć!!!!";
//    }

//    //parametry
//    @ResponseBody
//    @GetMapping("hello1")
//    public String hello1(@RequestParam(name = "imię", required = false, defaultValue = "Anonim") String name){ // parametr name który można dodać do cześć w adresie strony, można tez nadać nazwe parametrowi np imie, oraz czy jest wymagany required = false, można też dodac defoltową wartosć defaultValue = "Anonim";
//        return "Cześć " + name; //http://localhost:8080/hello1?name=Marcin , wyświetli się cześć Marcin
//    }


    //obsługa formularz z pliku index w folderze static
//    @ResponseBody
//    //@GetMapping("hello")
//    @RequestMapping("/hello") //dzięki temu mamy get, post jednocześnie
//    public String hello(@RequestParam String name, @RequestParam Integer age){ // parametr name który można dodać do cześć w adresie strony, można tez nadać nazwe parametrowi np imie, oraz czy jest wymagany required = false, można też dodac defoltową wartosć defaultValue = "Anonim";
//        return "Cześć! " + name + " wiek: " + age; //http://localhost:8080/hello1?name=Marcin , wyświetli się cześć Marcin
//    }


    //Lista urzytkowników - pobierana z repozytorium użytkowników

//    @ResponseBody
//    //@GetMapping("hello")
//    @RequestMapping("/hello") //dzięki temu mamy get, post jednocześnie
//    public String hello(@RequestParam String name, @RequestParam Integer age) { // parametr name który można dodać do cześć w adresie strony, można tez nadać nazwe parametrowi np imie, oraz czy jest wymagany required = false, można też dodac defoltową wartosć defaultValue = "Anonim";
//        UserReposytory userReposytory = new UserReposytory();
//
//        User user = new User(name, age);
//        userReposytory.add(user);
//
//        List<User> userList = userReposytory.getAll();
//        String result = "";
//        for (User user1 : userList) {
//            result += user1.getName() + " " + " wiek: " + age + "<br/>";
//        }
//        return result; //http://localhost:8080/hello1?name=Marcin , wyświetli się cześć Marcin
//    }  // wyświetlą sie wszyscy urzytkownicy dodani przez formularz, trafią odni do listy


    // wstrzykiwanie zależności jak np mamy kilka kontrolerów

//    UserReposytory userReposytory; //uwspólnienie kożystania z tej samej klasy
//
//    public HelloControler(UserReposytory userReposytory) { // spring będzie nam tworzyć reposytory, trzebo oznaczyć klase repozytory adnotację @Repozytory
//        this.userReposytory = userReposytory;
//    }
//
//    @ResponseBody
//    //@GetMapping("hello")
//    @RequestMapping("/hello") //dzięki temu mamy get, post jednocześnie
//    public String hello(@RequestParam String name, @RequestParam Integer age) { // parametr name który można dodać do cześć w adresie strony, można tez nadać nazwe parametrowi np imie, oraz czy jest wymagany required = false, można też dodac defoltową wartosć defaultValue = "Anonim";
//
//        User user = new User(name, age);
//        userReposytory.add(user);
//
//        List<User> userList = userReposytory.getAll();
//        String result = "";
//        for (User user1 : userList) {
//            result += user1.getName() + " " + " wiek: " + age + "<br/>";
//        }
//        return result; //http://localhost:8080/hello1?name=Marcin , wyświetli się cześć Marcin
//    } // wyświetlą sie wszyscy urzytkownicy dodani przez formularz, trafią odni do listy


    //podzielenie na dodawanie, list i przekierowanie

    UserReposytory userReposytory; //uwspólnienie kożystania z tej samej klasy

    public HelloControler(UserReposytory userReposytory) { // spring będzie nam tworzyć reposytory, trzebo oznaczyć klase repozytory adnotację @Repozytory
        this.userReposytory = userReposytory;
    }

    //    @ResponseBody tu ma dodać a nie wyświetlić
    @RequestMapping("/add") //dzięki temu mamy get, post jednocześnie
    public String hello(@RequestParam String name, @RequestParam Integer age) { // parametr name który można dodać do cześć w adresie strony, można tez nadać nazwe parametrowi np imie, oraz czy jest wymagany required = false, można też dodac defoltową wartosć defaultValue = "Anonim";

        User user = new User(name, age);
        userReposytory.add(user);
        return "redirect:/list"; // przekierowanie na liste po dodaniu
    }

    @ResponseBody
    @RequestMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "0") Integer minWiek) { //http://localhost:8080/list?minWiek=10 //required = false nie wymagane, defaultValue = "0" domyślna wartość od zera, dla parametru min wiek wyświetli osoby z wartościami większe niz np minWiek=10
        List<User> userList = userReposytory.getAll();
        String result = "";
        for (User user1 : userList) {
            if (user1.getAge() >= minWiek) {
                result += user1.getName() + " " + " wiek: " + user1.getAge() + "<br/>";
            }
        }
        return result;
    } // wyświetlą sie wszyscy urzytkownicy dodani przez formularz, trafią odni do listy
}
