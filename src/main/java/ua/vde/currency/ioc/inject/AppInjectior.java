package ua.vde.currency.ioc.inject;

import ua.vde.currency.ifaces.IPropertyBank;
import ua.vde.currency.impl.PropertyBankImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AppInjectior extends AbstractModule {

  @Override
  protected void configure() {
    bind(IPropertyBank.class).to(PropertyBankImpl.class).in(Scopes.SINGLETON);
  }

}
