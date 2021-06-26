import java.util.HashMap;
import java.util.HashSet;

public class PhoneBook {

    HashMap<String, HashSet<String>> phonebook;

    public PhoneBook() {
        this.phonebook = new HashMap<>();
    }

    public void add(String name, String phone) {
        HashSet<String> set = phonebook.getOrDefault(name, new HashSet<>());
        set.add(phone);
          phonebook.put(name, set);
    }

    public void get(String name) {
        System.out.println(phonebook.get(name));
    }
}
