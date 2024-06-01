package firm.provider.dto;

import firm.provider.model.MyUser;

public class UserInfo {
    private String firstName;
    private String lastName;
    private String thirdName;
    private String mail;
    private int task1Mistakes;
    private int task2Mistakes;
    private int task3Mistakes;
    private int task4Mistakes;

    private boolean task1Completed;
    private boolean task2Completed;
    private boolean task3Completed;
    private boolean task4Completed;

    public UserInfo() {
    }

    public UserInfo(String firstName, String lastName, String thirdName, String mail, int task1Mistakes, int task2Mistakes, int task3Mistakes, int task4Mistakes, boolean task1Completed, boolean task2Completed, boolean task3Completed, boolean task4Completed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.thirdName = thirdName;
        this.mail = mail;
        this.task1Mistakes = task1Mistakes;
        this.task2Mistakes = task2Mistakes;
        this.task3Mistakes = task3Mistakes;
        this.task4Mistakes = task4Mistakes;
        this.task1Completed = task1Completed;
        this.task2Completed = task2Completed;
        this.task3Completed = task3Completed;
        this.task4Completed = task4Completed;
    }

    public static UserInfo fromUser(MyUser user) {
        UserInfo userInfo = new UserInfo();

        userInfo.firstName = user.getFirstName();
        userInfo.lastName = user.getLastName();
        userInfo.thirdName = user.getThirdName();
        userInfo.task1Completed = user.isTask1Completed();
        userInfo.task2Completed = user.isTask2Completed();
        userInfo.task3Completed = user.isTask3Completed();
        userInfo.task4Completed = user.isTask4Completed();
        userInfo.task1Mistakes = user.getTask1Mistakes();
        userInfo.task2Mistakes = user.getTask2Mistakes();
        userInfo.task3Mistakes = user.getTask3Mistakes();
        userInfo.task4Mistakes = user.getTask4Mistakes();

        return userInfo;
    }
}
