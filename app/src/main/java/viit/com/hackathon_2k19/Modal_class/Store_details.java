package viit.com.hackathon_2k19.Modal_class;

public class Store_details {

    public String user_photo_url,user_name,user_phno,user_email;

    public Store_details() {
    }

    public Store_details(String user_photo_url, String user_name, String user_phno, String user_email) {
        this.user_photo_url = user_photo_url;
        this.user_name = user_name;
        this.user_phno = user_phno;
        this.user_email = user_email;
    }

    public String getUser_photo_url() {
        return user_photo_url;
    }

    public void setUser_photo_url(String user_photo_url) {
        this.user_photo_url = user_photo_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phno() {
        return user_phno;
    }

    public void setUser_phno(String user_phno) {
        this.user_phno = user_phno;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
