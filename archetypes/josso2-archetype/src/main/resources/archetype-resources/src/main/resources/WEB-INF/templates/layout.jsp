<%--
  ~ Atricore IDBus
  ~
  ~ Copyright (c) 2009, Atricore Inc.
  ~
  ~ This is free software; you can redistribute it and/or modify it
  ~ under the terms of the GNU Lesser General Public License as
  ~ published by the Free Software Foundation; either version 2.1 of
  ~ the License, or (at your option) any later version.
  ~
  ~ This software is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  ~ Lesser General Public License for more details.
  ~
  ~ You should have received a copy of the GNU Lesser General Public
  ~ License along with this software; if not, write to the Free
  ~ Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  ~ 02110-1301 USA, or see the FSF site: http://www.fsf.org.
  --%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>

    <title><fmt:message key="title"/></title>

    <meta name="Title" content="Atricore, Inc"/>
    <meta name="Author" content="Nicolas Calabrese"/>
    <meta name="Author" content="Sebastian Gonzalez Oyuela"/>
    <meta name="Keywords" content="Atricore, IDBUS, JOSSO, SSO, Single Sign On, SAML, SAML2"/>
    <meta name="Description" content="Atricore IDBus,  SAML 2.0 Front End"/>

    <meta name="Robots" content="index,follow"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <link href="<%=request.getContextPath()%>/resources/css/screen.css" rel="stylesheet" type="text/css" media="screen, projector"/>

    <!--[if IE 6]><link href="<%=request.getContextPath()%>/resources/css/ie6.css" rel="stylesheet" type="text/css" media="screen, projector" /><![endif]-->
    <!--[if IE 7]><link href="<%=request.getContextPath()%>/resources/css/ie7.css" rel="stylesheet" type="text/css" media="screen, projector" /><![endif]-->

</head>

<body>
<div><p class="alert browser-support">Using a modern browser that supports web standards ensures that the site's full
    visual experience is available. Consider <a href="http://www.firefox.com/">upgrading your browser</a>
    if you are using an older technology.</p></div>


<div id="wrapper">

    <!-- PAGE HEADER  -->

    <div id="header">

        <h1> <!-- Logo My Company 1-->
            <a href="http://www.mycompany.org" title="Click here to go to the homepage">
                <img src="<%=request.getContextPath()%>/resources/img/content/company-logo-2.png" alt="Atricore IDBus" width="372" height="48"/>
            </a>
        </h1> <!-- /Logo My Company 1-->


        <h2> <!-- Log My Company 2 -->
            <a href="http://www.mycompany.com">
                <img src="<%=request.getContextPath()%>/resources/img/content/company-logo-1.gif" alt="Atricore, the company behind JOSSO" width="254"
                     height="66"/>
            </a>
        </h2> <!-- /Logo My Comnpany  2-->


    </div>


    <!-- PAGE CONTENT  -->
    <div id="content" class="clearfix">
        <tiles:insertAttribute name="content"/>
    </div> <!-- /content -->

    <!-- PAGE FOOTER  -->
    <div id="footer">
        <p>Copyright &copy; 2009. Atricore, Inc.</p>
    </div>


</div>

</body>
</html>
