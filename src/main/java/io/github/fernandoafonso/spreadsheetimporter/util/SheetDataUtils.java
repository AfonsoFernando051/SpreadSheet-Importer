package io.github.fernandoafonso.spreadsheetimporter.util;


import java.math.BigDecimal;
import java.time.LocalDate;

import io.github.fernandoafonso.spreadsheetimporter.model.SheetData;
import io.github.fernandoafonso.spreadsheetimporter.model.SheetValor;

/**
 * @author Fernando Dias
 */
public class SheetDataUtils {
  /**
   * @param data do evento
   * @return SheetData
   */
  public static SheetData generateSheetData(LocalDate data) {
    SheetData celula = new SheetData();
    celula.setValue(data);
    celula.setNumeroLinha(0);
    celula.setColuna("");
    celula.setTituloColuna("");
    return celula;
  }

  /**
   * @param valor da celula
   * @return SheetData
   */
  public static SheetValor generateSheetValor(BigDecimal valor) {
    SheetValor celula = new SheetValor();
    celula.setValue(valor);
    celula.setNumeroLinha(0);
    celula.setColuna("");
    celula.setTituloColuna("");
    return celula;
  }
}
