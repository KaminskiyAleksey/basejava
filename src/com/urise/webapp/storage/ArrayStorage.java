package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int RESUME_MAX_CAPACITY = 4; //10000;
    private Resume[] storage = new Resume[RESUME_MAX_CAPACITY];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /* проверка на наличие резюме в storage
    если нашли, то возвращаем порядковый номер,
    если не нашли, то возвращаем -1
     */
    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                return i;
            }
        }
        return -1;
    }


    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            System.out.println("Обновляем Resume. uuid = " + storage[index].getUuid());
            storage[index] = r;
        } else {
            System.out.println("Ошибка update. Резюме не найдено. uuid = " + r.getUuid());
        }
    }

    public void save(Resume r) {
        if (size == RESUME_MAX_CAPACITY) {
            System.out.println("Ошибка save. Переполнение массива резюме");
            return;
        }
        if (findIndex(r.getUuid()) == -1) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Ошибка save. Резюме уже есть. uuid = " + r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Ошибка get. Резюме не найдено. uuid = " + uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Ошибка delete. Резюме не найдено. uuid = " + uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
