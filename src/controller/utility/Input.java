package controller.utility;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Input {
  public static String input(Scanner scan, String cue, Appendable output, boolean console) throws IllegalStateException, IOException {
    if (console) {
      output.append(cue);
    }
    String st = "";
    try {
      st = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Not enough parameters. " + cue);
    }
    return st;
  }

  private static String convertDate(SimpleDateFormat paradigm, SimpleDateFormat format, String date) {
    String res = "";
    try {
      format.setLenient(false);
      Date curDate = format.parse(date);
      date = paradigm.format(curDate);
      res = date;
    } catch (ParseException e) {
      return res;
      // This does nothing.
    }
    return res;
  }

  public static String inputDate(Scanner scan, String cue, Appendable output, boolean console) throws IllegalArgumentException, IOException {
    String date = input(scan, cue, output, console);
    if (date.equals("N") || date.equals("n") || date.equals("Q") || date.equals("q")) {
      return date;
    }
    SimpleDateFormat paradigm = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy:MM:dd");
    SimpleDateFormat format3 = new SimpleDateFormat("yyyy.MM.dd");
    SimpleDateFormat format4 = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat format5 = new SimpleDateFormat("yyyy,MM,dd");

    try {
      paradigm.setLenient(false);
      paradigm.parse(date);
      return date;
    } catch (ParseException e) {
      // This does nothing.
    }

    String res = "";

    res = convertDate(paradigm, format1, date);
    if (!res.equals("")) {
      return res;
    }

    res = convertDate(paradigm, format2, date);
    if (!res.equals("")) {
      return res;
    }

    res = convertDate(paradigm, format3, date);
    if (!res.equals("")) {
      return res;
    }

    res = convertDate(paradigm, format4, date);
    if (!res.equals("")) {
      return res;
    }

    res = convertDate(paradigm, format5, date);
    if (!res.equals("")) {
      return res;
    }

    throw new IllegalArgumentException("Invalid date.");
  }

  /**
   * This method will determine whether to quit or not.
   *
   * @param st the string to be predicated
   * @return whether the string is a quitting message
   */
  public static boolean isQuit(String st) {
    return st.equals("q") || st.equals("Q");
  }
}
