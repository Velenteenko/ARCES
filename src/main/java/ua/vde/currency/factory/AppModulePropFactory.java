package ua.vde.currency.factory;

import ua.vde.currency.ioc.inject.AppInjectior;
import ua.vde.currency.ioc.module.AppModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class AppModulePropFactory {

  private static AppModule instance;

  public static void setInstance(AppModule appModule) {
    instance = appModule;
  }

  public static AppModule getInstance() {
    if (instance == null) {
      Injector injector = Guice.createInjector(new AppInjectior());
      AppModule module = injector.getInstance(AppModule.class);
      return module;
    }

    return instance;
  }

}
