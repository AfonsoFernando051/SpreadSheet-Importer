package io.github.fernandoafonso.spreadsheetimporter.model;

import java.util.HashMap;


/**
 * @author Fernando Dias
 */
public class PlanilhaModel {

  /**
   * Nome da planilha
   */
  String nomeIdentificador;

  /**
   * Coluna da planilha e valor
   */
  HashMap<String, String> colunaEValor = new HashMap<String, String>();


  /**
   * Componente relacionado à importação
   */
  private TipoPlanilhaImportacaoEnum tipoLogico;

  /**
   * @return {@link #nomeIdentificador}
   */
  public String getNomeIdentificador() {
    return nomeIdentificador;
  }

  /**
   * @param nomeIdentificador atualiza {@link #nomeIdentificador}.
   */
  public void setNomeIdentificador(String nomeIdentificador) {
    this.nomeIdentificador = nomeIdentificador;
  }

  /**
   * @return {@link #colunaEValor}
   */
  public HashMap<String, String> getColunaEValor() {
    return colunaEValor;
  }

  /**
   * @param colunaEValor atualiza {@link #colunaEValor}.
   */
  public void setColunaEValor(HashMap<String, String> colunaEValor) {
    this.colunaEValor = colunaEValor;
  }

  /**
   * Adiciona uma nova coluna e seu valor ao mapa {@link #colunaEValor}.
   *
   * @param coluna - nome da coluna (chave)
   * @param valor  - valor associado à coluna
   */
  public void addColunaEValor(String coluna, String valor) {
    if (this.colunaEValor == null) {
      this.colunaEValor = new HashMap<>();
    }
    this.colunaEValor.put(coluna, valor);
  }


  /**
   * @return {@link #tipoLogico}
   */
  public TipoPlanilhaImportacaoEnum getTipoLogico() {
    return tipoLogico;
  }

  /**
   * @param tipoLogico atualiza {@link #tipoLogico}.
   */
  public void setTipoLogico(TipoPlanilhaImportacaoEnum tipoLogico) {
    this.tipoLogico = tipoLogico;
  }

}
