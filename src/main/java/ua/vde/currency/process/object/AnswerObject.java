package ua.vde.currency.process.object;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnswerObject {

  private String dateOfRequest;
  private String currencyCode;
  private double average;

  public AnswerObject(String dateOfRequest, String currencyCode, double averageCurrencyValue) {
    this.dateOfRequest = dateOfRequest;
    this.currencyCode = currencyCode;
    this.average = averageCurrencyValue;
  }

  public String getDateOfRequest() {
    return dateOfRequest;
  }

  public void setDateOfRequest(String dateOfRequest) {
    this.dateOfRequest = dateOfRequest;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String cc) {
    this.currencyCode = cc;
  }

  public double getAverage() {
    return average;
  }

  public void setAverage(double averageCurrencyValue) {
    this.average = averageCurrencyValue;
  }

  @Override
  public String toString() {
    return "AnswerObject [dateOfRequest=" + dateOfRequest + ", currencyCode=" + currencyCode + ", averageCurrencyValue=" + average + "]";
  }

}
