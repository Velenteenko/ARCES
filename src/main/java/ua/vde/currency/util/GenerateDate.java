package ua.vde.currency.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateDate {

  private static DateFormat dfOut = new SimpleDateFormat("yyyyMMdd");
  private static DateFormat dfIn = new SimpleDateFormat("ddMMyyyy");

  // private static long interval = 24 * 1000 * 60 * 60; // 1 hour in milliseconds

  public static List<String> generateDatesByPeriod(String from, String to) {

    List<String> datesOut = new ArrayList<String>();

    try {
      Date startDate = dfIn.parse(from);
      Date endDate = dfIn.parse(to);

      for (Date date : getListOfDates(startDate, endDate)) {
        datesOut.add(dfOut.format(date));
      }
    }
    catch (ParseException e) {
      e.printStackTrace();
    }

    return datesOut;
  }

  public static Map<String, String> getPeriodOfDates() {

    Map<String, String> map = new HashMap<String, String>();

    Date newDateNow = new Date();

    String nowDate = dfIn.format(newDateNow);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(newDateNow);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    String firstDateOfMonth = dfIn.format(calendar.getTime());

    map.put("from", firstDateOfMonth);
    map.put("to", nowDate);

    return map;
  }

  private static List<Date> getListOfDates(Date startDate, Date endDate) {
    boolean flagFirst = false;
    List<Date> dates = new ArrayList<Date>();

    Calendar cal = Calendar.getInstance();
    cal.setTime(startDate);
    while (cal.getTime().before(endDate)) {
      if (!flagFirst) {
        dates.add(cal.getTime());
        flagFirst = true;
      }
      cal.add(Calendar.DATE, 1);
      dates.add(cal.getTime());
    }

    if (dates.size() == 0 && (startDate.equals(endDate))) {
      dates.add(startDate);
      return dates;
    }

    return dates;
  }

}
