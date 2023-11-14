package org.example.generators;

import com.github.javafaker.Faker;

import java.util.Locale;

public class GenerateUserData {


    public static String generateName() {
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().firstName();
        return name;
    }

    public static String generateEmail() {
        Faker faker = new Faker(new Locale("en"));
        String emailAddress = faker.internet().emailAddress();
        return emailAddress;
    }

    public static String generatePassword() {
        Faker faker = new Faker(new Locale("ru"));
        String pass = faker.internet().password();
        return pass;
    }
    public static String generateIdNumber() {
        Faker faker = new Faker(new Locale("en"));
        String uuid = faker.internet().uuid();
        return uuid;
    }


}
