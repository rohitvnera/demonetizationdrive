package com.ampdev.platform.framework.dataaccess.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4013528320937607847L;

	public HibernateAwareObjectMapper()
	{
		Hibernate4Module hbm = new Hibernate4Module();

		this.registerModule(hbm);

	}

	@Override
	public void writeValue(JsonGenerator jgen, Object value) throws IOException, JsonGenerationException, JsonMappingException
	{
		try
		{
			if (value != null)
				jgen.writeString(String.valueOf(value));
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}

	}

}
