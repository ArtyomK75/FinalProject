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
            <c:when test="${pageAction == 'beginEditPayment'}">

                <%@ include file="/WEB-INF/jspf/client/edit_client_payment.jspf" %>

            </c:when>

            <c:when test="${pageAction == 'editUserCounts'}">

                <%@ include file="/WEB-INF/jspf/client/edit_client_counts_begin.jspf" %>

            </c:when>



        </c:choose>

        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </tr>

</table>
</body>
</html>
