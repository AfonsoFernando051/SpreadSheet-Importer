package io.github.fernandoafonso.spreadsheetimporter.model;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * @author Fernando Dias
 * @param <T> -> Tipo genérico
 */
public interface InterfacePlanilhaModel<T> {

  /**
   * @return instancia concreta de modelo de planilha
   */
  T criarInstancia();

  /**
   * @return mapeamento de colunas
   */
  // Map<String, Consumer<RowData>> getMapeamentoColunas();

  /**
   * @return mapeamento de colunas
   */
  Function<RowData, ArrayList<T>> createModelsByRow();

}
