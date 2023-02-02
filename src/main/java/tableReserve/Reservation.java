package tableReserve;


public class Reservation {
    private int peopleCount;
    private String name;

    public Reservation(int peopleCount, String name) {
        this.peopleCount = peopleCount;
        this.name = name;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public String getName() {
        return name;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public void setName(String name) {
        this.name = name;
    }
}
