<%--
- form.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="customer.whine.form.label.header" path="header"/>
	<acme:input-textarea code="customer.whine.form.label.description" path="description"/>
	<jstl:if test="${_command == 'show' }">
		<acme:input-textarea code="customer.whine.form.label.redress" path="redress"/>
	</jstl:if>
	
	<jstl:if test="${_command == 'create' }">
		<acme:input-checkbox code="customer.whine.form.label.confirmation" path="confirmation"/>	
		<acme:submit code="customer.whine.form.button.create" action="/customer/whine/create"/>
	</jstl:if>	
</acme:form>
