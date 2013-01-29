/**
 * 
 */
package it.dontesta.liferay.portlet.sugarcrm.services;

import it.dontesta.liferay.portlet.sugarcrm.data.model.Account;
import it.dontesta.liferay.portlet.sugarcrm.data.model.EntryValue;
import it.dontesta.liferay.portlet.sugarcrm.data.model.GetEntryListResponse;
import it.dontesta.liferay.portlet.sugarcrm.data.model.GetEntryListRequest;
import it.dontesta.liferay.portlet.sugarcrm.data.model.NameValueList;
import it.dontesta.liferay.portlet.sugarcrm.data.model.UserResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * @author amusarra
 *
 */
public class SugarCRMServiceUtil {

	protected static String serviceURL = PortletProps.get("sugarcrm.RestURL");
	protected static Map<String, String> paramsURL = new HashMap<String, String>();
	
	private static Log _log = LogFactoryUtil.getLog(SugarCRMServiceUtil.class);
	
	/**
	 * Execute a login in to SugarCRM 
	 * 
	 * @param username
	 * @param password
	 * @return The SessionId otherwise return an exception.
	 * @throws SystemException
	 */
	public static String login(String username, String password) throws SystemException {
		
		// Prepare a input data for the rest service
		paramsURL.put("method", "login");
		paramsURL.put("input_type", "JSON");
		paramsURL.put("response_type", "JSON");

		JSONObject loginParams = JSONFactoryUtil.createJSONObject();
		JSONObject loginUserAuth = JSONFactoryUtil.createJSONObject();
		
		loginUserAuth.put("user_name", username);
		loginUserAuth.put("password", password);
		loginUserAuth.put("version", 1);

		loginParams.put("user_auth", loginUserAuth);
		
		paramsURL.put("rest_data", JSONFactoryUtil.looseSerialize(loginParams));

		if (_log.isDebugEnabled()) {
			_log.debug("Try login in to SugarCRM for " + username + "...");
			_log.debug("Rest Data to send: " + paramsURL.get("rest_data"));
		}
		
		// Create a new RestTemplate instance
		RestTemplate sugarRestTemplate = new RestTemplate();
		
		// Add the Jackson message converter
		sugarRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		// Make the HTTP GET request, marshaling the response from JSON to an array of Events
		UserResponse user = sugarRestTemplate.getForObject(serviceURL, UserResponse.class, paramsURL);

		return user.getId();
	}
	
	/**
	 * Execute a logout
	 * 
	 * @param sessionId The user sessionId 
	 * @return true if ok otherwise false
	 * @throws SystemException
	 */
	public static boolean logout(String sessionId) throws SystemException {
		// Prepare a input data for the rest service
		paramsURL.put("method", "logout");
		paramsURL.put("input_type", "JSON");
		paramsURL.put("response_type", "JSON");

		JSONObject logoutSession = JSONFactoryUtil.createJSONObject();
		logoutSession.put("session", sessionId);
		
		paramsURL.put("rest_data", JSONFactoryUtil.looseSerialize(logoutSession));

		if (_log.isDebugEnabled()) {
			_log.debug("Try logout from SugarCRM for sessionId: " + sessionId + "...");
			_log.debug("Rest Data to send: " + paramsURL.get("rest_data"));
		}

		// Create a new RestTemplate instance
		RestTemplate sugarRestTemplate = new RestTemplate();
		
		// Add the Jackson message converter
		sugarRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		// Make the HTTP GET request, marshaling the response from JSON to an array of Events
		String logout = sugarRestTemplate.getForObject(serviceURL, String.class, paramsURL);

		if (_log.isDebugEnabled()) {
			_log.debug("Logout: " + logout);
		}
		
		return true;
	}
	
	/**
	 * Get the Account list by name. 
	 * 
	 * @param sessionId The user sessionId
	 * @param keyword
	 * @return
	 * @throws SystemException
	 */
	public static List<Account> getAccountsByeName(String sessionId, String keyword) throws SystemException {
		// Prepare a input data for the rest service
		paramsURL.put("method", "get_entry_list");
		paramsURL.put("input_type", "JSON");
		paramsURL.put("response_type", "JSON");
	
		GetEntryListRequest getEntryList = new GetEntryListRequest();
		getEntryList.setSession(sessionId);
		getEntryList.setModule_name("Accounts");
		getEntryList.setQuery("accounts.name like '" + keyword + "'");
		getEntryList.setOrder_by("accounts.name asc");
		getEntryList.setOffset(0);
		
		List<String> selectFields = new ArrayList<String>();
		selectFields.add("id");
		selectFields.add("name");
		selectFields.add("description");
		selectFields.add("account_type");
		getEntryList.setSelect_fields(selectFields);
		
		getEntryList.setDelete(0);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			paramsURL.put("rest_data", mapper.writeValueAsString(getEntryList));
		} catch (JsonProcessingException e) {
			if (_log.isErrorEnabled()) {
				_log.error(e.getMessage());
			}
		}
		
		if (_log.isDebugEnabled()) {
			_log.debug("Try get account from SugarCRM for sessionId: " + sessionId + "...");
			_log.debug("Rest Data to send: " + paramsURL.get("rest_data"));
		}
		
		List<Account> accountList = new LinkedList<Account>();
		
		// Create a new RestTemplate instance
		RestTemplate sugarRestTemplate = new RestTemplate();
		
		// Add the Jackson message converter
		sugarRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		// Make the HTTP GET request, marshaling the response from JSON to an array of Events
		GetEntryListResponse accountResponse = sugarRestTemplate.getForObject(serviceURL, GetEntryListResponse.class, paramsURL);

		if (_log.isDebugEnabled()) {
			_log.debug("Account Result Count: " + accountResponse.getResult_count());
		}
		
		ArrayList<EntryValue> entryList = accountResponse.getEntry_list();
		
		for (EntryValue entryValue : entryList) {
			NameValueList nameValueList = entryValue.getName_value_list();

			if (_log.isDebugEnabled()) {
				_log.debug("Object Id: " + entryValue.getId());
				_log.debug("Module Name: " + entryValue.getModule_name());
				_log.debug("Name: " + nameValueList.getName().getValue());
			}
			
			Account account = new Account();
			account.setId(nameValueList.getId().getValue());
			account.setName(nameValueList.getName().getValue());
			account.setDescription(nameValueList.getDescription().getValue());
			account.setAccount_type(nameValueList.getAccount_type().getValue());
			
			accountList.add(account);
		}
		
		return accountList;
	}
}
