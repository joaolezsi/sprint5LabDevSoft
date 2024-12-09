package com.example.demo.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.EnderecoDTO;
import com.example.demo.Model.Endereco;
import com.example.demo.Repository.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Endereco addEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = convertToEndereco(enderecoDTO);
        enderecoRepository.save(endereco);
        return endereco;
    }

    public Optional<Endereco> getEndereco(int id) {
        return enderecoRepository.findById(id);
    }

    @Transactional
    public Endereco updateEndereco(EnderecoDTO enderecoDTO, int id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        Endereco endereco = enderecoOptional.get();
        if (enderecoOptional.isPresent()) {
            endereco.editEndereco(enderecoDTO);
            enderecoRepository.save(endereco); // O método save realiza update se a entidade já existir
        }
        return endereco;
    }

    @Transactional
    public void deleteEndereco(int id) {
        enderecoRepository.deleteById(id);
    }

    private Endereco convertToEndereco(EnderecoDTO dto) {
        return new Endereco(dto.rua, dto.numero, dto.bairro, dto.cidade, dto.estado, dto.cep);
    }
}
