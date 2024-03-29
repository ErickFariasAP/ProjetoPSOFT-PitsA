package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.sabor.Sabor;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.sabor.SaborRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Sabores")
public class SaborControllerTests {
        final String URI_SABORES = "/sabores";

        @Autowired
        MockMvc driver;

        @Autowired
        SaborRepository saborRepository;

        @Autowired
        EstabelecimentoRepository estabelecimentoRepository;

        ObjectMapper objectMapper = new ObjectMapper();
        Sabor sabor;
        Estabelecimento estabelecimento;
        SaborPostPutRequestDTO saborPostPutRequestDTO;

        @BeforeEach
        void setup() {
                objectMapper.registerModule(new JavaTimeModule());
                sabor = saborRepository.save(Sabor.builder()
                                .nome("Calabresa")
                                .tipo("salgado")
                                .precoM(10.0)
                                .precoG(15.0)
                                .disponibilidade(true)
                                .build());
                saborPostPutRequestDTO = SaborPostPutRequestDTO.builder()
                                .nome(sabor.getNome())
                                .tipo(sabor.getTipo())
                                .precoM(sabor.getPrecoM())
                                .precoG(sabor.getPrecoG())
                                .disponibilidade(sabor.getDisponibilidade())
                                .build();
                estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                                .codigoAcesso("654321")
                                .build());
        }

        @AfterEach
        void tearDown() {
                estabelecimentoRepository.deleteAll();
                saborRepository.deleteAll();
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação dos fluxos básicos API Rest")
        class SaborVerificacaoFluxosBasicosApiRest {

                @Test
                @DisplayName("Quando buscamos por todos sabores salvos")
                void quandoBuscamosPorTodosSaboresSalvos() throws Exception {
                        // Arrange
                        // Vamos ter 3 sabores no banco
                        Sabor sabor1 = Sabor.builder()
                                        .nome("Chocolate")
                                        .tipo("doce")
                                        .precoM(10.0)
                                        .precoG(15.0)
                                        .disponibilidade(true)
                                        .build();
                        Sabor sabor2 = Sabor.builder()
                                        .nome("Frango")
                                        .tipo("salgado")
                                        .precoM(10.0)
                                        .precoG(15.0)
                                        .disponibilidade(true)
                                        .build();
                        saborRepository.saveAll(Arrays.asList(sabor1, sabor2));

                        // Act
                        String responseJsonString = driver.perform(get(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        List<SaborPostPutRequestDTO> resultado = objectMapper.readValue(responseJsonString,
                                        new TypeReference<>() {
                                        });

                        // Assert
                        assertAll(
                                        () -> assertEquals(3, resultado.size()));
                }

                @Test
                @DisplayName("Quando buscamos um sabor salvo pelo id")
                void quandoBuscamosPorUmSaborSalvo() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(get(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals(sabor.getId().longValue(), resultado.getId().longValue()),
                                        () -> assertEquals(sabor.getNome(), resultado.getNome()),
                                        () -> assertEquals(sabor.getTipo(), resultado.getTipo()),
                                        () -> assertEquals(sabor.getPrecoM(), resultado.getPrecoM()),
                                        () -> assertEquals(sabor.getPrecoG(), resultado.getPrecoG()),
                                        () -> assertEquals(sabor.getDisponibilidade(), resultado.getDisponibilidade()));
                }

                @Test
                @DisplayName("Quando buscamos um sabor inexistente")
                void quandoBuscamosPorUmSaborInexistente() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(get(URI_SABORES + "/999999")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("O sabor consultado nao existe!", resultado.getMessage()));
                }

                @Test
                @DisplayName("Quando buscamos um sabor com código de acesso inválido")
                void quandoBuscamosPorUmSaborComCodigoInvalido() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(get(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", "999999")
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Codigo de acesso invalido!", resultado.getMessage()));
                }

                @Test
                @DisplayName("Quando criamos um novo sabor com dados válidos")
                void quandoCriarSaborValido() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(post(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isCreated()) // Codigo 201
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals(saborPostPutRequestDTO.getNome(), resultado.getNome()),
                                        () -> assertEquals(saborPostPutRequestDTO.getTipo(), resultado.getTipo()),
                                        () -> assertEquals(saborPostPutRequestDTO.getPrecoM(), resultado.getPrecoM()),
                                        () -> assertEquals(saborPostPutRequestDTO.getPrecoG(), resultado.getPrecoG()),
                                        () -> assertEquals(saborPostPutRequestDTO.getDisponibilidade(),
                                                        resultado.getDisponibilidade()));
                }

                @Test
                @DisplayName("Quando alteramos o sabor com dados válidos")
                void quandoAlteramosSaborValido() throws Exception {
                        // Arrange
                        Long saborId = sabor.getId();

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", saborId.toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals(saborId, resultado.getId().longValue()),
                                        () -> assertEquals(saborPostPutRequestDTO.getNome(), resultado.getNome()),
                                        () -> assertEquals(saborPostPutRequestDTO.getTipo(), resultado.getTipo()),
                                        () -> assertEquals(saborPostPutRequestDTO.getPrecoM(), resultado.getPrecoM()),
                                        () -> assertEquals(saborPostPutRequestDTO.getPrecoG(), resultado.getPrecoG()),
                                        () -> assertEquals(saborPostPutRequestDTO.getDisponibilidade(),
                                                        resultado.getDisponibilidade()));
                }

                @Test
                @DisplayName("Quando alteramos um sabor inexistente")
                void quandoAlteramosSaborInexistente() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", "999999")
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("O sabor consultado nao existe!", resultado.getMessage()));
                }

                @Test
                @DisplayName("Quando alteramos um sabor passando código de acesso inválido")
                void quandoAlteramosSaborCodigoAcessoInvalido() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", "999999")
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Codigo de acesso invalido!", resultado.getMessage()));
                }

                @Test
                @DisplayName("Quando excluímos um sabor salvo")
                void quandoExcluimosSaborValido() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(delete(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso()))
                                        .andExpect(status().isNoContent()) // Codigo 204
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        System.out.println(responseJsonString);

                        // Assert
                        assertTrue(responseJsonString.isBlank());
                }

                @Test
                @DisplayName("Quando excluímos um sabor inexistente")
                void quandoExcluimosSaborInexistente() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(delete(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", "999999")
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso()))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("O sabor consultado nao existe!", resultado.getMessage()));
                }

                @Test
                @DisplayName("Quando excluímos um sabor passando código de acesso inválido")
                void quandoExcluimosSaborCodigoAcessoInvalido() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(delete(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", "999999"))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Codigo de acesso invalido!", resultado.getMessage()));
                }
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação de nome")
        class SaborVerificacaoNome {

                @Test
                @DisplayName("Quando alteramos um sabor com nome válido")
                void quandoAlteramosSaborNomeValido() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setNome("Portuguesa");

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200 
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertEquals("Portuguesa", resultado.getNome());
                }

                @Test
                @DisplayName("Quando alteramos um sabor com nome vazio")
                void quandoAlteramosSaborNomeVazio() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setNome("");

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("Nome obrigatorio", resultado.getErrors().get(0)));
                }
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação de tipo")
        class SaborVerificacaoTipo {

                @Test
                @DisplayName("Quando alteramos um sabor com tipo válido")
                void quandoAlteramosSaborTipoValido() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setTipo("salgado");

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertEquals("salgado", resultado.getTipo());
                }

                @Test
                @DisplayName("Quando alteramos um sabor com tipo nulo")
                void quandoAlteramosSaborTipoNulo() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setTipo(null);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("Tipo obrigatorio", resultado.getErrors().get(0)));
                }

                @Test
                @DisplayName("Quando alteramos um sabor com tipo inválido")
                void quandoAlteramosSaborTipoInvalido() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setTipo("tipo invalido");

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("Tipo deve ser salgado ou doce",
                                                        resultado.getErrors().get(0)));
                }
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação de preco")
        class SaborVerificacaoPreco {

                @Test
                @DisplayName("Quando alteramos um sabor com precos válidos")
                void quandoAlteramosSaborPrecosValidos() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setPrecoM(40.0);
                        saborPostPutRequestDTO.setPrecoG(60.0);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals(40.0, resultado.getPrecoM()),
                                        () -> assertEquals(60.0, resultado.getPrecoG()));
                }

                @Test
                @DisplayName("Quando alteramos um sabor com precos nulos")
                void quandoAlteramosSaborPrecosVazios() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setPrecoM(null);
                        saborPostPutRequestDTO.setPrecoG(null);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertTrue(resultado.getErrors().contains("PrecoM obrigatorio")),
                                        () -> assertTrue(resultado.getErrors().contains("PrecoG obrigatorio")));
                }

                @Test
                @DisplayName("Quando alteramos um sabor com precos inválidos")
                void quandoAlteramosSaborPrecosInvalidos() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setPrecoM(-10.0);
                        saborPostPutRequestDTO.setPrecoG(-250.0);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertTrue(resultado.getErrors()
                                                        .contains("PrecoM deve ser maior que zero")),
                                        () -> assertTrue(resultado.getErrors()
                                                        .contains("PrecoG deve ser maior que zero")));
                }

                @Test
                @DisplayName("Quando alteramos um sabor com precos válidos e inválidos")
                void quandoAlteramosSaborPrecosValidosEInvalidos() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setPrecoM(40.0);
                        saborPostPutRequestDTO.setPrecoG(-250.0);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("PrecoG deve ser maior que zero",
                                                        resultado.getErrors().get(0)));
                }
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação de disponibilidade")
        class SaborVerificacaoDisponibilidade {

                @Test
                @DisplayName("Quando alteramos um sabor com disponibilidade válida")
                void quandoAlteramosSaborDisponibilidadeValida() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setDisponibilidade(false);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertFalse(resultado.getDisponibilidade());
                }

                @Test
                @DisplayName("Quando alteramos um sabor com disponibilidade nula")
                void quandoAlteramosSaborDisponibilidadeNula() throws Exception {
                        // Arrange
                        saborPostPutRequestDTO.setDisponibilidade(null);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("Disponibilidade obrigatoria",
                                                        resultado.getErrors().get(0)));
                }

                @Test
                @DisplayName("Quando alteramos a disponibilidade de um sabor para false")
                void quandoAlteramosDisponibilidadeSaborFalse() throws Exception {
                        // Arrange
                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId() + "/" + false)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .param("disponibilidade", String.valueOf(false))
                                        .content(objectMapper.writeValueAsString(sabor)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertFalse(resultado.getDisponibilidade());
                }

                @Test
                @DisplayName("Quando alteramos a disponibilidade de um sabor para true")
                void quandoAlteramosDisponibilidadeSaborTrue() throws Exception {
                        // Arrange
                        sabor.setDisponibilidade(false);
                        saborRepository.save(sabor);
                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId() + "/" + true)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .param("disponibilidade", String.valueOf(true))
                                        .content(objectMapper.writeValueAsString(sabor)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborPostPutRequestDTO resultado = objectMapper
                                        .readValue(responseJsonString, SaborPostPutRequestDTO.class);

                        // Assert
                        assertTrue(resultado.getDisponibilidade());
                }

                @Test
                @DisplayName("Quando alteramos a disponibilidade de um sabor para false quando já está false")
                void quandoAlteramosDisponibilidadeSaborFalseQuandoJaEstaFalse() throws Exception {
                        // Arrange
                        sabor.setDisponibilidade(false);
                        saborRepository.save(sabor);
                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId() + "/" + false)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .param("disponibilidade", String.valueOf(false))
                                        .content(objectMapper.writeValueAsString(sabor)))
                                        .andExpect(status().isBadRequest()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertEquals("O sabor consultado ja esta indisponivel!", resultado.getMessage());
                }

                @Test
                @DisplayName("Quando alteramos a disponibilidade de um sabor para true quando já está true")
                void quandoAlteramosDisponibilidadeSaborTrueQuandoJaEstaTrue() throws Exception {
                        // Arrange
                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId() + "/" + true)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                        .param("disponibilidade", String.valueOf(true))
                                        .content(objectMapper.writeValueAsString(sabor)))
                                        .andExpect(status().isBadRequest()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertEquals("O sabor consultado ja esta disponivel!", resultado.getMessage());
                }

                @Test
                @DisplayName("Quando alteramos a disponibilidade de um sabor com o código de acesso errado")
                void quandoAlteramosDisponibilidadeSaborCodigoErrado() throws Exception {
                        // Arrange
                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId() + "/" + false)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("saborId", sabor.getId().toString())
                                        .param("estabelecimentoId", estabelecimento.getId().toString())
                                        .param("estabelecimentoCodigoAcesso", "aaaaaa")
                                        .param("disponibilidade", String.valueOf(false))
                                        .content(objectMapper.writeValueAsString(sabor)))
                                        .andExpect(status().isBadRequest()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertEquals("Codigo de acesso invalido!", resultado.getMessage());
                }
        }
}