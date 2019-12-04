package in.games.sm.Adapter;

public class NotificationObjects {

    String starting_num,number,end_num;

    public NotificationObjects() {
    }

    public NotificationObjects(String starting_num, String number, String end_num) {
        this.starting_num = starting_num;
        this.number = number;
        this.end_num = end_num;
    }

    public String getStarting_num() {
        return starting_num;
    }

    public void setStarting_num(String starting_num) {
        this.starting_num = starting_num;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEnd_num() {
        return end_num;
    }

    public void setEnd_num(String end_num) {
        this.end_num = end_num;
    }
}
