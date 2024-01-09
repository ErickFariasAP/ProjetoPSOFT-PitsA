package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.associacao.AssociacaoDTO;
import com.ufcg.psoft.commerce.dto.associacao.AssociacaoGetDTO;
//import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.associacao.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
//import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Associação")
class AssociacaoControllerTests {

    final String URI_ASSOCIACAO = "/associacao";

    @Autowired
    MockMvc driver;

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    EntregadorRepository entregadorRepository;

//     @Autowired
//     EntregadorService entregadorService;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    Entregador entregador;

    Estabelecimento estabelecimento;

    AssociacaoDTO associacaoDTO;

    AssociacaoDTO associacaoEstabelecimentoInvalidaDTO;

    AssociacaoDTO associacaoEntregadorInvalidaDTO;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        // Object Mapper suporte para LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        entregador = entregadorRepository.save(Entregador.builder()
                .nome("Entregador Um")
                .placaVeiculo("ABC-1234")
                .corVeiculo("Branco")
                .tipoVeiculo("carro")
                .codigoAcesso("123456")
                .build()
        );
        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("654321")
                .build()
        );
        associacaoDTO = AssociacaoDTO.builder()
                        .entregadorId(entregador.getId())
                        .estabelecimentoId(estabelecimento.getId())
                        .build();
        associacaoEstabelecimentoInvalidaDTO = AssociacaoDTO.builder()
                        .entregadorId(9999L)
                        .estabelecimentoId(9999L)
                        .build();
        associacaoEntregadorInvalidaDTO = AssociacaoDTO.builder()
                        .entregadorId(9999L)
                        .estabelecimentoId(estabelecimento.getId())
                        .build();
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
        associacaoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de criacao de associacao")
    class ClienteCriacaoAssociacao {

        @Test
        @DisplayName("Quando criamos uma associacao com sucesso")
        void testCriarAssociacaoComSucesso() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(post(URI_ASSOCIACAO)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(associacaoDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            AssociacaoGetDTO resultado = objectMapper.readValue(responseJsonString, AssociacaoGetDTO.AssociacaoGetDTOBuilder.class).build(); 

            // Assert
            assertAll(
                    () -> assertEquals(1, associacaoRepository.count()),
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(entregador.getId(), resultado.getEntregadorId()),
                    () -> assertEquals(estabelecimento.getId(), resultado.getEstabelecimentoId())
            );
        }

        @Test
        @DisplayName("Quando criamos uma associacao com entregador inexistente")
        void testCriarAssociacaoComEntregadorInexistente() throws Exception {
            // Arrange

            // Act
             String responseJsonString = driver.perform(post(URI_ASSOCIACAO)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(associacaoEntregadorInvalidaDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals(0, associacaoRepository.count()),
                    () -> assertEquals("O entregador consultado nao existe!", resultado.getMessage())
            );
        }

        @Test
        @DisplayName("Quando criamos uma associacao com estabelecimento inexistente")
        void testCriarAssociacaoComEstabelecimentoInexistente() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(post(URI_ASSOCIACAO)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(associacaoEstabelecimentoInvalidaDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals(0, associacaoRepository.count()),
                    () -> assertEquals("O estabelecimento consultado nao existe!", resultado.getMessage())
            );
        }

        @Test
        @DisplayName("Quando criamos uma associacao passando codigo de acesso invalido")
        void testCriarAssociacaoComCodigoDeAcessoInvalido() throws Exception {
            // Arrange

            // Act
              String responseJsonString = driver.perform(post(URI_ASSOCIACAO)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "codigo errado")
                            .content(objectMapper.writeValueAsString(associacaoDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals(0, associacaoRepository.count()),
                    () -> assertEquals("Codigo de acesso invalido!", resultado.getMessage())
            );
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de aprovação de associacao")
    class ClienteAprovacaoAssociacao {

        @BeforeEach
        void setUp() {
            associacaoRepository.save(Associacao.builder()
                    .entregadorId(entregador.getId())
                    .estabelecimentoId(estabelecimento.getId())
                    .status(false)
                    .build()
            );
                associacaoDTO = AssociacaoDTO.builder()
                        .entregadorId(entregador.getId())
                        .estabelecimentoId(estabelecimento.getId())
                        .build();
                associacaoEstabelecimentoInvalidaDTO = AssociacaoDTO.builder()
                        .entregadorId(9999L)
                        .estabelecimentoId(9999L)
                        .build();
                associacaoEntregadorInvalidaDTO = AssociacaoDTO.builder()
                        .entregadorId(9999L)
                        .estabelecimentoId(estabelecimento.getId())
                        .build();
        }

        @AfterEach
        void tearDown() {
                associacaoRepository.deleteAll();
        }

        
        @Test
        @DisplayName("Quando aprovamos uma associacao com sucesso")
        void quandoAprovamosAssociacaoComSucesso() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(put(URI_ASSOCIACAO + "/" + 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", estabelecimento.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(associacaoDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Associacao resultado = objectMapper.readValue(responseJsonString, Associacao.AssociacaoBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(1, associacaoRepository.count()),
                    () -> assertTrue(resultado.isStatus())
            );
        }
        @Test
        @DisplayName("Quando aprovamos uma associacao com entregador inexistente")
        void quandoAprovamosAssociacaoComEntregadorInexistente() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(put(URI_ASSOCIACAO + "/" + 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", estabelecimento.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(associacaoEntregadorInvalidaDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals(1, associacaoRepository.count()),
                    () -> assertEquals("O entregador consultado nao existe!", resultado.getMessage())
            );
        }

        @Test
        @DisplayName("Quando aprovamos uma associacao com estabelecimento inexistente")
        void quandoAprovamosAssociacaoComEstabelecimentoInexistente() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(put(URI_ASSOCIACAO + "/" + 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", estabelecimento.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(associacaoEstabelecimentoInvalidaDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals(1, associacaoRepository.count()),
                    () -> assertEquals("O estabelecimento consultado nao existe!", resultado.getMessage())
            );
        }

        @Test
        @DisplayName("Quando aprovamos uma associacao passando codigo de acesso invalido")
        void quandoAprovamosAssociacaoComCodigoDeAcessoInvalido() throws Exception {
             // Arrange

            // Act
                String responseJsonString = driver.perform(put(URI_ASSOCIACAO + "/" + 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "codigo errado")
                            .content(objectMapper.writeValueAsString(associacaoDTO)))
                        .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals(1, associacaoRepository.count()),
                    () -> assertEquals("Codigo de acesso invalido!", resultado.getMessage())
            );
        }
    }
}