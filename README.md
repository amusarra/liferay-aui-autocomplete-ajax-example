Alloy UI Autocomplete Ajax Example
=====================================

1. Introduction
---------------------
This repository contains a portlet that shows the use of the component AUI Auto Complete. 
The data shown by the component of Auto Complete, are requested by access to services, 
both local (Liferay) and remote (Rest Services).

The portlet contains two controls Autocomplete, the first shows the roles defined on Liferay,
while the second shows the data from a remote system.

The data shown in the second control AutoComplete, come from a CRM system that is called SugarCRM. 
In particular, the data represent the Account associated with the current user. Access to data is supported 
with Rest services published by the CRM system.

2. Configuration
---------------------
Before you can retrieve the data from the CRM system Account, you must satisfy the following items:

* SugarCRM application address (for example: http://crm.example.com)
* On Liferay there must be a user who has the same credentials on the CRM system.
* Liferay must be configured in such a way as to be stored unencrypted password on the session.

Configuration to be put in webapps/ROOT/WEB-INF/classes/portal-ext.properties
	##
	## Session
	##
    	#
    	# Explicitly exclude attributes that are shared from the portal to portlets.
    	#
    	session.shared.attributes.excludes=

    	#
    	# Set this to true to store the user's password in the session.
    	#
    	session.store.password=true
