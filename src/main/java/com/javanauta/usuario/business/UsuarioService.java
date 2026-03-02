package com.javanauta.usuario.business;


import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario (UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        //ultima linha é a forma mais resumida (clean).
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));


    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new com.henrique.aprendendospring.infrascruture.exceptions.ConflictExcpection("Email já cadastrado" + email);
            }
        } catch (com.henrique.aprendendospring.infrascruture.exceptions.ConflictExcpection e){
            throw new com.henrique.aprendendospring.infrascruture.exceptions.ConflictExcpection("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){ //metodo para encontrar por email
        return usuarioRepository.findByEmail(email).//retorna o usuario interface da repository para encontrar por email no postman
                orElseThrow(()-> new ResourceNotFoundException("usuario não existente " + email));
        //taca o erro de usuario nao existente + mensagem
        //poderia ter sido tratado com != null
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO dto){
      String email = jwtUtil.extratirEmailToken(token.substring(7));
      dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
      //busca o usuario no banco de daos
      Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
              new ResourceNotFoundException("Email não localizado"));
      //difcil de acontecer, visto que estamos procurando um email que já está no token. Ou seja, não tem como retornar null
        //mesclou os dados que recebemos na requisição DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
//        usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));//encripta a senha - cuidado com a dupla encriptação
        //salvou os dados do usuario convertido e depois pegou o retorno e converteu para UsuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    //Criar metodo para transferencia no postman de entity para DTO
    //Criar metodo para update nos dados

}
