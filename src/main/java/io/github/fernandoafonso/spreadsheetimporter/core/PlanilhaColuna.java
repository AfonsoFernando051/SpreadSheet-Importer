package io.github.fernandoafonso.spreadsheetimporter.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Fernando Dias
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PlanilhaColuna {

  /**
   * Valor do enum
   */
  String value();
}
