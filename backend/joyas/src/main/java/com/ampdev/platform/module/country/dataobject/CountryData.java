package com.ampdev.platform.module.country.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.country.constants.CountryConstants;

@NamedQueries({ @NamedQuery(name = CountryConstants.QUERY_GET_ALL_COUNTRIES, query = "from CountryData"),
	@NamedQuery(name = CountryConstants.QUERY_GET_COUNTRIES, query = "from CountryData where id in (:ids)"),
	@NamedQuery(name = CountryConstants.QUERY_DELTE_COUNTRY, query = "delete from CountryData where id= :id") })
@Entity
@Table(name = "country")
public class CountryData extends PersistedDataObject
{

	/**
	 * Generated serial version id
	 */
	private static final long serialVersionUID = -7812405339777738148L;

	@Id
	@GeneratedValue
	@Column(name = "country_id")
	private long id;

	@Column(name = "country_name")
	private String countryName;

	public CountryData()
	{
	}

	public CountryData(int id, String countryName)
	{
		this.id = id;
		this.countryName = countryName;
	}

	@Override
	public final long getDataId()
	{
		return id;
	}

	public final String getCountryName()
	{
		return countryName;
	}

	public final void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public final void setId(long id)
	{
		this.id = id;
	}

}
