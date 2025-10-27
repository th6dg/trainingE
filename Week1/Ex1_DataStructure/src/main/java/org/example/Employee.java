package org.example;

public class Employee implements Comparable<Employee>{
    private String name;
    private Integer id;
    private Integer age;

    public Employee() {}

    public Employee(String name, Integer id, Integer age){
        this.age = age;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Employee) {
            if(((Employee) o).getId().equals(this.id)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Employee: id = "+getId()+",  name = "+getName()+",  age = "+getAge();
    }

    @Override
    public int compareTo(Employee o) {
        {
            int cmp = this.id.compareTo(o.id);          // compare id first
            if (cmp != 0) return cmp;

            cmp = this.name.compareTo(o.name);         // compare name if id equal
            if (cmp != 0) return cmp;

            return this.age.compareTo(o.age);          // compare age if id & name equal
        }
    }
}
