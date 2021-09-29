package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int RESUME_MAX_SIZE = 4; //10000;
    private Resume[] storage = new Resume[RESUME_MAX_SIZE];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage,0,size - 1, null);
        size = 0;
    }

    /* проверка на наличие резюме в storage
    если нашли, то возвращаем порядковый номер,
    если не нашли, то возвращаем -1
     */
    public int check(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                return i;
            }
        }
        return -1;
    }


    public void update(Resume r) {
        int i = check(r.getUuid());
        if (i != -1){
            System.out.println("Обновляем " + storage[i].getUuid());
            //Здесь должна быть логика обновления storage[i]
            //Сейчас обновлять нечего, т.к. у нас только одно ключевое поле в классе Resume
        }
        else{
            System.out.println("Ошибка update. Резюме не найдено");
        }
    }

    public void save(Resume r) {
        if (size == RESUME_MAX_SIZE){
            System.out.println("Ошибка save. Переполнение массива резюме");
            return;
        }
        if (check(r.getUuid()) == -1){
           storage[size] = r;
           size++;
        }
        else{
            System.out.println("Ошибка save. Резюме уже есть");
        }
    }

    public Resume get(String uuid) {
        int i = check(uuid);
        if (i != -1){
            return storage[i];
        }
        else{
            System.out.println("Ошибка get. Резюме не найдено");
            return null;
        }
    }

    public void delete(String uuid) {
        int i = check(uuid);
        if (i != -1){
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
        else{
            System.out.println("Ошибка delete. Резюме не найдено");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResume = new Resume[size];

        allResume = Arrays.copyOf(storage, size);

        return allResume;
    }

     public int size() {
        return size;
    }
}
