package io.github.fernandoafonso.spreadsheetimporter.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Fernando Dias
 */
public class SaveBytesManager {

  /**
   * Nome do arquivo
   */
  private String nomeArquivo;

  /**
   * Arquivo a de índices a ser carregado
   */
  private InputStream fileData = null;

  /**
   * Cópia do arquivo a de índices ser carregado
   */
  private byte[] bytes = null;


  /**
   * Seta data de Criacao
   */
  public void setDataCriacao() {
    try (InputStream inputStream = getNewInputWithBytes()) {

      Workbook workbook;

      // Detecta o tipo do arquivo automaticamente
      if (isXLS(inputStream)) {
        workbook = new HSSFWorkbook(inputStream); // Para arquivos XLS (antigo)
      } else {
        OPCPackage pkg = OPCPackage.open(inputStream);
        workbook = new XSSFWorkbook(pkg); // Para arquivos XLSX (moderno)
      }

      workbook.close(); // Fecha o workbook para evitar vazamento de memória

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Detecta se o arquivo é do tipo XLS (formato antigo)
   *
   * @param inputStream - REFERENCIA
   * @return True se eh xls
   */
  private boolean isXLS(InputStream inputStream) {
    try {
      byte[] header = new byte[8];
      inputStream.mark(8);
      inputStream.read(header, 0, 8);
      inputStream.reset();

      // Verifica a assinatura do arquivo XLS (D0 CF 11 E0 A1 B1 1A E1)
      return (header[0] == (byte) 0xD0 && header[1] == (byte) 0xCF
          && header[2] == (byte) 0x11 && header[3] == (byte) 0xE0);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * @return {@link #fileData}
   */
  public InputStream getFileData() {
    return fileData;
  }

  /**
   * @param fileData atualiza {@link #fileData}.
   */
  public void setFileData(InputStream fileData) {
    if (fileData instanceof ByteArrayInputStream) {
      // Se for um ByteArrayInputStream, crie um novo PushbackInputStream para
      // permitir a leitura repetida
      this.fileData = new PushbackInputStream(fileData);
    } else {
      this.fileData = fileData;
    }
  }

  /**
   * Método que salva os bytes de um InputStream para posteriormente
   * reaproveitar esses dados
   *
   * @param inputStream - a ser convertido em bytes
   */
  public void setBytes(InputStream inputStream) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len;
    try {
      while ((len = inputStream.read(buffer)) != -1) {
        baos.write(buffer, 0, len);
      }
      baos.flush();
      this.bytes = baos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace(); // Tratar exceção, se necessário
    } finally {
      try {
        baos.close(); // Fechar o stream ByteArrayOutputStream
      } catch (IOException e) {
        e.printStackTrace(); // Tratar exceção, se necessário
      }
    }
  }

  /**
   * @param content atualiza {@link #bytes}.
   */
  public void setBytes(byte[] content) {
    this.bytes = content;
  }

  /**
   * @return {@link #nomeArquivo}
   */
  public String getNomeArquivo() {
    return nomeArquivo;
  }

  /**
   * @param nomeArquivo atualiza {@link #nomeArquivo}.
   */
  public void setNomeArquivo(String nomeArquivo) {
    this.nomeArquivo = nomeArquivo;
  }

  /**
   * @return {@link #bytes}
   */
  public byte[] getBytes() {
    return bytes;
  }

  /**
   * Fecha arquivo
   */
  public void closeFileData() {
    try {
      if (null != this.fileData) {
        this.fileData.close();
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @return Input stream a partir de bytes salvos
   */
  public ByteArrayInputStream getNewInputWithBytes() {
    return new ByteArrayInputStream(this.bytes);

  }
}
