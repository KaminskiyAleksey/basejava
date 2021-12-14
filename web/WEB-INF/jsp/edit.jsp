<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.Section" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" placeholder="Имя"></dd>
            <font color="red">${full_name_error}</font>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="com.urise.webapp.model.SectionType"/>
            <h2><a>${sectionType.title}</a></h2>
            <c:choose>
                <c:when test="${sectionType=='PERSONAL' || sectionType=='OBJECTIVE'}">
                    <textarea name='${sectionType}' rows="4" cols="50"><%=resume.getSection(sectionType)%></textarea>
                </c:when>
                <c:when test="${sectionType=='QUALIFICATIONS' || sectionType=='ACHIEVEMENT'}">
                    <textarea name='${sectionType}' rows="4" cols="50"><%=String.join("\n", ((ListSection) resume.getSection(sectionType)).getItems()).trim().replaceAll("\r","")%></textarea>
                </c:when>
                <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                    &nbsp;
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>