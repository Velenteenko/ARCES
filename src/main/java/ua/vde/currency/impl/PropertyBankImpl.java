package ua.vde.currency.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ua.vde.currency.ifaces.IPropertyBank;

public class PropertyBankImpl implements IPropertyBank {

  private InputStream inputStream;
  private Properties properties;

  public PropertyBankImpl() throws IOException {
    getPropValues();
  }

  private void getPropValues() throws IOException {

    try {
      properties = new Properties();
      String propFile = "bankapi.properties";

      inputStream = getClass().getClassLoader().getResourceAsStream(propFile);

      if (inputStream != null) {
        properties.load(inputStream);
      }
      else {
        throw new FileNotFoundException("Property file " + propFile + " not found!");
      }

    }
    catch (Exception e) {
      System.out.println("Exception: " + e);
    }
    finally {
      inputStream.close();
    }
  }

  @Override
  public String getHost() {
    return properties.getProperty("host.bank.ua");
  }

  @Override
  public String getValcode() {
    return properties.getProperty("host.valcode");
  }

  @Override
  public String getDateFormat() {
    return properties.getProperty("process.request.formatter.fromto");
  }

  @Override
  public String getPathParam_Date() {
    return properties.getProperty("host.date");
  }

  @Override
  public String getPathParam_Json() {
    return properties.getProperty("host.json");
  }

  @Override
  public String getDateFormat_Service() {
    return properties.getProperty("host.valcode");
  }

  @Override
  public String getProcessRequestValCode() {
    return properties.getProperty("process.request.valcode");
  }

}
