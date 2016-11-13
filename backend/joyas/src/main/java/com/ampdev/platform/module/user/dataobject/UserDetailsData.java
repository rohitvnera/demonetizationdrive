package com.ampdev.platform.module.user.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.country.dataobject.CountryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;

@NamedQueries({ @NamedQuery(name = "getUserDetails", query = "from UserDetailsData where id IN (:ids)"),
	@NamedQuery(name = "deleteUserDetails", query = "delete from UserDetailsData where id= :userId") })
@Entity
@Table(name = "user_details")
@JsonInclude(Include.NON_NULL)
public class UserDetailsData extends PersistedDataObject
{

	/**
	 * Generated Serial version id
	 */
	private static final long serialVersionUID = 608093306320162141L;

	public UserDetailsData()
	{

	}

	@Id
	@Column(name = "user_id")
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "email")
	private String emailAddress;

	@Column(name = "phone")
	private String phoneNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private CountryData country;

	@Transient
	private String userName;

	@Transient
	private String password;

	@Transient
	private String countryName;

	@Override
	public final long getDataId()
	{
		return id;
	}

	public final String getFirstName()
	{
		return firstName;
	}

	public final void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public final String getLastName()
	{
		return lastName;
	}

	public final void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public final String getMiddleName()
	{
		return middleName;
	}

	public final void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public final String getEmailAddress()
	{
		return emailAddress;
	}

	public final void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public final String getPhoneNumber()
	{
		return phoneNumber;
	}

	public final void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public final CountryData getCountry()
	{
		return country;
	}

	public final void setCountry(CountryData country)
	{
		this.country = country;
	}

	public final void setId(long id)
	{
		this.id = id;
	}

	public final String getUserName()
	{
		return userName;
	}

	public final void setUserName(String userName)
	{
		this.userName = userName;
	}

	public final String getPassword()
	{
		return password;
	}

	public final void setPassword(String passWord)
	{
		this.password = passWord;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public void setUserDetails(UserDetailsData userDetailsData)
	{
		if (userDetailsData.getFirstName() != null)
		{
			setFirstName(userDetailsData.getFirstName());
		}

		if (userDetailsData.getLastName() != null)
		{
			setLastName(userDetailsData.getLastName());
		}

		if (userDetailsData.getMiddleName() != null)
		{
			setMiddleName(userDetailsData.getMiddleName());
		}

		if (userDetailsData.getEmailAddress() != null)
		{
			setEmailAddress(userDetailsData.getEmailAddress());
		}

		if (userDetailsData.getPhoneNumber() != null)
		{
			setPhoneNumber(userDetailsData.getPhoneNumber());
		}

		if (userDetailsData.getCountry() != null)
		{
			setCountry(userDetailsData.getCountry());
		}
	}

}
