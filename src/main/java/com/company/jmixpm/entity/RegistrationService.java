package com.company.jmixpm.entity;

import io.jmix.core.UnconstrainedDataManager;
import io.jmix.email.EmailException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RegistrationService {

    private final UnconstrainedDataManager unconstrainedDataManager;

    public RegistrationService(UnconstrainedDataManager unconstrainedDataManager) {
        this.unconstrainedDataManager = unconstrainedDataManager;
    }

    /**
     * @return true if user with this email (or login) already exists.
     */
    public boolean checkUserAlreadyExist(String email) {
        List<User> users = unconstrainedDataManager.load(User.class).query("select u from User u where u.email = :email " +
                        "or u.username = :email")
                .parameter("email", email)
                .list();
        return !users.isEmpty();
    }

    public User registerNewUser(String email, String firstName, String lastName) {
        User user = unconstrainedDataManager.create(User.class);
        user.setUsername(email);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setActive(false);
        user.setNeedsActivation(true);

        user = unconstrainedDataManager.save(user);
        return user;
    }

    public String generateRandomActivationToken() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        ThreadLocalRandom current = ThreadLocalRandom.current();
        String generatedString = current.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public void saveActivationToken(User user, String activationToken) {
        user = unconstrainedDataManager.load(User.class)
                .id(user.getId())
                .one();

        user.setActivationToken(activationToken);

        unconstrainedDataManager.save(user);
    }

    public void sendActivationEmail(User user) throws EmailException {
        // todo send activation email
    }

    @Nullable
    public User loadUserByActivationToken(String token) {
        // todo load user by activation token
        return null;
    }

    public void activateUser(User user, String password) {
        // todo activate user
        // todo set password
        // todo assign roles
    }
}
