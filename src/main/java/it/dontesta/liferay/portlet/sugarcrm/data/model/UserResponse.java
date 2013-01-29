/**
 * 
 */
package it.dontesta.liferay.portlet.sugarcrm.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author amusarra
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserResponse {
	private String id;

	/**
	 * @return the User SessionId
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
