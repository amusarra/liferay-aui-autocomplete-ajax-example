AUI().ready(
	/*
	This function gets loaded when all the HTML, not including the portlets, is
	loaded.
	*/
	function() {

		var list = AUI().all(".dialog-link li a");
		
		list.on("click", function(event) {
			event.preventDefault();
			var uri = event.target.getAttribute("href");
			crmAccountOpenDialogiFrame(uri);
		});
	}
);

Liferay.provide(window, 'crmAccountOpenDialogiFrame', function(contentUri) {
	Liferay.Util.openWindow({
		dialog : {
			width : 800,
			height : 600,
			centered : true,
			modal : true,
			resizable: false,
			cssClass: 'crmAccountOpenDialogiFrame',
		},
		title : '',
		uri : contentUri
	});
});

Liferay.Portlet.ready(

	/*
	This function gets loaded after each and every portlet on the page.

	portletId: the current portlet's id
	node: the Alloy Node object of the current portlet
	*/

	function(portletId, node) {
	}
);

Liferay.on(
	'allPortletsReady',
	/*
	This function gets loaded when everything, including the portlets, is on
	the page.
	*/

	function() {
	}
);