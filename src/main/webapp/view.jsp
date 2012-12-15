<%--
    Copyright (C) 2009-2012 Antonio Musarra

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>

<%@page import="com.liferay.portal.kernel.dao.search.DisplayTerms"%>
<%@page import="it.dontesta.liferay.portlet.util.ActionKeys"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>

<portlet:defineObjects />
<liferay-theme:defineObjects/>

<portlet:resourceURL var="ajaxResourceURL">
	<portlet:param name="<%=Constants.CMD %>" value="<%=ActionKeys.GET_LIFERAY_ROLES %>"/>
</portlet:resourceURL>

<div id="wrapper">
	<h1><liferay-ui:message key="portlet-title"/></h1>
	<h2><liferay-ui:message key="portlet-descrition"/></h2>
	<h4><liferay-ui:message key="portlet-search-title"/></h4>
	<div id="myAutoComplete"></div>
</div>

<aui:script use="aui-autocomplete">

var dataSource = new A.DataSource.IO(
    {
        source: '<%=ajaxResourceURL %>'
    }
);

var autocomplete = new A.AutoComplete(
    {
        dataSource: dataSource,
        delimChar: '',
        contentBox: '#myAutoComplete',
        matchKey: 'name',
        schema: {
            resultListLocator: 'response',
            resultFields: ['key','name','description', 'type']
        },
        uniqueName:'keyword',
        schemaType:'json',
        typeAhead: true,
        cssClass: 'ac_input'
    });

autocomplete.generateRequest = function(query) {
    return {
        request: '&<%=DisplayTerms.KEYWORDS %>=' + query
    };
}

autocomplete.render();

</aui:script>