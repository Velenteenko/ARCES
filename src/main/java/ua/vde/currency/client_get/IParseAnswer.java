package ua.vde.currency.client_get;

import java.util.List;

import ua.vde.currency.process.object.AnswerObject;
import ua.vde.currency.process.object.AnswerObjectByDate;

public interface IParseAnswer {

  public AnswerObject prepareAnswerObject(String currencyCode, List<String> formatedDate);

  public AnswerObjectByDate prepareAnswerObjectByDate(String currencyCode, List<String> formatedDate) throws Exception;

}
