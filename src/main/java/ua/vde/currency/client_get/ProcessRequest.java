package ua.vde.currency.client_get;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import ua.vde.currency.factory.AppModulePropFactory;
import ua.vde.currency.ioc.module.AppModule;
import ua.vde.currency.process.object.AnswerObject;
import ua.vde.currency.process.object.AnswerObjectByDate;
import ua.vde.currency.process.object.RequestObject;

import com.google.gson.Gson;

public class ProcessRequest implements IParseAnswer {

  private String requestPath;
  private AppModule propModule;

  public ProcessRequest(String requestPath) {
    this.requestPath = requestPath;
    this.propModule = AppModulePropFactory.getInstance();
  }

  public AnswerObject prepareAnswerObject(String currencyCode, List<String> formatedDate) {

    double currency = 0.0;
    Integer counter = 0;
    String[] splitQuery = requestPath.split(propModule.getProcessRequestValCode());
    String request;

    for (String nextQueryDate : formatedDate) {

      request = new String();
      request += splitQuery[0] + propModule.getValcode() + currencyCode + propModule.getPathParam_Date() + nextQueryDate + propModule.getPathParam_Json();
      ResteasyClient client = new ResteasyClientBuilder().build();
      ResteasyWebTarget target = client.target(request);
      String value = target.request().get().readEntity(String.class);

      Gson gson = new Gson();
      RequestObject[] requestObjectsList = gson.fromJson(value, RequestObject[].class);

      for (RequestObject requestObjectType : requestObjectsList) {
        currency += requestObjectType.getRate();
      }
      ++counter;
    }

    // average
    currency = currency / counter;

    return new AnswerObject(new SimpleDateFormat("dd.MM.YYYY").format(new Date()), currencyCode, currency);
  }

  @Override
  public AnswerObjectByDate prepareAnswerObjectByDate(String currencyCode, List<String> formatedDate) throws InvocationTargetException {

    double currency = 0.0;
    Integer counter = 0;
    String[] splitQuery = requestPath.split(propModule.getProcessRequestValCode());
    String request;
    List<String> excludeDate = new ArrayList<String>();

    for (String nextQueryDate : formatedDate) {

      request = new String();
      request += splitQuery[0] + propModule.getValcode() + currencyCode + propModule.getPathParam_Date() + nextQueryDate + propModule.getPathParam_Json();
      ResteasyClient client = new ResteasyClientBuilder().build();
      ResteasyWebTarget target = client.target(request);
      String value = target.request().get().readEntity(String.class);

      Gson gson = new Gson();
      RequestObject[] requestObjectsList = null;
      try {
        requestObjectsList = gson.fromJson(value, RequestObject[].class);
      }
      catch (Exception ex) {
        if (counter != 0) {
          counter--;
        }
        else {
          counter = 1;
        }
        excludeDate.add(nextQueryDate);
        continue;
      }

      for (RequestObject requestObjectType : requestObjectsList) {
        currency += requestObjectType.getRate();
      }
      ++counter;
    }

    if (excludeDate.size() == 0) {
      excludeDate.add("0");
    }
    else {
      List<String> formatedExcludeDate = new ArrayList<String>();
      for (String excludeDateCurr : excludeDate) {
        try {
          Date exDate = new SimpleDateFormat("yyyyMMdd").parse(excludeDateCurr);
          formatedExcludeDate.add(new SimpleDateFormat("dd.MM.yyyy").format(exDate));
        }
        catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      excludeDate.clear();
      excludeDate.addAll(formatedExcludeDate);
    }

    // average
    currency = currency / counter;

    Date datefrom;
    Date dateto;

    if (formatedDate.size() > 1) {
      try {
        datefrom = new SimpleDateFormat("yyyyMMdd").parse(formatedDate.get(0));
        dateto = new SimpleDateFormat("yyyyMMdd").parse(formatedDate.get(formatedDate.size() - 1));
      }
      catch (ParseException e) {
        datefrom = new Date();
        dateto = new Date();
        return new AnswerObjectByDate(new SimpleDateFormat("dd.MM.yyyy").format(new Date()), currencyCode, currency,
            new SimpleDateFormat("dd.MM.yyyy").format(datefrom), new SimpleDateFormat("dd.MM.yyyy").format(dateto), excludeDate);
      }

      return new AnswerObjectByDate(new SimpleDateFormat("dd.MM.yyyy").format(new Date()), currencyCode, currency,
          new SimpleDateFormat("dd.MM.yyyy").format(datefrom), new SimpleDateFormat("dd.MM.yyyy").format(dateto), excludeDate);
    }
    else {
      try {
        datefrom = new SimpleDateFormat("yyyyMMdd").parse(formatedDate.get(0));
      }
      catch (ParseException e) {
        datefrom = new Date();
        return new AnswerObjectByDate(new SimpleDateFormat("dd.MM.yyyy").format(new Date()), currencyCode, currency,
            new SimpleDateFormat("dd.MM.yyyy").format(datefrom), new SimpleDateFormat("dd.MM.yyyy").format(datefrom), excludeDate);
      }
      return new AnswerObjectByDate(new SimpleDateFormat("dd.MM.yyyy").format(new Date()), currencyCode, currency,
          new SimpleDateFormat("dd.MM.yyyy").format(datefrom), new SimpleDateFormat("dd.MM.yyyy").format(datefrom), excludeDate);
    }
  }

}
