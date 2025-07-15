package io.github.fernandoafonso.spreadsheetimporter.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import io.github.fernandoafonso.spreadsheetimporter.model.SheetValor;

/**
 * @author Fernando Dias
 */
public class ImportacaoServiceUtil {

  /**
   * Instância única, criada uma vez
   */
  private static final ImportacaoServiceUtil INSTANCE = new ImportacaoServiceUtil();

  /**
   * Construtor privado para evitar instância externa
   */
  private ImportacaoServiceUtil() {
  }

  /**
   * Método de acesso global
   *
   * @return instancia
   */
  public static ImportacaoServiceUtil getInstance() {
    return INSTANCE;
  }

  /**
   * @param valor a ser formatado
   * @return valor formatado
   */
  public String formatarValorMoeda(BigDecimal valor) {
    if (valor == null) {
      return "0,00";
    }

    BigDecimal valorFormatado = valor.setScale(2, RoundingMode.HALF_UP);
    NumberFormat formatoMoeda = NumberFormat
        .getNumberInstance(new Locale("pt", "BR"));
    formatoMoeda.setMinimumFractionDigits(2);
    formatoMoeda.setMaximumFractionDigits(2);

    return formatoMoeda.format(valorFormatado);
  }

  /**
   * @param cotacaoValor        -> valor de cotação cadastrada no sistema
   * @param valCotacaoCalculado -> valor de cotação calculada
   * @return true se valores forem coerentes
   */
  public boolean valorEhCoerente(BigDecimal cotacaoValor,
                                 BigDecimal valCotacaoCalculado) {
    BigDecimal tolerancia = new BigDecimal("0.01");
    boolean ehCoerente = cotacaoValor.subtract(valCotacaoCalculado).abs()
        .compareTo(tolerancia) <= 0;
    return ehCoerente;
  }

  /**
   * @param cotacaoSheet -> Célula a ser verificada
   * @return true se cotação deve ser importada
   */
  public boolean deveImportarCotacao(SheetValor cotacaoSheet) {
    return cotacaoSheet != null && cotacaoSheet.getValue() != null;
  }

  /**
   * Processa o valor de uma célula, tratando porcentagens se necessário
   *
   * @param sheetValor celula
   * @return valor da celula
   */
  public BigDecimal processarValorCelula(SheetValor sheetValor) {
    BigDecimal valor = sheetValor.getValue();
    return valor;
  }

}
