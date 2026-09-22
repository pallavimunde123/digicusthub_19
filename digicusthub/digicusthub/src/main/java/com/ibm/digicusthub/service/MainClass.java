package com.ibm.digicusthub.service;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;

public class MainClass {

    public static void main(String[] args) {
        List<String> nameSt = List.of("Akshay","Siya","Rajesh","Akshayy","Sagar","Priyankaa");

        Map<Character,Long> groupChar = nameSt.stream().collect
                (Collectors.groupingBy(name -> name.charAt(0), Collectors.counting()));

        groupChar.forEach((key,value)-> System.out.println(key+" "+value));

        System.out.println("----------------------------------------------");
                 Pattern pattern= Pattern.compile("(.)\\1+");
                 for(String name : nameSt){
                  Matcher matcher=  pattern.matcher(name);
                  if(matcher.find()){
                      System.out.println(name + " contains repeated char");
                  }
                 }
        System.out.println("------------------------------------------");

        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "HR", 70000),
                new Employee(2, "Bob", "IT", 90000),
                new Employee(3, "Charlie", "IT", 85000),
                new Employee(4, "David", "Finance", 90000),
                new Employee(5, "Eve", "HR", 60000),
                new Employee(6, "Frank", "IT", 75000),
                new Employee(2, "Bob", "IT", 90000));

        employees.stream().filter(i-> i.getSalary() > 80000).forEach(System.out::println);

        //studnet (id,name)  s
        //book (id,title)   b
        //transaction(id,student_id,book_id)   t

      //  select s.id , s.name , count(t.book_id) as total_books from Student s
        // JOIN transaction t on s.id = t.student_id
        //Group by s.id, s.name having total_books =(
          // select MAX(book_count) from (select count(*) as book_count from transaction group by student_id)as sub);
        



    }
}
