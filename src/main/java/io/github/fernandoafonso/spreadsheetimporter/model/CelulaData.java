package io.github.fernandoafonso.spreadsheetimporter.model;

import io.github.fernandoafonso.spreadsheetimporter.core.ImportableSheet;

/**
 * Container que armazena uma célula da planilha junto com seus metadados
 * associados. Inclui a célula em si, o título da coluna, a letra/nome da coluna
 * e o número da linha.
 * <p>
 * Utilizado no processamento de planilhas para manter o contexto completo de
 * cada célula.
 * </p>
 *
 * @author Fernando Dias
 */
public class CelulaData {

  /**
   * Identificador da Célula
   */
  private final String identificador;

  /**
   * Célula do contexto
   */
  private ImportableSheet<?> sheetData;

  /**
   * Constrói um novo container de célula com todos os metadados.
   *
   * @param identificador identifica a celula com valor do mapa
   * @param sheetData     a célula da planilha (não pode ser nula)
   * @throws IllegalArgumentException se cell for nulo
   */
  public CelulaData(String identificador, ImportableSheet<?> sheetData) {
    if (sheetData == null) {
      throw new IllegalArgumentException("Célula não pode ser nula");
    }
    this.identificador = identificador;
    this.setSheetData(sheetData);
  }

  /**
   * @return {@link #identificador}
   */
  public String getIdentificador() {
    return identificador;
  }

  /**
   * @return {@link #sheetData}
   */
  public ImportableSheet<?> getSheetData() {
    return sheetData;
  }

  /**
   * @param sheetData atualiza {@link #sheetData}.
   */
  public void setSheetData(ImportableSheet<?> sheetData) {
    this.sheetData = sheetData;
  }

}
