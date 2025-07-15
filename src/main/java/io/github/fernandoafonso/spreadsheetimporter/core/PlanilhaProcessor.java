package io.github.fernandoafonso.spreadsheetimporter.core;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import io.github.fernandoafonso.spreadsheetimporter.model.PlanilhaModel;

/**
 * @author Fernando Dias
 * @param <T> - Tipo generico
 */
public interface PlanilhaProcessor<T> {
  /**
   * Processa linhas da planilha e retorna lista com valores
   *
   * @param workbook de importação
   * @param planilha a ser processada
   * @return valores importados
   * @throws IOException Exception de stream
   */
  List<T> processar(Workbook workbook, PlanilhaModel planilha)
    throws IOException;

}
