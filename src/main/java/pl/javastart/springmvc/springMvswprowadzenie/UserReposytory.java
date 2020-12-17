package pl.javastart.springmvc.springMvswprowadzenie;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserReposytory {
    //repozytorium dla userów trzymanych w liście, klasa odpowiedzialna za trzymanie użytkowników

    private List<User> userList = new ArrayList<>();

    public List<User> getAll() { //zwraca listę użytkowników
        return userList;
    }

    public void add(User user) {
        userList.add(user);
    }
}
