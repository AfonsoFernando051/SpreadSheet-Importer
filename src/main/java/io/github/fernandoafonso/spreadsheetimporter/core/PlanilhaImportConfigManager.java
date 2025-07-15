package io.github.fernandoafonso.spreadsheetimporter.core;

import java.util.HashMap;

import io.github.fernandoafonso.spreadsheetimporter.model.PlanilhaModel;
import io.github.fernandoafonso.spreadsheetimporter.model.TipoPlanilhaImportacaoEnum;

/**
 * @author Fernando Dias
 */
public class PlanilhaImportConfigManager {

  /**
   * Lista de planilhas a serem importadas
   */
  private HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhas = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();

  /**
   * Retorna a única planilha cadastrada, se houver exatamente uma.
   *
   * @return a única {@link PlanilhaModel} presente no mapa
   * @throws IllegalStateException se não houver nenhuma ou houver mais de uma
   *                               planilha
   */
  public PlanilhaModel getUniquePlanilha() {
    if (planilhas == null || planilhas.isEmpty()) {
      throw new IllegalStateException("Nenhuma planilha foi registrada.");
    }
    if (planilhas.size() > 1) {
      throw new IllegalStateException("Mais de uma planilha foi registrada. Use o tipo específico para recuperar.");
    }
    return planilhas.values().iterator().next();
  }

  /**
   * @param tipo     da planilha
   * @param planilha atualiza {@link #planilhas}.
   */
  public void addPlanilhas(TipoPlanilhaImportacaoEnum tipo,
                           PlanilhaModel planilha) {
    if (null != this.planilhas) {
      this.planilhas.put(tipo, planilha);
    } else {
      this.planilhas = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
      this.planilhas.put(tipo, planilha);
    }
  }

  /**
   * @return {@link #planilhas}
   */
  public HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> getPlanilhas() {
    return planilhas;
  }

  /**
   * @param planilhas atualiza {@link #planilhas}.
   */
  public void setPlanilhas(HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhas) {
    this.planilhas = planilhas;
  }

}
