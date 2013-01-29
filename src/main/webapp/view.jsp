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

<%@page import="com.liferay.util.portlet.PortletProps"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
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
<%
String viewAccountRecordURL = PortletProps.get("sugarcrm.ViewAccountRecord");
%>
<portlet:resourceURL var="ajaxResourceURL">
	<portlet:param name="<%=Constants.CMD %>" value="<%=ActionKeys.GET_LIFERAY_ROLES %>"/>
</portlet:resourceURL>

<portlet:resourceURL var="ajaxResourceURLSugarCRMAccountList">
	<portlet:param name="<%=Constants.CMD %>" value="<%=ActionKeys.GET_SUGARCRM_ACCOUNT_LIST %>"/>
</portlet:resourceURL>

<div id="wrapper">
	<h1><liferay-ui:message key="portlet-title"/></h1>
	<h2><liferay-ui:message key="portlet-descrition"/></h2>
	<h4><liferay-ui:message key="portlet-search-title"/></h4>
	<div id="myAutoComplete"></div>
	<h2><liferay-ui:message key="portlet-descrition-2"/></h2>
	<h4><liferay-ui:message key="portlet-search-account-list"/></h4>
	<div id="myAutoCompleteAccountListSugarCRM"></div>
	<div id="selectedAccountItems"></div>
</div>

<aui:script use="aui-autocomplete">

var dataSource = new A.DataSource.IO(
    {
        source: '<%=ajaxResourceURL %>'
    }
);

var autoComplete = new A.AutoComplete(
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

autoComplete.generateRequest = function(query) {
    return {
        request: '&<%=DisplayTerms.KEYWORDS %>=' + query
    };
}

autoComplete.render();

</aui:script>

<aui:script use="aui-autocomplete">

var dataSourceSugarCRM = new A.DataSource.IO(
    {
        source: '<%=ajaxResourceURLSugarCRMAccountList %>'
    }
);

var autoComplete = new A.AutoComplete(
    {
        dataSource: dataSourceSugarCRM,
        delimChar: '',
        contentBox: '#myAutoCompleteAccountListSugarCRM',
        matchKey: 'name',
        schema: {
            resultListLocator: 'response',
            resultFields: ['key','name','description', 'type', 'sessionId']
        },
        uniqueName:'keyword',
        schemaType:'json',
        typeAhead: true,
        cssClass: 'ac_input'
    });

autoComplete.generateRequest = function(query) {
    return {
        request: '&<%=DisplayTerms.KEYWORDS %>=' + query
    };
}

 autoComplete.on('itemSelect', function(event, item) {
                var viewAccountRecordURL = '<%=viewAccountRecordURL %>';
                var viewAccountRecordURL_Id = viewAccountRecordURL.replace('{recordId}', item.key);
                var viewAccountRecordURL_SessionId = viewAccountRecordURL_Id.replace('{sessionId}', item.sessionId);
                
                A.one("#selectedAccountItems").append('<div id="' + item.key + '" class="dialog-link"><li><a title="<liferay-ui:message key="portlet-link-detail-account"/>" class="viewAccountRecord" target="_blank" href="' + viewAccountRecordURL_SessionId + '">' + item.name + '</a></li></div>');
            });
autoComplete.render();

</aui:script>