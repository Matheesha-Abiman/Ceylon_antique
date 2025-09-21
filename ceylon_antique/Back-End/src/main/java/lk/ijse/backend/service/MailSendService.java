package lk.ijse.backend.service;

public interface MailSendService {
    void sendRegisteredEmail(String name, String email);
    void sendLoggedInEmail(String userName, String email);
}
