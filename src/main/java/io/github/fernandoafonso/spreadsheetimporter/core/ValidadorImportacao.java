package io.github.fernandoafonso.spreadsheetimporter.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Fernando Dias
 */
public class ValidadorImportacao {

  /**
   * Mapa de atributos a serem validados
   */
  private final Map<String, Object> atributos = new LinkedHashMap<>();

  /**
   * Registra um atributo nomeado para validação.
   *
   * @param nome  -> do atributo
   * @param valor -> do atributo
   */
  public void adicionarAtributo(String nome, Object valor) {
    atributos.put(nome, valor);
  }

  /**
   * @return true se atributos estiverem vazios
   */
  public boolean isEmpty() {
    return atributos.isEmpty();
  }

  /**
   * Verifica se alguma chave do mapa de atributos contém o trecho informado
   * (case-sensitive).
   *
   * @param trecho trecho da chave a ser procurado
   * @return true se alguma chave contiver o trecho
   */
  public boolean possuiAtributoContendo(String trecho) {
    for (String chave : atributos.keySet()) {
      if (chave.contains(trecho)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retorna o valor do primeiro atributo cuja chave contém o trecho informado.
   *
   * @param trecho trecho da chave a ser procurado
   * @return o valor do atributo correspondente ou null se não encontrado
   */
  public Object getAtributoContendo(String trecho) {
    for (Map.Entry<String, Object> entry : atributos.entrySet()) {
      if (entry.getKey().contains(trecho)) {
        return entry.getValue();
      }
    }
    return null;
  }
}
