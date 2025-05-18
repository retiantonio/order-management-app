package DataModel;

public class Client {

    private int id;

    private String firstName;
    private String lastName;

    private String address;
    private String phone;

    public Client(int id, String firstName, String lastName, String address, String phone) {
        this.id = id;

        this.firstName = firstName;
        this.lastName = lastName;

        this.address = address;
        this.phone = phone;
    }


}
