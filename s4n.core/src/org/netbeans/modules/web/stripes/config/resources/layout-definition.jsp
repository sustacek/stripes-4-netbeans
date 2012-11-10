<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
    isELIgnored="false"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page" />

<s:layout-definition>
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
        <head>
            <title>Sample title</title>
            
            <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css" media="all" />
            <script type="text/javascript" src="${contextPath}/js/javascript.js"></script>
            
            <s:layout-component name="headers"/>
            
        </head>
        <body>
            <div id="container">
                
                <div id="intro">
                    <div id="pageHeader">
                        
                        <h1><span><a title="Sample title" href="${contextPath}/">Sample title</a></span></h1>
                    </div>
                    
                    <div id="quickSummary">
                        <%-- header1 variable is passed from layout-usage via corresponding attribute --%>
                        <h2>${header1}</h2>
                        <p>Sample subheader</p>
                    </div>
                </div> <!-- head -->
                
                <div id="statusBar">
                    <s:layout-component name="statusBar"/>
                    <s:messages />
                    <s:errors globalErrorsOnly="true"/>&nbsp;
                </div>
            
                <div id="navigation">
                    <s:layout-component name="navigation"/>
                </div>
                
                <div id="content">
                    <s:layout-component name="body"/>
                </div> <!-- content -->
                
                <div id="footer">
                    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
                </div>
                
                <div id="menu">              
                    <%@ include file="/WEB-INF/jspf/menu.jspf" %>
                        
                </div> <!-- menu -->
                
            </div> <!-- container -->
            
        </body>
    </html>
</s:layout-definition>