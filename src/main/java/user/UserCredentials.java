package user;

public class UserCredentials {

    String email;
    String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail() , user.getPassword());
    }

    public static UserCredentials getCredentialWithWrongEmail(User user) {
        return new UserCredentials(user.getEmail() + "N", user.getPassword());
    }

    public static UserCredentials getCredentialWithWrongPassword(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword() + "6");
    }
    public static UserCredentials getCredentialWithChangeData(User user) {
        return new UserCredentials(user.getEmail()+ "3", user.getPassword() + "8");
    }
}
