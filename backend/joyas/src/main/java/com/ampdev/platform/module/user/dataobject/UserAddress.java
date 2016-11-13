package com.ampdev.platform.module.user.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.country.dataobject.CountryData;
import com.ampdev.platform.module.user.constants.UserConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(name = UserConstants.QUERY_GET_USER_ADDRESS, query = "from UserAddress a where a.userId = :userId"),
	@NamedQuery(name = "deleteUserAddress", query = "delete from UserAddress where userId= :userId") })
@Entity
@Table(name = "user_address")
@JsonInclude(Include.NON_NULL)
public class UserAddress extends PersistedDataObject
{

	/**
	 * Generated Serial version id
	 */
	private static final long serialVersionUID = 8924884254177027001L;

	@Id
	@Column(name = "address_id")
	private long id;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "address_line_1")
	private String addressFirstLine;

	@Column(name = "address_line_2")
	private String addressSecondLine;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private CountryData countryData;

	@Column(name = "zip")
	private long zip;

	@Override
	public final long getDataId()
	{
		return id;
	}

	public final void setId(long id)
	{
		this.id = id;
	}

	public final long getUserId()
	{
		return userId;
	}

	public final void setUserId(long userId)
	{
		this.userId = userId;
	}

	public final String getAddressFirstLine()
	{
		return addressFirstLine;
	}

	public final void setAddressFirstLine(String addressFirstLine)
	{
		this.addressFirstLine = addressFirstLine;
	}

	public final String getAddressSecondLine()
	{
		return addressSecondLine;
	}

	public final void setAddressSecondLine(String addressSecondLine)
	{
		this.addressSecondLine = addressSecondLine;
	}

	public final String getCity()
	{
		return city;
	}

	public final void setCity(String city)
	{
		this.city = city;
	}

	public final String getState()
	{
		return state;
	}

	public final void setState(String state)
	{
		this.state = state;
	}

	public final CountryData getCountryData()
	{
		return countryData;
	}

	public final void setCountryData(CountryData countryData)
	{
		this.countryData = countryData;
	}

	public final long getZip()
	{
		return zip;
	}

	public final void setZip(long zip)
	{
		this.zip = zip;
	}

	public void setUserAddress(UserAddress userAddress)
	{
		if (userAddress.getAddressFirstLine() != null)
		{
			setAddressFirstLine(userAddress.getAddressFirstLine());
		}

		if (userAddress.getAddressSecondLine() != null)
		{
			setAddressSecondLine(userAddress.getAddressSecondLine());
		}

		if (userAddress.getCity() != null)
		{
			setCity(userAddress.getCity());
		}

		if (userAddress.getZip() > 0)
		{
			setZip(userAddress.getZip());
		}

		if (userAddress.getCountryData() != null)
		{
			setCountryData(userAddress.getCountryData());
		}

		if (userAddress.getState() != null)
		{
			setState(userAddress.getState());
		}
	}

}
