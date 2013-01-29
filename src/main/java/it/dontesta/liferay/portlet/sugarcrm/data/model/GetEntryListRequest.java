/**
 * 
 */
package it.dontesta.liferay.portlet.sugarcrm.data.model;

import java.util.List;

/**
 * @author amusarra
 *
 */
public class GetEntryListRequest {
	/**
	 * @return the session
	 */
	public String getSession() {
		return session;
	}
	/**
	 * @param session the session to set
	 */
	public void setSession(String session) {
		this.session = session;
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
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * @return the order_by
	 */
	public String getOrder_by() {
		return order_by;
	}
	/**
	 * @param order_by the order_by to set
	 */
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * @return the select_fields
	 */
	public List<String> getSelect_fields() {
		return select_fields;
	}
	/**
	 * @param select_fields the select_fields to set
	 */
	public void setSelect_fields(List<String> select_fields) {
		this.select_fields = select_fields;
	}
	/**
	 * @return the link_name_to_fields_array
	 */
	public List<Object> getLink_name_to_fields_array() {
		return link_name_to_fields_array;
	}
	/**
	 * @param link_name_to_fields_array the link_name_to_fields_array to set
	 */
	public void setLink_name_to_fields_array(List<Object> link_name_to_fields_array) {
		this.link_name_to_fields_array = link_name_to_fields_array;
	}
	/**
	 * @return the max_results
	 */
	public int getMax_results() {
		return max_results;
	}
	/**
	 * @param max_results the max_results to set
	 */
	public void setMax_results(int max_results) {
		this.max_results = max_results;
	}
	/**
	 * @return the delete
	 */
	public int getDelete() {
		return delete;
	}
	/**
	 * @param delete the delete to set
	 */
	public void setDelete(int delete) {
		this.delete = delete;
	}
	
	private String session;
	private String module_name;
	private String query;
	private String order_by;
	private int offset;
	private List<String> select_fields;
	private List<Object> link_name_to_fields_array;
	private int max_results;
	private int delete;
	
}
