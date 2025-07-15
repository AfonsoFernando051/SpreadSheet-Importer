package io.github.fernandoafonso.spreadsheetimporter.translator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public final class SheetTranslator {

    // Constantes para mapeamento de colunas
    public static final String NOME = "NOME";
    public static final String IDADE = "IDADE";
    public static final String EMAIL = "EMAIL";

    // Mapa explicativo (constante -> descrição)
    private static final Map<String, String> CONSTANT_DESCRIPTIONS;
	public static final String CABECALHO = "CABECALHO";
	public static final String LINHA_INICIO = "LINHA_INICIO";
	public static final String LINHA_FIM = "LINHA_FIM";

    static {
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put(NOME, "Nome completo do usuário");
        descriptions.put(IDADE, "Idade em anos completos");
        descriptions.put(EMAIL, "Endereço de e-mail válido");
        
        CONSTANT_DESCRIPTIONS = Collections.unmodifiableMap(descriptions);
    }

    // Método para acessar descrições
    public static String getDescription(String constant) {
        return CONSTANT_DESCRIPTIONS.getOrDefault(constant, "Descrição não disponível");
    }

    // Evitar instanciação
    private SheetTranslator() {}
}
