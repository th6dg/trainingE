package org.example;

import java.util.*;

/*
    hashCode() → bucket selection
    equals() → detects duplicates
    Comparable
    Comparator
 */
public class Main {

    static Random random = new Random();
    static int length = 8;

    public static Employee createRandomEmployee() {
        Integer id = Integer.valueOf(random.nextInt(20));
        Integer age = Integer.valueOf(random.nextInt(100));
        String randomString = new Random().ints(length, 97, 123) // a-z
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return new Employee(randomString, id, age);
    }
    public static void main(String[] args) {
        Set<Employee> Danhsach1 = new HashSet<>();
        Set<Employee> Danhsach2 = new LinkedHashSet<>();
        Set<Employee> Danhsach3 = new TreeSet<>();

        for (int i = 0; i<20; i++) {
            Employee employee = createRandomEmployee();
            if(Danhsach1.contains(employee) && Danhsach2.contains(employee)) {
                System.out.print("Duplicate employee: ");
                System.out.println(employee);
            }
            Danhsach1.add(employee);
            Danhsach2.add(employee);
            Danhsach3.add(employee);
        }

        System.out.println("======= Hash Set ========");
        for (Employee e:Danhsach1) {
            System.out.println(e);
        }

        System.out.println("======= Linked Hash Set ========");
        for (Employee e:Danhsach2) {
            System.out.println(e);
        }

        System.out.println("========= Tree Set =========");
        for (Employee e:Danhsach3) {
            System.out.println(e);
        }

    }
}