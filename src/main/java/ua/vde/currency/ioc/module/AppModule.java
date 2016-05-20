package ua.vde.currency.ioc.module;

import ua.vde.currency.ifaces.IPropertyBank;

import com.google.inject.Inject;

public class AppModule {

  private IPropertyBank propertyBank;

  public String getHost() {
    return propertyBank.getHost();
  }

  public String getValcode() {
    return propertyBank.getValcode();
  }

  public String getDateFormat() {
    return propertyBank.getDateFormat();
  }

  public String getPathParam_Date() {
    return propertyBank.getPathParam_Date();
  }

  public String getPathParam_Json() {
    return propertyBank.getPathParam_Json();
  }

  public String getDateFormat_Service() {
    return propertyBank.getDateFormat_Service();
  }

  public String getProcessRequestValCode() {
    return propertyBank.getProcessRequestValCode();
  }

  // -----inject part-----------------
  @Inject
  public void setPropertyBank(IPropertyBank iPropertyBank) {
    this.propertyBank = iPropertyBank;
  }

}
