package com.javanauta.usuario.business.converter;


import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream()
                .map(this::paraEndereco)
                .toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS) {

        List<Telefone> telefones = new ArrayList<>();

        if (telefoneDTOS != null) {
            for (TelefoneDTO telefoneDTO : telefoneDTOS) {
                telefones.add(paraTelefone(telefoneDTO));
            }
        }

        return telefones;
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }
    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO){

        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefones()))
                .build();

    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS){
        List<EnderecoDTO> enderecos = new ArrayList<>();
        for(Endereco enderecoDTO : enderecoDTOS){
            enderecos.add(paraEnderecoDTO(enderecoDTO));
        }
        return enderecos;
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){//metodo refatorado retirando o DTO do parametro e dos atributos do return
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefoneDTOS) {

        List<TelefoneDTO> telefones = new ArrayList<>();//Nova lista de TelefoneDTO chamada telefones
        if (telefoneDTOS != null) {//evitar nullPointerException
            for (Telefone telefone : telefoneDTOS) {//Loop na list existentee chamado telefoneDTOS
                //no Telefone - é a lista de parametro. No telefone - é a váriavel de loop
                telefones.add(paraTelefoneDTO(telefone));//lista atual: adicione a conversão para telefoneDTO
            }
        }
        return telefones;
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){//metodo de update exclusivo de usuario
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())//Lemrbar que errei o método ao fechar com ()), interrompendo o fluxo do ternário.
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() !=null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() !=null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() !=null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() !=null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() !=null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() !=null ? dto.getCep() : entity.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero() !=null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd() !=null ? dto.getDdd() : entity.getDdd())//troquei o entity pelo dto, visto que estou trabalhando com dtos
                .build();
    }

    public Endereco paraEnderecoEnity(EnderecoDTO dto, Long idUsuario){
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEnity(TelefoneDTO dto, Long idUsuario){
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuario_id(idUsuario)
                .build();
    }

}