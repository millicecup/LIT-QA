package day4;

class encapsulation {
    private String name;
    private int age;

    // Constructor
    public encapsulation(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter Name
    public String getName() {
        return name;
    }

    // Setter Name
    public void setName(String name) {
        this.name = name;
    }

    // Getter umur
    public int getAge() {
        return age;
    }

    // Setter umur
    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        } else {
            System.out.println("Invalid age. umur must be positive.");
        }
    }
}

// Main 
class Main {
    public static void main(String[] args) {
        encapsulation person = new encapsulation("Alice", 30);
        
        // use getter
        System.out.println("Name: " + person.getName());
        System.out.println("umur: " + person.getAge());
        
        // use setter
        person.setName("Bob");
        person.setAge(35);
        
        System.out.println("Updated Name: " + person.getName());
        System.out.println("Updated umur: " + person.getAge());
        
        // umur invalid
        person.setAge(-5); // error

        person.setName("trudy");
        person.setAge(28);
        System.out.println("Updated Name: " + person.getName());
        System.out.println("Updated umur: " + person.getAge());
    }
}
