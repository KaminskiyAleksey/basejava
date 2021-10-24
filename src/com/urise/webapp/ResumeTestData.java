package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.ContactType.MAIL;
import static com.urise.webapp.model.ContactType.PHONE;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        //CONTACTS
        Map<ContactType, String> mapContact = new HashMap<ContactType, String>();
        mapContact.put(PHONE, "+7(921) 855-0482");
        mapContact.put(MAIL, "gkislin@yandex.ru");

        //EXPERIENCE
        Position position1 = new Position("Старший разработчик (backend)", LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO");
        Position position2 = new Position("Автор проекта", LocalDate.of(2013, 10, 1), LocalDate.now(), "Создание, организация и проведение Java онлайн проектов и стажировок");

        List<Position> listPosition1 = Arrays.asList(position1);
        Organization organization1 = new Organization("Wrike", listPosition1);

        List<Position> listPosition2 = Arrays.asList(position2);
        Organization organization2 = new Organization("Java Online Projects", listPosition2);

        List<Organization> listOrganisation = Arrays.asList(organization1, organization2);
        OrganizationSection organizationSectionExperience = new OrganizationSection(listOrganisation);

        //EDUCATION
        Position education1 = new Position("Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML", LocalDate.of(2011, 3, 1), LocalDate.of(2011, 3, 1), null);
        Position education2 = new Position("Functional Programming Principles in Scala by Martin Odersky", LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1), null);

        List<Position> listEducation1 = Arrays.asList(education1);
        Organization colledge1 = new Organization("Luxoft", listEducation1);

        List<Position> listEducation2 = Arrays.asList(education2);
        Organization colledge2 = new Organization("Coursera", listEducation2);

        List<Organization> listColledge = Arrays.asList(colledge1, colledge2);
        OrganizationSection organizationSectionEducation = new OrganizationSection(listColledge);

        //QUALIFICATIONS
        List<String> listQualifications = Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        SkillsSection qualificationsSection = new SkillsSection(listQualifications);

        //PERSONAL
        List<String> listPersonal = Arrays.asList("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        SkillsSection personalSection = new SkillsSection(listPersonal);

        //OBJECTIVE
        List<String> listObjective = Arrays.asList("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        SkillsSection objectiveSection = new SkillsSection(listObjective);

        //ACHIEVEMENT
        List<String> listAchivement = Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        SkillsSection achivementSection = new SkillsSection(listAchivement);

        //Resume
        Map<SectionType, AbstractSection> mapSection = new HashMap<SectionType, AbstractSection>();
        mapSection.put(PERSONAL, personalSection);
        mapSection.put(OBJECTIVE, objectiveSection);
        mapSection.put(ACHIEVEMENT, achivementSection);
        mapSection.put(QUALIFICATIONS, qualificationsSection);
        mapSection.put(EXPERIENCE, organizationSectionExperience);
        mapSection.put(EDUCATION, organizationSectionEducation);

        Resume resume = new Resume("Григорий Кислин");
        resume.setContact(mapContact);
        resume.setSection(mapSection);

        //Print
        System.out.println("Uuid = " + resume.getUuid());
        System.out.println("Fullname " + resume.getFullName());

        System.out.println("");
        System.out.println("Контакты");
        Map<ContactType, String> mapContactGet = resume.getContact();
        for (Map.Entry<ContactType, String> entry : mapContactGet.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        Map<SectionType, AbstractSection> mapSectionGet = resume.getSection();

        for (Map.Entry<SectionType, AbstractSection> entry : mapSectionGet.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
