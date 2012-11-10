<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
    isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page" />

<s:layout-render name="/layout/layout-definition.jsp" header1="Test page">
    
    <s:layout-component name="body">
        <p>Sample content</p>
        <a href="" onclick="javascript: alertMe(); return false;">Test javascriptu</a>
    </s:layout-component>
    
    <s:layout-component name="navigation">
        <ul>
            <li><a href="#">Nav item 1</a></li>
            <li><a href="#">Nav item 2</a></li>
            <li><a href="#">Nav item 3</a></li>
        </ul>
    </s:layout-component>
    
</s:layout-render>