package firm.provider.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "public")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String firstName;
    private String lastName;
    private String thirdName;
    @Column(unique=true)
    private String mail;
    private String password;

    private int task1Mistakes;
    private int task2Mistakes;
    private int task3Mistakes;
    private int task4Mistakes;

    private boolean task1Completed;
    private boolean task2Completed;
    private boolean task3Completed;
    private boolean task4Completed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTask1Mistakes() {
        return task1Mistakes;
    }

    public void setTask1Mistakes(int task1Mistakes) {
        this.task1Mistakes = task1Mistakes;
    }

    public int getTask2Mistakes() {
        return task2Mistakes;
    }

    public void setTask2Mistakes(int task2Mistakes) {
        this.task2Mistakes = task2Mistakes;
    }

    public int getTask3Mistakes() {
        return task3Mistakes;
    }

    public void setTask3Mistakes(int task3Mistakes) {
        this.task3Mistakes = task3Mistakes;
    }

    public int getTask4Mistakes() {
        return task4Mistakes;
    }

    public void setTask4Mistakes(int task4Mistakes) {
        this.task4Mistakes = task4Mistakes;
    }

    public boolean isTask1Completed() {
        return task1Completed;
    }

    public void setTask1Completed(boolean task1Completed) {
        this.task1Completed = task1Completed;
    }

    public boolean isTask2Completed() {
        return task2Completed;
    }

    public void setTask2Completed(boolean task2Completed) {
        this.task2Completed = task2Completed;
    }

    public boolean isTask3Completed() {
        return task3Completed;
    }

    public void setTask3Completed(boolean task3Completed) {
        this.task3Completed = task3Completed;
    }

    public boolean isTask4Completed() {
        return task4Completed;
    }

    public void setTask4Completed(boolean task4Completed) {
        this.task4Completed = task4Completed;
    }
}
