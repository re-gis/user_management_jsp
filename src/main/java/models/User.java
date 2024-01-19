package models;

public class User {
    private int id;
    private String name;
    private int age;
    private String code;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public User(int id, String name, int age, String school, String code) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.school = school;
        this.code = code;
    }

    public User(String name, int age, String school, String code){
        this.name = name;
        this.age = age;
        this.school = school;
        this.code = code;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}