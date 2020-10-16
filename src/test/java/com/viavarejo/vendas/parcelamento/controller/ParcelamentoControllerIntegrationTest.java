package com.viavarejo.vendas.parcelamento.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "selic.servico-pesquisa.habilitado=false")
@AutoConfigureMockMvc
public class ParcelamentoControllerIntegrationTest {
    private static final String JSON_PATH_PARCELA = "$.[%d].numeroParcela";
    private static final String JSON_PATH_VALOR = "$.[%d].valor";
    private static final String JSON_PATH_TAXA_JUROS_MES = "$.[%d].taxaJurosAoMes";
    private static final String ENDPOINT_PARCELAMENTO = "/api/v1/vendas/parcelamento";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCriarParcelamentoSemJurosCorretamente() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post(ENDPOINT_PARCELAMENTO)
                .content(getPayloadJsonParaCalculoSemJuros())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int indice = 0; indice < 6; indice++) {
            resultActions.andExpect(jsonPath(format(JSON_PATH_PARCELA, indice)).value(indice + 1))
                    .andExpect(jsonPath(format(JSON_PATH_VALOR, indice)).value("1666.67"))
                    .andExpect(jsonPath(format(JSON_PATH_TAXA_JUROS_MES, indice)).value("0.0"));
        }
    }

    @Test
    public void deveCriarParcelamentoComJurosPadraoCorretamente() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post(ENDPOINT_PARCELAMENTO)
                .content(getPayloadJsonParaCalculoComJuros())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int indice = 0; indice < 10; indice++) {
            resultActions.andExpect(jsonPath(format(JSON_PATH_PARCELA, indice)).value(indice + 1))
                    .andExpect(jsonPath(format(JSON_PATH_VALOR, indice)).value("1064.33"))
                    .andExpect(jsonPath(format(JSON_PATH_TAXA_JUROS_MES, indice)).value("1.15"));
        }
    }

    private String getPayloadJsonParaCalculoSemJuros() {
        return "{\"produto\":{\"codigo\":1,\"nome\":\"TV de 50 polegadas\",\"valor\":15000},\"condicaoPagamento\":{\"valorEntrada\":5000,\"qtdeParcelas\":6}}";
    }

    private String getPayloadJsonParaCalculoComJuros() {
        return "{\"produto\":{\"codigo\":1,\"nome\":\"TV de 50 polegadas\",\"valor\":15000},\"condicaoPagamento\":{\"valorEntrada\":5000,\"qtdeParcelas\":10}}";
    }
}
