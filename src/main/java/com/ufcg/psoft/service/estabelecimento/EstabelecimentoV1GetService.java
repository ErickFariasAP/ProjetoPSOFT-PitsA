package com.ufcg.psoft.commerce.service.estabelecimento;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.exception.estabelecimento.EstabelecimentoInexistenteException;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.sabor.Sabor;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;

@Service
public class EstabelecimentoV1GetService implements EstabelecimentoGetService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public EstabelecimentoResponseDTO get(Long id) {
        if (!estabelecimentoRepository.existsById(id)) {
            throw new EstabelecimentoInexistenteException();
        }
        EstabelecimentoResponseDTO estabelecimentoGetRequestDTO = modelMapper.map(estabelecimentoRepository.findById(id), EstabelecimentoResponseDTO.class);
        return estabelecimentoGetRequestDTO;
    }

    @Override
    public List<EstabelecimentoResponseDTO> getAll() {
        List<Estabelecimento> todosEstabelecimentoes = estabelecimentoRepository.findAll();
        return todosEstabelecimentoes.stream()
                .map(estabelecimento -> modelMapper.map(estabelecimento, EstabelecimentoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Sabor> getCardapio(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoInexistenteException::new);
        return estabelecimento.getSabores();
    }

    @Override
    public Set<Sabor> getCardapioPorTipo(Long id, String tipo) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoInexistenteException::new);

        if (tipo.equals("salgado")) {
            Set<Sabor> saboresSalgados = estabelecimento.getSabores().stream()
                    .filter(sabor -> "salgado".equals(sabor.getTipo()))
                    .collect(Collectors.toSet());
            return saboresSalgados;
        } else {
           Set<Sabor> saboresDoces = estabelecimento.getSabores().stream()
                    .filter(sabor -> "doce".equals(sabor.getTipo()))
                    .collect(Collectors.toSet());
            return saboresDoces; 
        }
    }
}
