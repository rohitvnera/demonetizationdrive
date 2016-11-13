package com.ampdev.platform.module.common.dataobject;

import java.lang.reflect.Field;
import java.util.Collection;

import com.ampdev.platform.module.common.util.EncoderUtil;
import com.ampdev.platform.module.common.util.Util;

public abstract class PersistedDataObject implements IDataObject
{

	private static final long serialVersionUID = 6981985134710564408L;

	public abstract long getDataId();


	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		toString(this.getClass(), sb);
		return sb.toString();
	}

	/**
	 * Print string version of the object, all data object extending this class should override this method
	 * 
	 * @param clazz
	 * @param sb
	 */
	private void toString(Class<?> clazz, StringBuilder sb)
	{
		Field f[] = clazz.getDeclaredFields();

		for (int i = 0; i < f.length; i++)
		{
			f[i].setAccessible(true);
			try
			{
				Object o = f[i].get(this);
				// Do not follow data objects or collections due to circular dependencies.
				if (!(o instanceof PersistedDataObject || o instanceof Collection))
				{
					sb.append(f[i].getName() + "=" + o + "\n");
				}
			}
			catch (IllegalAccessException e)
			{
				// Do nothing. It is a to string method
			}
		}
		if (clazz.getSuperclass() != null)
		{
			toString(clazz.getSuperclass(), sb);
		}
	}

	/**
	 * Escape certain characters which cause UI hung from the string This will remove new line characters and then encode the
	 * string to be displayed properly in HTML.
	 * 
	 * @param str
	 *            : string from which new line character has to be replaced
	 * @return string from which new line character are replaced
	 */
	protected String escape(String str)
	{
		if (str != null && str.length() > 0)
		{
			str = EncoderUtil.HTMLEncode(Util.removeNewLineCharacters(str));
			return str;
		}
		else
			return "";

	}
}
