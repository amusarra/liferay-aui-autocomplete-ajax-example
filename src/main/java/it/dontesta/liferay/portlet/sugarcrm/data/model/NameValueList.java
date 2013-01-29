/**
 * 
 */
package it.dontesta.liferay.portlet.sugarcrm.data.model;

/**
 * @author amusarra
 *
 */
public class NameValueList {
	protected NameValue id;
	protected NameValue name;
	protected NameValue description;
	protected NameValue account_type;
	/**
	 * @return the id
	 */
	public NameValue getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(NameValue id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public NameValue getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(NameValue name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public NameValue getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(NameValue description) {
		this.description = description;
	}
	/**
	 * @return the account_type
	 */
	public NameValue getAccount_type() {
		return account_type;
	}
	/**
	 * @param account_type the account_type to set
	 */
	public void setAccount_type(NameValue account_type) {
		this.account_type = account_type;
	}

}
