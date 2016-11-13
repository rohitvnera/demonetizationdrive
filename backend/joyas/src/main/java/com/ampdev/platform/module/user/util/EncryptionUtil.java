package com.ampdev.platform.module.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptionUtil
{

	@Autowired
	public PasswordEncoder passwordEncoder;

	public EncryptionUtil(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}

	public PasswordEncoder getPasswordEncoder()
	{
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}

	public String encodePassword(String password)
	{
		return passwordEncoder.encode(password);
	}

	public boolean matchPassword(String rawPassword, String encryptedPassword)
	{
		return passwordEncoder.matches(rawPassword, encryptedPassword);
	}
}
