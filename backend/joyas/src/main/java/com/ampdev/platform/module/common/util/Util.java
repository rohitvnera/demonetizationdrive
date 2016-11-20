package com.ampdev.platform.module.common.util;

import com.ampdev.platform.framework.clock.ClockInstance;
import com.ampdev.platform.module.findbank.dataobject.BankStatus;
import org.joda.time.DateTimeConstants;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Util {

    public static final String YEAR_MONTH_DATE = "yyyyMMdd";
    public static final String DATE_MONTH_YEAR = "dd MMM yyyy";
    public static final String COMMA = ",";
    private static final String TIMESTAMP_FORMAT = "0000-00-00 00:00:00";
    /**
     * TODO this static block is only for testing purpose. Need to remove this block after getting country data from DB
     */
    private static Map<String, Integer> countryMap = new HashMap<>();

    static {
        countryMap.put("India", 1);
        countryMap.put("United States", 2);
    }

    /**
     * Remove new line character from the string, certain attributes like activity subject as shown in CL, but if there is new
     * line character then UI may hung so replace them.
     *
     * @param str : string from which new line character has to be replaced
     * @return string from which new line character are replaced
     */
    public static String removeNewLineCharacters(String str) {
        if (str != null)
            return str.replaceAll("[\r\n]+", " "); // Remove new line characters
        else
            return str;
    }

    public static boolean isEmpty(String userName) {
        if (userName == null || "".equals(userName.trim()))
            return true;

        return false;
    }

    public static <T> boolean isEmpty(Collection<T> cols) {
        return (cols == null || cols.isEmpty());
    }

    public static Set<Long> getLongList(String input, String separator) {
        if (input == null || separator == null)
            return Collections.emptySet();

        String[] tokens = input.split(Pattern.quote(separator));
        Set<Long> longSet = new HashSet<Long>(tokens.length);

        // Process each token
        for (String token : tokens) {
            if (token.trim().length() > 0) {
                longSet.add(getLongValue(token.trim()));
            }
        }

        return longSet;
    }

    public static int getIntValue(Object object) {
        int retValue;

        if (object == null)
            return 0;

        if (object instanceof String && ((String) object).equalsIgnoreCase("null"))
            return 0;

        if (object instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) object;
            retValue = bigDecimal.intValue();
        } else if (object instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) object;
            retValue = bigInteger.intValue();
        } else if (object instanceof Long) {
            Long longObject = (Long) object;
            retValue = longObject.intValue();
        } else if (object instanceof Integer) {
            Integer integerObject = (Integer) object;
            retValue = integerObject.intValue();
        } else if (object instanceof Double) {
            Double doubleObject = (Double) object;
            retValue = doubleObject.intValue();
        } else if (object instanceof Float) {
            Float floatObject = (Float) object;
            retValue = floatObject.intValue();
        } else if (object instanceof String) {
            try {
                retValue = Integer.parseInt((String) object);
            } catch (NumberFormatException e) {
                // Not a valid string representation of a long number; default
                // to 0
                retValue = 0;
            }
        } else {
            Short shortObject = (Short) object;
            retValue = shortObject.intValue();
        }
        return retValue;
    }

    public static long getLongValue(Object object) {
        long retValue;

        if (object == null)
            return 0;

        if (object instanceof String && ((String) object).equalsIgnoreCase("null"))
            return 0;

        if (object instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) object;
            retValue = bigDecimal.longValue();
        } else if (object instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) object;
            retValue = bigInteger.longValue();
        } else if (object instanceof Long) {
            Long longObject = (Long) object;
            retValue = longObject.longValue();
        } else if (object instanceof Integer) {
            Integer integerObject = (Integer) object;
            retValue = integerObject.longValue();
        } else if (object instanceof Double) {
            Double doubleObject = (Double) object;
            retValue = doubleObject.longValue();
        } else if (object instanceof Float) {
            Float floatObject = (Float) object;
            retValue = floatObject.longValue();
        } else if (object instanceof String) {
            try {
                retValue = Long.parseLong((String) object);
            } catch (NumberFormatException e) {
                // Not a valid string representation of a long number; default
                // to 0
                retValue = 0;
            }
        } else {
            Short shortObject = (Short) object;
            retValue = shortObject.longValue();
        }
        return retValue;
    }

    public static String getStringValue(Object attribute) {
        if (attribute == null || !(attribute instanceof String))
            return null;

        return String.valueOf(attribute);
    }

    public static Set<String> getStringList(String input, String separator) {
        if (input == null || separator == null)
            return Collections.emptySet();

        String[] tokens = input.split(Pattern.quote(separator));
        Set<String> longSet = new HashSet<>(tokens.length);

        // Process each token
        for (String token : tokens) {
            if (token.trim().length() > 0) {
                longSet.add(getStringValue(token.trim()));
            }
        }

        return longSet;
    }

    /**
     * This method reads text file and returns String containing the content.
     *
     * @param filePath absolute path of file
     * @return String, as the contents of the file.
     * @throws IOException
     */
    public static List<String> readAsciiFileAsString(String filePath) throws IOException {

        List<String> newLines = new ArrayList<>();
        // Read the file content in a Buffered reader
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            // Read line by line and append the lines to the StringBuffer
            String line = null;
            while ((line = in.readLine()) != null) {
                newLines.add(line);
            }

            // Close the BufferedReader
            in.close();
        }

        return newLines;
    }

    /**
     * This method reads text file and returns String containing the content.
     * <p>
     * absolute path of file
     *
     * @return String, as the contents of the file.
     * @throws IOException
     */
    public static List<String> readAsciiFileAsString(File file) throws IOException {

        List<String> newLines = new ArrayList<>();
        // Read the file content in a Buffered reader
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            // Read line by line and append the lines to the StringBuffer
            String line = null;
            while ((line = in.readLine()) != null) {
                newLines.add(line);
            }

            // Close the BufferedReader
            in.close();
        }

        return newLines;
    }

    public static long getDateMilli(String date) {
        return getDateMilli(date, TIMESTAMP_FORMAT);
    }

    public static long getDateMilli(String dateInString, String format) {
        if (isEmpty(format)) {
            format = YEAR_MONTH_DATE;
        }

        long milliSeconds = -1l;
        if (!Util.isEmpty(dateInString)) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);

            try {

                Date date = formatter.parse(dateInString);
                milliSeconds = date.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return milliSeconds;
    }

    public static byte[] getImage(String imageURL) {
        byte[] bytes = null;

        URL url = null;
        try {
            url = new URL(imageURL);
            try (InputStream in = new BufferedInputStream(url.openStream())) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                bytes = out.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public final static String saveImage(String path, byte[] bytes) {

        String filepath = null;
        if (bytes != null) {
            try {

                File file = new File(path);
                if (file.createNewFile()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = new ByteArrayInputStream(bytes);
                int downloadedSize = 0;
                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.close();
                filepath = file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filepath;
    }

    public static String getFormattedMessage(String msgStr, Object dynamicValues[], boolean escapeHTML) {
        String retString = "";
        try {
            msgStr = msgStr.replaceAll("'", "''");
            msgStr = msgStr.replaceAll("\\''", "''");
            MessageFormat msgFormat = new MessageFormat(msgStr);
            if (escapeHTML)
                retString = msgFormat.format(dynamicValues);
            else
                retString = msgFormat.format(formatTextContent(dynamicValues));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retString;
    }

    public static Object[] formatTextContent(Object dynamicValues[]) {
        for (int i = 0; i < dynamicValues.length; i++) {
            if (dynamicValues[i] instanceof java.lang.String) {
                String strVal = (String) dynamicValues[i];
                if (strVal.indexOf("<") != -1 || strVal.indexOf(">") != -1) {
                    strVal = strVal.replaceAll("<", "&lt");
                    strVal = strVal.replaceAll(">", "&gt");
                    dynamicValues[i] = strVal;
                }
            }
        }
        return dynamicValues;
    }

    public static String getCommaSeperatedString(Collection<String> list) {
        String retVal = "";
        if (isEmpty(list)) {
            return retVal;
        }

        StringBuilder sb = new StringBuilder();
        for (String val : list) {
            sb.append(val).append(COMMA);
        }

        if (sb.toString().length() > 0)
            retVal = sb.toString().substring(0, sb.toString().length() - 1);
        return retVal;
    }

    public static List<String> getFileTrimmedLines(String absolutePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(absolutePath), StandardCharsets.UTF_8);
        List<String> retVal = new ArrayList<>();
        if (isEmpty(lines)) {
            return retVal;
        }

        for (String line : lines) {
            if (isEmpty(line)) {
                continue;
            }

            retVal.add(line.trim());
        }
        return retVal;
    }

    public static String getUniqueCommaSeperated(String value) {
        String retVal = "";
        if (isEmpty(value)) {
            return retVal;
        }
        Set<String> set = getStringList(value, COMMA);
        retVal = getCommaSeperatedString(set);
        return retVal;
    }


    /**
     * If last avg time greater than 0 and last modified is in within 6 hours then take average otherwise set absolute number
     *
     * @param bankStatusDB
     * @return
     */
    public static boolean takeAvg(BankStatus bankStatusDB) {
        if (bankStatusDB.getAvgWaitTime() > 0) {
            long currentTimeMillis = ClockInstance.getInstance().getCurrentTimeMillis();
            long lastModified = bankStatusDB.getWhenModified().getTime();
            if (currentTimeMillis - lastModified <= DateTimeConstants.MILLIS_PER_HOUR * 6) {
                return true;
            }
        }
        return false;
    }
}
