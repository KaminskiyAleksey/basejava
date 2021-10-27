package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.urise.webapp.model.ContactType.MAIL;
import static com.urise.webapp.model.ContactType.PHONE;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {

    public static Resume fillResume(String uuid, String fullName) {

        //EXPERIENCE
        Organization.Position position1 = new Organization.Position(2014, Month.of(10), 2016, Month.of(1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO");
        Organization.Position position2 = new Organization.Position(2013, Month.of(10), "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок");

        Organization organization1 = new Organization("Wrike", "https://www.wrike.com/", position1);

        Organization organization2 = new Organization("Java Online Projects", "http://javaops.ru/", position2);

        List<Organization> listOrganisation = Arrays.asList(organization1, organization2);
        OrganizationSection organizationSectionExperience = new OrganizationSection(listOrganisation);

        //EDUCATION
        Organization.Position education1 = new Organization.Position(2011, Month.of(3), 2011, Month.of(3), "Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML", null);
        Organization.Position education2 = new Organization.Position(2013, Month.of(3), 2013, Month.of(5), "Functional Programming Principles in Scala by Martin Odersky", null);

        Organization colledge1 = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", education1);

        Organization colledge2 = new Organization("Coursera", "https://www.coursera.org/course/progfun", education2);

        List<Organization> listColledge = Arrays.asList(colledge1, colledge2);
        OrganizationSection organizationSectionEducation = new OrganizationSection(listColledge);

        //QUALIFICATIONS
        List<String> listQualifications = Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        ListSection qualificationsSection = new ListSection(listQualifications);

        //PERSONAL
        TextSection personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        //OBJECTIVE
        TextSection objectiveSection = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        //ACHIEVEMENT
        List<String> listAchivement = Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        ListSection achivementSection = new ListSection(listAchivement);

        //Resume
        Resume resume = new Resume(uuid, fullName);

        //CONTACTS

        resume.addContact(PHONE, "+7(921) 855-0482");
        resume.addContact(MAIL, "gkislin@yandex.ru");

        resume.addSection(PERSONAL, personalSection);
        resume.addSection(OBJECTIVE, objectiveSection);
        resume.addSection(ACHIEVEMENT, achivementSection);
        resume.addSection(QUALIFICATIONS, qualificationsSection);
        resume.addSection(EXPERIENCE, organizationSectionExperience);
        resume.addSection(EDUCATION, organizationSectionEducation);

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = fillResume(UUID.randomUUID().toString(), "Григорий Кислин");

        //Print
        System.out.println("Uuid = " + resume.getUuid());
        System.out.println("Fullname " + resume.getFullName());

        System.out.println("");
        System.out.println("Контакты");

        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType + " " + resume.getContact(contactType));
        }

        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType);
            System.out.println(resume.getSection(sectionType));
            System.out.println("");
        }
    }
}
