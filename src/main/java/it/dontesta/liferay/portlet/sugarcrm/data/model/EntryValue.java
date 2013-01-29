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
public class EntryValue {
    protected String id;
    protected String module_name;
    protected NameValueList name_value_list;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

	/**
	 * @return the module_name
	 */
	public String getModule_name() {
		return module_name;
	}

	/**
	 * @param module_name the module_name to set
	 */
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	/**
	 * @return the name_value_list
	 */
	public NameValueList getName_value_list() {
		return name_value_list;
	}

	/**
	 * @param name_value_list the name_value_list to set
	 */
	public void setName_value_list(NameValueList name_value_list) {
		this.name_value_list = name_value_list;
	}


}
