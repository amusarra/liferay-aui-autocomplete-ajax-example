/**
 * 
 */
package it.dontesta.liferay.portlet;

import it.dontesta.liferay.portlet.util.ActionKeys;

import java.io.IOException;
import java.util.List;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Role;

import it.dontesta.liferay.portlet.sugarcrm.data.model.Account;
import it.dontesta.liferay.portlet.sugarcrm.services.SugarCRMServiceUtil;

import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
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
		
		com.liferay.portal.model.User lfrUser = null;
		try {
			lfrUser = PortalUtil.getUser(resourceRequest);
		} catch (PortalException e1) {
			if (_log.isErrorEnabled()) {
				_log.error(e1.getMessage());
			}
		} catch (SystemException e1) {
			if (_log.isErrorEnabled()) {
				_log.error(e1.getMessage());
			}
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
			
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Role.class, PortalClassLoaderUtil.getClassLoader()).
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
		
		if (cmd.equals(ActionKeys.GET_SUGARCRM_ACCOUNT_LIST)) {
			if (_log.isInfoEnabled()) {
				_log.info("Excute a " + ActionKeys.GET_SUGARCRM_ACCOUNT_LIST + " action for keyword " + keyword);
			}
			
			try {
				// Get a clear user password
				String clearPassword = null;
				String hashedClearPassword = null;
				if (Validator.isNull(lfrUser.getPasswordUnencrypted())) {
					clearPassword = PortalUtil.getUserPassword(resourceRequest);
				} else {
					clearPassword = lfrUser.getPasswordUnencrypted();
				}
				hashedClearPassword = DigesterUtil.digestHex("MD5", clearPassword);

				if (_log.isDebugEnabled()) {
					_log.debug("Your Unencrypted Password: " + clearPassword);
					_log.debug("Your Hashed Password: " + hashedClearPassword);
				}
				// Get SugarCRM sessionId
				String sessiondId = SugarCRMServiceUtil.login(lfrUser.getScreenName(), hashedClearPassword);
				if (Validator.isNotNull(sessiondId)) {
					if (_log.isInfoEnabled()) {
						_log.debug("Your SugarCRM SessionId is: " + sessiondId);
					}
					// Get SugarCRM Account list
					List<Account> accountList = SugarCRMServiceUtil.getAccountsByeName(sessiondId, searchPattern + "%");
					for (Account account : accountList) {
						JSONObject listEntry = JSONFactoryUtil.createJSONObject();
						listEntry.put("key", account.getId());
						listEntry.put("name", account.getName());
						listEntry.put("description", account.getDescription());
						listEntry.put("type", account.getAccount_type());
						listEntry.put("sessionId", sessiondId);
						results.put(listEntry);
					}
					// Logout session from SugarCRM
					//SugarCRMServiceUtil.logout(sessiondId);
				} else {
					if (_log.isWarnEnabled()) {
						_log.warn("Login failed in to SugarCRM for " + lfrUser.getScreenName());
					}
				}
			} catch (SystemException e) {
				if (_log.isErrorEnabled()) {
					_log.error(e.getMessage());
				}
			}
		}
		
		writeJSON(resourceRequest, resourceResponse, json);
	}

	private static Log _log = LogFactoryUtil.getLog(ViewAUIAutocompleteAjax.class);
}
