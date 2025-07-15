package io.github.fernandoafonso.spreadsheetimporter.core;

/**
 * Classe utilitária que representa o resultado de uma validação, contendo uma
 * mensagem e um indicador de sucesso ou falha.
 *
 * @author Fernando Dias
 */
public class ResultValidation {

  /**
   * Indica se a validação teve sucesso (true) ou falha (false).
   */
  private boolean sucesso;

  /**
   * Mensagem descritiva do resultado da validação.
   */
  private String mensagem;

  /**
   * Verifica se a validação foi bem-sucedida.
   *
   * @return true se foi sucesso, false se foi falha
   */
  public boolean isSucesso() {
    return sucesso;
  }

  /**
   * Define o status de sucesso da validação.
   *
   * @param sucesso true para sucesso, false para falha
   */
  public void setSucesso(boolean sucesso) {
    this.sucesso = sucesso;
  }

  /**
   * Obtém a mensagem do resultado da validação.
   *
   * @return mensagem associada à validação
   */
  public String getMensagem() {
    return mensagem;
  }

  /**
   * Define a mensagem descritiva da validação.
   *
   * @param mensagem texto com o resultado da validação
   */
  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  /**
   * Retorna uma representação em texto do resultado, incluindo o status
   * (SUCESSO/FALHA) e a mensagem.
   *
   * @return string formatada com o resultado da validação
   */
  @Override
  public String toString() {
    return "[" + (sucesso ? "SUCESSO" : "FALHA") + "] " + mensagem;
  }
}
