package ua.vde.currency.process.object;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnswerObjectByDate extends AnswerObject {

  private String date_from;
  private String date_to;
  private List<String> exclude_dates;

  public AnswerObjectByDate(String dateOfRequest, String currencyCode, double averageCurrencyValue) {
    super(dateOfRequest, currencyCode, averageCurrencyValue);
  }

  public AnswerObjectByDate(String dateOfRequest, String currencyCode, double averageCurrencyValue, String date_from, String date_to, List<String> exclude_dates) {
    super(dateOfRequest, currencyCode, averageCurrencyValue);
    this.date_from = date_from;
    this.date_to = date_to;
    this.exclude_dates = exclude_dates;
  }

  public String getDate_from() {
    return date_from;
  }

  public void setDate_from(String date_from) {
    this.date_from = date_from;
  }

  public String getDate_to() {
    return date_to;
  }

  public void setDate_to(String date_to) {
    this.date_to = date_to;
  }

  public List<String> getExclude_dates() {
    return exclude_dates;
  }

  public void setExclude_dates(List<String> exclude_dates) {
    this.exclude_dates = exclude_dates;
  }

  @Override
  public String toString() {
    return "AnswerObjectByDate [date_from=" + date_from + ", date_to=" + date_to + ", exclude_dates=" + exclude_dates + ", toString()=" + super.toString()
        + "]";
  }

}
