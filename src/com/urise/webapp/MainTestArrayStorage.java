package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        ARRAY_STORAGE.update(r1);
        ARRAY_STORAGE.update(r3);
        ARRAY_STORAGE.update(r2);

        //новые проверки
        System.out.println("" );
        System.out.println("Начало проверок" );

        ARRAY_STORAGE.get("uuid4");
        ARRAY_STORAGE.delete("uuid4");
        ARRAY_STORAGE.save(r3);

        Resume r4 = new Resume();
        r4.setUuid("uuid4");
        ARRAY_STORAGE.update(r4);

        ARRAY_STORAGE.save(r4);

        Resume r5 = new Resume();
        r5.setUuid("uuid5");
        ARRAY_STORAGE.save(r5);

        System.out.println("Конец проверок" );
        System.out.println("" );
        //-----------------------

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
