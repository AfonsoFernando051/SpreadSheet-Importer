package io.github.fernandoafonso.spreadsheetimporter.core;

/**
 * @author Fernando Dias
 * @param <T> Tipo do valor armazenado
 */
public abstract class AbstractSheet<T>
  implements ImportableSheet<T> {
  /**
   * Data da análise.
   */
  private T value;

  /**
   * Número da linha da planilha.
   */
  private int numeroLinha;

  /**
   * Coluna da planilha.
   */
  private String coluna;

  /**
   * Titulo Coluna da planilha.
   */
  private String tituloColuna;

  /**
   * @return {@link #value}
   */
  @Override
  public T getValue() {
    return value;
  }

  /**
   * @param value atualiza {@link #value}.
   */
  @Override
  public void setValue(T value) {
    this.value = value;
  }

  /**
   * @return {@link #numeroLinha}
   */
  public int getNumeroLinha() {
    return numeroLinha;
  }

  /**
   * @param numeroLinha atualiza {@link #numeroLinha}.
   */
  public void setNumeroLinha(int numeroLinha) {
    this.numeroLinha = numeroLinha;
  }

  /**
   * @return {@link #coluna}
   */
  public String getColuna() {
    return coluna;
  }

  /**
   * @param coluna atualiza {@link #coluna}.
   */
  public void setColuna(String coluna) {
    this.coluna = coluna;
  }

  /**
   * @return {@link #tituloColuna}
   */
  public String getTituloColuna() {
    return tituloColuna;
  }

  /**
   * @param tituloColuna atualiza {@link #tituloColuna}.
   */
  public void setTituloColuna(String tituloColuna) {
    this.tituloColuna = tituloColuna;
  }

  @Override
  public String toString() {
    return "AbstractSheet [value=" + value + ", numeroLinha=" + numeroLinha
        + ", coluna=" + coluna + ", tituloColuna=" + tituloColuna + "]";
  }
}
