package io.github.fernandoafonso.spreadsheetimporter.core;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import io.github.fernandoafonso.spreadsheetimporter.model.CelulaData;
import io.github.fernandoafonso.spreadsheetimporter.model.InterfacePlanilhaModel;
import io.github.fernandoafonso.spreadsheetimporter.model.PlanilhaModel;
import io.github.fernandoafonso.spreadsheetimporter.model.RowData;
import io.github.fernandoafonso.spreadsheetimporter.translator.SheetTranslator;

/**
 * Classe criada para criar processamento genérico de planilhas através de modal
 * de configuração gerado em formulário de importação
 *
 * @author Fernando Dias
 * @param <T> -> Tipo genérico, classe filha deve implementar
 */
public class GenericPlanilhaProcessor<T>
  implements PlanilhaProcessor<T> {

  /**
   * Modelo de configuração de importação
   */
  private final InterfacePlanilhaModel<T> modelConfig;

  /**
   * Mapa com titulos de colunas
   */
  private Map<Integer, String> mapaTitulosPorIndice = new HashMap<>();

  /**
   * @param modelConfig - odelo de configuração de importação
   */
  public GenericPlanilhaProcessor(InterfacePlanilhaModel<T> modelConfig) {
    this.modelConfig = modelConfig;
  }

  @Override
  public ArrayList<T> processar(Workbook workbook,
                                PlanilhaModel configPlanilha)
    throws IOException {
    ArrayList<T> resultados = new ArrayList<>();
    int abaIndex = Character
        .getNumericValue(configPlanilha.getNomeIdentificador().charAt(0));
    Sheet sheet = workbook.getSheetAt(abaIndex);

    for (Row row : sheet) {
      if (linhaImportacaoInvalida(row, configPlanilha)) {
        continue;
      }

      resultados.addAll(processarLinha(row, configPlanilha));
    }

    return resultados;
  }

  /**
   * @param row      -> linha
   * @param planilha -> do contexto
   * @return lista processada pela linha
   */
  private ArrayList<T> processarLinha(Row row, PlanilhaModel planilha) {
    ArrayList<T> resultadosLinha = new ArrayList<>();
    try {
      // Processa linhas de dados
      RowData linha = new RowData();
      for (Map.Entry<String, String> entry : planilha.getColunaEValor()
          .entrySet()) {

        String colunaKey = entry.getKey();
        int colunaIndex = colunaToIndice(planilha, colunaKey);
        Cell cell = row.getCell(colunaIndex);
        String titulo = mapaTitulosPorIndice.get(colunaIndex);

        if (cell != null && !isCelulaInvalida(cell)
            && celulaMatches(cell, colunaIndex)) {
          ImportableSheet<?> sheet = SheetFactory
              .create(cell, titulo, row.getRowNum() + 1);
          CelulaData celula = new CelulaData(colunaKey, sheet);
          linha.setNumeroLinha(row.getRowNum() + 1);
          linha.addCelula(celula);
        }
      }
      if (!linha.getCelulas().isEmpty()) {
        resultadosLinha = createModelsByRows(linha);
      }
    } catch (Exception e) {
      e.getMessage();
    }
    return resultadosLinha;
  }

  /**
   * Converte o valor de uma célula para String, tratando todos os tipos
   * possíveis.
   *
   * @param cell - Célula a ser convertida
   * @return String com o valor formatado da célula
   * @throws IllegalStateException se o tipo da célula não for suportado
   */
  private String cellToString(Cell cell) {
    if (cell == null) {
      return "";
    }

    switch (cell.getCellType()) {
    case STRING:
      return cell.getStringCellValue().trim();

    case NUMERIC:
      if (DateUtil.isCellDateFormatted(cell)) {
        return cell.getLocalDateTimeCellValue().toString();
      }
      // Remove .0 de números inteiros
      double num = cell.getNumericCellValue();
      if (num == (long) num) {
        return String.valueOf((long) num);
      }
      return String.valueOf(num);

    case BOOLEAN:
      return String.valueOf(cell.getBooleanCellValue());

    case FORMULA:
      switch (cell.getCachedFormulaResultType()) {
      case STRING:
        return cell.getStringCellValue().trim();
      case NUMERIC:
        return String.valueOf(cell.getNumericCellValue());
      case BOOLEAN:
        return String.valueOf(cell.getBooleanCellValue());
      default:
        return cell.getCellFormula();
      }

    case BLANK:
      return "";

    default:
      throw new IllegalStateException("Tipo de célula não suportado: "
          + cell.getCellType());
    }
  }

  /**
   * Verifica se o valor da célula é IGUAL ao nome/identificador da coluna. Ex.:
   * Se a coluna é "A", a célula deve ter o valor "A".
   *
   * @param cell      - Célula a ser validada
   * @param colunaKey - Nome esperado da coluna (ex: "A", "DATA", "VALOR")
   * @return true se o valor da célula for IGUAL à colunaKey, false caso
   *         contrário
   */
  private boolean celulaMatches(Cell cell, int colunaKey) {
    if (cell == null) {
      return false;
    }
    return cell.getColumnIndex() == colunaKey;
  }

  /**
   * @param cell -> celula validada
   * @return true -> se celula for nula ou vazia
   */
  private boolean isCelulaInvalida(Cell cell) {
    return cell == null || cell.getCellType() == CellType.BLANK;
  }

  /**
   * @param linha percorrida
   * @return modelos criados a partir da linha
   */
  @SuppressWarnings("unchecked")
  protected ArrayList<T> createModelsByRows(RowData linha) {
    T modelo = (T) modelConfig.createModelsByRow().apply(linha);
    if (modelo != null) {
      return (ArrayList<T>) modelo;
    }
    return new ArrayList<T>();
  }

  /**
   * @param row      -> A verificar numeração
   * @param planilha -> Atual
   * @return true se a linha eh valida
   */
  public boolean linhaImportacaoInvalida(Row row, PlanilhaModel planilha) {
    if (row == null) {
      return true;
    }
    int rowNum = row.getRowNum() + 1;
    int posInicio = colunaToIndice(planilha, SheetTranslator.LINHA_INICIO);

    if (!planilha.getColunaEValor()
        .containsKey(SheetTranslator.CABECALHO)) {
      boolean isCabecalho = row.getRowNum() == 0;
      if (isCabecalho) {
        // Processa linha de cabeçalho
        preencherCabecalho(row, planilha);
        return true;
      }
    } else {
      int posCabecalho = colunaToIndice(planilha,
                                        SheetTranslator.CABECALHO);
      boolean isCabecalho = row.getRowNum() + 1 == posCabecalho;

      if (isCabecalho) {
        // Processa linha de cabeçalho
        preencherCabecalho(row, planilha);
        return true;
      }
      if (rowNum < posCabecalho) {
        return true;
      }
    }

    if (planilha.getColunaEValor()
        .containsKey(SheetTranslator.LINHA_FIM)) {
      int posFim = colunaToIndice(planilha, SheetTranslator.LINHA_FIM);
      return rowNum < posInicio || rowNum > posFim;
    }

    return rowNum < posInicio;
  }

  /**
   * @param row      -> linha
   * @param planilha do contexto
   */
  protected void preencherCabecalho(Row row, PlanilhaModel planilha) {
    for (Map.Entry<String, String> entry : planilha.getColunaEValor()
        .entrySet()) {
      String colunaKey = entry.getKey();
      if (entry.getValue() != null && entry.getValue().matches("\\d+")) {
        continue;
      }
      int colunaIndex = colunaToIndice(planilha, colunaKey);
      Cell cell = row.getCell(colunaIndex);

      if (cell != null && !isCelulaInvalida(cell)) {
        mapaTitulosPorIndice.put(colunaIndex, cellToString(cell));
      }
    }
  }

  /**
   * Retorna a posição da coluna, tratando valores numéricos ou letras no estilo
   * Excel.
   *
   * @param planilha a planilha com mapeamento de colunas
   * @param letra    a chave da coluna
   * @return índice da coluna como inteiro
   */
  public int colunaToIndice(PlanilhaModel planilha, String letra) {
    String valor = planilha.getColunaEValor().get(letra);
    if (valor != null) {
      valor = valor.trim();
      // Verifica se é numérico
      if (valor.matches("\\d+")) {
        return Integer.parseInt(valor);
      }
      // Verifica se é alfabético (estilo Excel: A, AB, ZY...)
      if (valor.matches("[A-Za-z]+")) {
        return excelColunaParaIndice(valor.toUpperCase());
      }
    }
    return 0;
  }

  /**
   * Converte uma coluna no estilo Excel (ex: A, Z, AA, AB...) para índice
   * (0-based).
   *
   * @param coluna nome da coluna (ex: "A", "AB")
   * @return índice correspondente (ex: "A" = 0, "Z" = 25, "AA" = 26)
   */
  private int excelColunaParaIndice(String coluna) {
    int resultado = 0;
    for (int i = 0; i < coluna.length(); i++) {
      char c = coluna.charAt(i);
      resultado = resultado * 26 + (c - 'A' + 1);
    }
    return resultado - 1; // Ajusta para índice 0-based
  }

  /**
   * Valida e converte célula para LocalDate
   *
   * @param cell -> Celula do contexto
   * @return data convertida
   */
  protected LocalDate validarEConverterData(Cell cell) {
    validarCelulaNaoNula(cell, "DATA");

    if (cell.getCellType() != CellType.NUMERIC
        || !DateUtil.isCellDateFormatted(cell)) {
      throw new IllegalArgumentException(formatarMensagemErro(cell,
                                                              "deve ser uma data formatada"));
    }

    LocalDateTime dateTime = cell.getLocalDateTimeCellValue();
    if (dateTime == null) {
      throw new IllegalArgumentException(formatarMensagemErro(cell,
                                                              "contém uma data inválida"));
    }

    LocalDate data = dateTime.toLocalDate();
    if (data.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException(formatarMensagemErro(cell,
                                                              "não pode ser uma data futura"));
    }

    return data;
  }

  /**
   * Valida e converte célula para BigDecimal
   *
   * @param cell -> celula
   * @param tipo -> de planilha
   * @param min  Valor mínimo permitido (inclusive)
   * @param max  Valor máximo permitido (inclusive, null para sem limite)
   * @return valor convertido
   */
  protected BigDecimal validarEConverterValor(Cell cell, String tipo,
                                              double min, Double max) {
    validarCelulaNaoNula(cell, tipo);

    if (cell.getCellType() != CellType.NUMERIC) {
      throw new IllegalArgumentException(formatarMensagemErro(cell,
                                                              "deve conter um valor numérico"));
    }

    double valor = cell.getNumericCellValue();

    if (valor < min) {
      throw new IllegalArgumentException(formatarMensagemErro(cell, String
          .format("não pode ser menor que %.2f", min)));
    }

    if (max != null && valor > max) {
      throw new IllegalArgumentException(formatarMensagemErro(cell, String
          .format("não pode exceder %.2f", max)));
    }

    return BigDecimal.valueOf(valor);
  }

  /**
   * Valida se célula não é nula
   *
   * @param cell -> celula
   * @param tipo -> de planilha
   */
  protected void validarCelulaNaoNula(Cell cell, String tipo) {
    if (cell == null) {
      throw new IllegalArgumentException(String
          .format("Célula de %s não pode ser nula", tipo));
    }
  }

  /**
   * Formata mensagem de erro com referência da coluna
   *
   * @param cell     -> celula
   * @param mensagem -> mensagem de erro
   * @return mensagem formatada
   */
  protected String formatarMensagemErro(Cell cell, String mensagem) {
    return String.format("Erro na coluna %s: %s",
                         getColunaLetra(cell.getColumnIndex()), mensagem);
  }

  /**
   * @param columnIndex -> index a pesquisar para coluna
   * @return index da coluna
   */
  public static String getColunaLetra(int columnIndex) {
    StringBuilder coluna = new StringBuilder();
    while (columnIndex >= 0) {
      coluna.insert(0, (char) ('A' + (columnIndex % 26)));
      columnIndex = (columnIndex / 26) - 1;
    }
    return coluna.toString();
  }

}
