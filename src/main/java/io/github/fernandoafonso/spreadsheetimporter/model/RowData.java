package io.github.fernandoafonso.spreadsheetimporter.model;

import java.util.ArrayList;

import io.github.fernandoafonso.spreadsheetimporter.core.ImportableSheet;

/**
 * Classe criada para abstrair a ideia de linha de uma planilha, de forma que
 * agrupe uma lista de celulas e estas sejam manuseadas pelo desenvolvedor para
 * abstrair informações e criar modelos de importação
 *
 * @author Fernando Dias
 */
public class RowData {

  /**
   * Numero da linha
   */
  private int numeroLinha;

  /**
   * Lista de celulas da linhas
   */
  ArrayList<CelulaData> celulas = new ArrayList<CelulaData>();

  /**
   * Obtém o número da linha na planilha (baseado em 1).
   *
   * @return o número da linha
   */
  public int getNumeroLinha() {
    return numeroLinha;
  }

  /**
   * Obtém o número da linha na planilha (baseado em 1).
   *
   * @param numeroLinha -> que identifica a linha
   */
  public void setNumeroLinha(int numeroLinha) {
    this.numeroLinha = numeroLinha;
  }

  /**
   * @param celula -> Adicionada na lista de celulas
   */
  public void addCelula(CelulaData celula) {
    this.celulas.add(celula);
  }

  /**
   * @return {@link #celulas}
   */
  public ArrayList<CelulaData> getCelulas() {
    return celulas;
  }

  /**
   * @param celulas atualiza {@link #celulas}.
   */
  public void setCelulas(ArrayList<CelulaData> celulas) {
    this.celulas = celulas;
  }

  /**
   * @param identificador da celula
   * @return valor da celula por identificador
   */
  public ImportableSheet<?> getCelulaByIdentificador(String identificador) {
    for (CelulaData celulaData : celulas) {
      if (celulaData.getIdentificador().equals(identificador)) {
        return celulaData.getSheetData();
      }
    }
    return null;
  }

  /**
   * Verifica se existe alguma célula com o identificador especificado na linha
   *
   * @param identificador O identificador da coluna a ser verificado
   * @return true se a linha contém uma célula com o identificador, false caso
   *         contrário
   */
  public boolean containsIdentificador(String identificador) {
    if (identificador == null || celulas == null || celulas.isEmpty()) {
      return false;
    }

    for (CelulaData celula : celulas) {
      if (identificador.equals(celula.getIdentificador())) {
        return true;
      }
    }
    return false;
  }

}
