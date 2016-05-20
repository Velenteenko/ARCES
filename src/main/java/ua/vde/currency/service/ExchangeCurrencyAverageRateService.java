package ua.vde.currency.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ua.vde.currency.client_get.ProcessRequest;
import ua.vde.currency.factory.AppModulePropFactory;
import ua.vde.currency.ioc.module.AppModule;
import ua.vde.currency.process.object.AnswerObjectByDate;
import ua.vde.currency.util.GenerateDate;

@Path("/currencyRate")
public class ExchangeCurrencyAverageRateService {

  // private IPropertyBank propertyBank;
  AppModule module;

  public ExchangeCurrencyAverageRateService() {

    // Injector injector = Guice.createInjector(new AppInjectior());
    // module = injector.getInstance(AppModule.class);

    module = AppModulePropFactory.getInstance();
  }

  /**
   * The method returned currency average rate. Defaul EURO
   * 
   * @return
   */
  @GET
  @Path("/quickshow")
  @Produces(MediaType.APPLICATION_JSON)
  public AnswerObjectByDate getAnswers() {

    Map<String, String> periodOfDatesMap = GenerateDate.getPeriodOfDates();

    AnswerObjectByDate answerObjectsListByDates = null;
    try {
      answerObjectsListByDates = returnAnswerByDates(periodOfDatesMap, module.getHost(), "EUR");
    }
    catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return answerObjectsListByDates;
  }

  /** Using query parameter **/
  @GET
  @Path("/show")
  @Produces(MediaType.APPLICATION_JSON)
  public AnswerObjectByDate getAnswersBySpecificDateAndCurrencyCode(@QueryParam("date") String dateFrom, @QueryParam("cc") String currencyCode) {

    Map<String, String> periodOfDatesMap = new HashMap<String, String>();
    periodOfDatesMap.put("from", dateFrom);
    periodOfDatesMap.put("to", dateFrom);

    AnswerObjectByDate answerObjectsListByDates = null;
    try {
      answerObjectsListByDates = returnAnswerByDates(periodOfDatesMap, module.getHost(), currencyCode);
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return answerObjectsListByDates;
  }

  /**
   * Return default Euro but with possibility to change currency
   * 
   * @param currency
   * @return
   */
  @GET
  @Path("/show/{currency}")
  @Produces(MediaType.APPLICATION_JSON)
  public AnswerObjectByDate getAnswersOfCurrencyParam(@PathParam("currency") String currency) {

    Map<String, String> periodOfDatesMap = GenerateDate.getPeriodOfDates();

    AnswerObjectByDate answerObjectsListByDates = null;
    try {
      answerObjectsListByDates = returnAnswerByDates(periodOfDatesMap, module.getHost(), currency);
    }
    catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return answerObjectsListByDates;
  }

  @GET
  @Path("/show/{currency_code}/from/{date_from}/to/{date_to}")
  @Produces(MediaType.APPLICATION_JSON)
  public AnswerObjectByDate getAnswersByDates(@PathParam("date_from") String dateFrom, @PathParam("date_to") String dateTo,
      @PathParam("currency_code") @DefaultValue("EUR") String currencyCode) {

    Map<String, String> periodOfDatesMap = new HashMap<String, String>();
    periodOfDatesMap.put("from", dateFrom);
    if (dateTo.equals("now")) {
      periodOfDatesMap.put("to", new SimpleDateFormat("ddMMyyyy").format(new Date()));
    }
    else {
      periodOfDatesMap.put("to", dateTo);
    }

    AnswerObjectByDate answerObjectsListByDates = null;
    try {
      answerObjectsListByDates = returnAnswerByDates(periodOfDatesMap, module.getHost(), currencyCode);
    }
    catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return answerObjectsListByDates;
  }

  @GET
  @Path("/show/from/{date_from}/to/{now_or_to}")
  @Produces(MediaType.APPLICATION_JSON)
  public AnswerObjectByDate getAnswersFromToNow(@PathParam("date_from") String dateFrom, @PathParam("now_or_to") String dateTo) {

    Map<String, String> periodOfDatesMap = new HashMap<String, String>();
    periodOfDatesMap.put("from", dateFrom);
    if (dateTo.equals("now")) {
      periodOfDatesMap.put("to", new SimpleDateFormat("ddMMyyyy").format(new Date()));
    }
    else {
      periodOfDatesMap.put("to", dateTo);
    }

    AnswerObjectByDate answerObjectsListByDates = null;
    try {
      answerObjectsListByDates = returnAnswerByDates(periodOfDatesMap, module.getHost(), "EUR");
    }
    catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return answerObjectsListByDates;
  }

  @GET
  @Path("/show/date/{date_from}")
  @Produces(MediaType.APPLICATION_JSON)
  public AnswerObjectByDate getAnswersBySpecificDate(@PathParam("date_from") String dateFrom) {

    Map<String, String> periodOfDatesMap = new HashMap<String, String>();
    periodOfDatesMap.put("from", dateFrom);
    periodOfDatesMap.put("to", dateFrom);

    AnswerObjectByDate answerObjectsListByDates = null;
    try {
      answerObjectsListByDates = returnAnswerByDates(periodOfDatesMap, module.getHost(), "EUR");
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return answerObjectsListByDates;
  }

  // private List<AnswerObject> returnAnswers(Map<String, String> periodOfDatesMap, String uri, String currency) {
  //
  // List<AnswerObject> answers = new ArrayList<AnswerObject>();
  //
  // List<String> periodOfDates = GenerateDate.generateDatesByPeriod(periodOfDatesMap.get("from"), periodOfDatesMap.get("to"));
  //
  // ProcessRequest processRequest = new ProcessRequest(uri);
  //
  // AnswerObject answerObject = processRequest.prepareAnswerObject(currency, periodOfDates);
  //
  // answers.add(answerObject);
  //
  // return answers;
  // }

  /**
   * General processing of getting answer(s) to prepare send in specified response.
   * 
   * @param periodOfDatesMap
   * @param uri
   * @param currency
   * @return Json array with answer object
   * @throws InvocationTargetException
   */
  private List<AnswerObjectByDate> returnAnswersByDates(Map<String, String> periodOfDatesMap, String uri, String currency) throws InvocationTargetException {

    List<AnswerObjectByDate> answers = new ArrayList<AnswerObjectByDate>();

    List<String> periodOfDates = GenerateDate.generateDatesByPeriod(periodOfDatesMap.get("from"), periodOfDatesMap.get("to"));

    ProcessRequest processRequest = new ProcessRequest(uri);

    AnswerObjectByDate answerObjectByDate = processRequest.prepareAnswerObjectByDate(currency, periodOfDates);

    answers.add(answerObjectByDate);

    return answers;
  }

  private AnswerObjectByDate returnAnswerByDates(Map<String, String> periodOfDatesMap, String uri, String currency) throws InvocationTargetException {

    // List<AnswerObjectByDate> answers = new ArrayList<AnswerObjectByDate>();

    List<String> periodOfDates = GenerateDate.generateDatesByPeriod(periodOfDatesMap.get("from"), periodOfDatesMap.get("to"));

    ProcessRequest processRequest = new ProcessRequest(uri);

    AnswerObjectByDate answerObjectByDate = processRequest.prepareAnswerObjectByDate(currency, periodOfDates);

    // answers.add(answerObjectByDate);

    return answerObjectByDate;
  }

}
