package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        var customers = Arrays.asList(
                new Customer("Jane", 23),
                new Customer("Danny", 26),
                new Customer("Steve", 36),
                new Customer("Connor", 37)
        );
        var oldest = customers.stream().filter(customer -> customer.getAge() > 30).toList();
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(new File("json.txt"), oldest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            XmlMapper xm = new XmlMapper();
            xm.writeValue(new File("xml.txt"), customers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            var readJson = om.readValue(new File("json.txt"), List.class);
            System.out.println(readJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File("+30ages.txt");
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(oldest);
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object object = ois.readObject();
            if (object instanceof List readCustomer) {
                System.out.println(readCustomer);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}