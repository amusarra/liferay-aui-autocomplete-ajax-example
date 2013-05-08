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

Below is the configuration of the portlet (in portlet.properties)

	sugarcrm.URL=http://localhost:8888/sugarcrm_dev_WebServices
	sugarcrm.ViewAccountRecord=${sugarcrm.URL}/index.php?module=Accounts&action=DetailView&record={recordId}&MSID={sessionId}
	sugarcrm.RestURL=${sugarcrm.URL}/service/v4/rest.php?method={method}&input_type={input_type}&response_type={response_type}&rest_data={rest_data}

Configuration to be put in webapps/ROOT/WEB-INF/classes/portal-ext.properties for store the user's password in 
the session.

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

3. Issue
---------------------
It may happen that the data from the CRM are not shown due to a bug of web services, ie, the content-type 
of answer is wrong, it is not the type "application / json" but "text / html", this causes the exception 
described in the post http://musarra.wordpress.com/2011/04/11/bad-content-type-on-sugarcrm-rest-api-interface/

4. In Action!
---------------------
Below are some screenshots that show the integration between Liferay and SugarCRM.

![AUI Auto Complete Example](http://musarra.files.wordpress.com/2013/01/auiautocompletesugarcrm_11.png)

![AUI Auto Complete Example](http://musarra.files.wordpress.com/2013/01/auiautocompletesugarcrm_21.png)

![AUI Auto Complete Example](http://musarra.files.wordpress.com/2013/01/auiautocompletesugarcrm_31.png)

![AUI Auto Complete Example](http://musarra.files.wordpress.com/2013/01/auiautocompletesugarcrm_41.png)

![AUI Auto Complete Example](http://musarra.files.wordpress.com/2013/01/auiautocompletesugarcrm_51.png)

![AUI Auto Complete Example](http://musarra.files.wordpress.com/2013/01/auiautocompletesugarcrm_61.png)

5. Resources
---------------------
At my Blog:
* Alloy UI AutoComplete Ajax Example (http://musarra.wordpress.com/2012/12/18/alloy-ui-autocomplete-ajax-example/)
* Alloy UI Autocomplete Portlet (http://musarra.wordpress.com/2012/12/27/alloy-ui-autocomplete-portlet/)
* Alloy UI Autocomplete: Get data from SugarCRM (http://musarra.wordpress.com/2013/03/15/alloy-ui-autocomplete-get-data-from-sugarcrm-2/)

6. The End
---------------------
With this little project update I wanted to show how simple and almost immediate integration between Liferay and SugarCRM. 
For more information, please refer to my blog (http://musarra.wordpress.com) or write to the mail box at antonio[dot]musarra[at]gmail.com.
