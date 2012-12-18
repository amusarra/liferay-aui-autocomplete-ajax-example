/**
 * 
 */
package it.dontesta.liferay.portlet;

import it.dontesta.liferay.portlet.util.ActionKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author amusarra
 *
 */
public class ViewAUIAutocompleteAjax extends MVCPortlet {

	/* (non-Javadoc)
	 * @see com.liferay.util.bridges.mvc.MVCPortlet#serveResource(javax.portlet.ResourceRequest, javax.portlet.ResourceResponse)
	 */
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		
		if (_log.isInfoEnabled()) {
			_log.info("Call " + "it.dontesta.liferay.portlet.ViewAUIAutocompleteAjax.serveResource(ResourceRequest, ResourceResponse)");
		}
		
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONArray results = JSONFactoryUtil.createJSONArray();
		json.put("response", results);
		
		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);
		String keyword = ParamUtil.getString(resourceRequest, DisplayTerms.KEYWORDS);
		String searchPattern = keyword.replace("*", "%");
		
		if (cmd.equals(ActionKeys.GET_LIFERAY_ROLES)) {
			if (_log.isInfoEnabled()) {
				_log.info("Excute a " + ActionKeys.GET_LIFERAY_ROLES + " action for keyword " + keyword);
			}
			
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Role.class).
					add(PropertyFactoryUtil.forName("name").like(searchPattern));
			try {
				@SuppressWarnings("unchecked")
				List<Role> roles = RoleLocalServiceUtil.dynamicQuery(dynamicQuery);
				for (Role role : roles) {
					JSONObject listEntry = JSONFactoryUtil.createJSONObject();
					listEntry.put("key", role.getRoleId());
					listEntry.put("name", role.getName());
					listEntry.put("description", role.getDescription());
					listEntry.put("type", role.getType());
					results.put(listEntry);
				}
			} catch (Exception e) {
				if (_log.isErrorEnabled()) {
					_log.error(e.getMessage());
				}
			}
		}
		writeJSON(resourceRequest, resourceResponse, json);
	}

	private static Log _log = LogFactoryUtil.getLog(ViewAUIAutocompleteAjax.class);
}
