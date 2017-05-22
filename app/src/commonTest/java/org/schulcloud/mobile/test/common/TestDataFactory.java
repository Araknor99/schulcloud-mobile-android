package org.schulcloud.mobile.test.common;

import org.schulcloud.mobile.data.model.Event;
import org.schulcloud.mobile.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static User makeUser(String uniqueSuffix) {
        User u = new User();
        u.set_id(randomUuid());
        u.setLastName(uniqueSuffix);
        u.setEmail(uniqueSuffix);
        u.setFirstName(uniqueSuffix);
        return u;
    }

    public static List<User> makeListUsers(int number) {
        List <User> users = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            users.add(makeUser(String.valueOf(i)));
        }
        return users;
    }

    public static Event makeEvent(String uniqueSuffix) {
        Event e = new Event();

        return e;
    }

    public static List<Event> makeListEvents(int number) {
        List <Event> events = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            events.add(makeEvent(String.valueOf(i)));
        }
        return events;
    }

}