<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Admin" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <tr>
        <c:choose>
            <c:when test="${pageAction == 'beginEditUser'}">
                <%@ include file="/WEB-INF/jspf/admin/edit_user_begin.jspf" %>

            </c:when>

            <c:when test="${pageAction == 'newUser'}">
                <%@ include file="/WEB-INF/jspf/admin/edit_user_new.jspf" %>
            </c:when>

            <c:when test="${pageAction == 'editUser'}">
                <%@ include file="/WEB-INF/jspf/admin/edit_user_exist.jspf" %>
            </c:when>

            <c:when test="${pageAction == 'editUserCounts'}">
                <%@ include file="/WEB-INF/jspf/admin/edit_user_counts.jspf" %>
            </c:when>

            <c:when test="${pageAction == 'editUserCountsBegin'}">
                <%@ include file="/WEB-INF/jspf/admin/edit_user_counts_begin.jspf" %>
            </c:when>

            <c:when test="${pageAction == 'editUserCreditCardBegin'}">
                <%@ include file="/WEB-INF/jspf/admin/edit_user_credit_card_begin.jspf" %>
            </c:when>

            <c:when test="${pageAction == 'editUserCreditCard'}">
                <%@ include file="/WEB-INF/jspf/admin/edit_user_credit_card.jspf" %>
            </c:when>


        </c:choose>

        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </tr>

</table>
</body>
</html>
