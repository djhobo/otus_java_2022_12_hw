package homework;

import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private final NavigableMap<Customer, String> map;

    public CustomerService() {
        map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        return copy(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copy(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copy(Map.Entry<Customer, String> entry) {
        Map.Entry<Customer, String> temp = null;
        if (entry != null) {
            temp = new AbstractMap.SimpleEntry<>(new Customer(entry.getKey().getId(), entry.getKey().getName(),
                    entry.getKey().getScores()), entry.getValue());
        }
        return temp;
    }
}
