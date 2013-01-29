/**
 * 
 */
package it.dontesta.liferay.portlet.sugarcrm.data.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author amusarra
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GetEntryListResponse {	
	protected int result_count;
	protected int total_count;
	protected int next_offset;
    protected ArrayList<EntryValue> entry_list;
    protected ArrayList<LinkLists> relationship_list;

	/**
	 * @return the result_count
	 */
	public int getResult_count() {
		return result_count;
	}
	/**
	 * @param result_count the result_count to set
	 */
	public void setResult_count(int result_count) {
		this.result_count = result_count;
	}
	/**
	 * @return the next_offset
	 */
	public int getNext_offset() {
		return next_offset;
	}
	/**
	 * @param next_offset the next_offset to set
	 */
	public void setNext_offset(int next_offset) {
		this.next_offset = next_offset;
	}
	/**
	 * @return the total_count
	 */
	public int getTotal_count() {
		return total_count;
	}
	/**
	 * @param total_count the total_count to set
	 */
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	/**
	 * @return the entry_list
	 */
	public ArrayList<EntryValue> getEntry_list() {
		return entry_list;
	}
	/**
	 * @param entry_list the entry_list to set
	 */
	public void setEntry_list(ArrayList<EntryValue> entry_list) {
		this.entry_list = entry_list;
	}
	/**
	 * @return the relationship_list
	 */
	public ArrayList<LinkLists> getRelationship_list() {
		return relationship_list;
	}
	/**
	 * @param relationship_list the relationship_list to set
	 */
	public void setRelationship_list(ArrayList<LinkLists> relationship_list) {
		this.relationship_list = relationship_list;
	}
}
