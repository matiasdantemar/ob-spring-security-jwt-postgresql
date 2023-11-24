package com.example.obspringsecurityjwt.security.service;


import com.example.obspringsecurityjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Autentica un usuario de la base de datos
 *
 * Authentication Manager llama al método loadUserByUsername de esta clase
 * para obtener los detalles del usuario de la base de datos cuando
 * se intente autenticar un usuario
 */
// como se va a autenticar el usuario, sirve para cargar datos de usuario para poder autenticar
@Service // servicio clase que se situa entre el controlador y el repositorio
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // sirve para extraer los usuarios de la base de datos


    //cargamos nuestro codigo spring, para que luego cuando spring tenga que autenticar, logar etc va a saber como tiene que cargar el usuario, como comprobar las cosas
    @Override //busca en BD el usuario en base al username que se supone que es unico
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.obspringsecurityjwt.domain.User user = userRepository.findByUsername(username)//se extrae el usuario y se guarda en un objeto
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username)); //lamba en caso de que no lo encuentre lanza una excepcion

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),new ArrayList<>());

        //existen dos clases que se llaman User, entonces se utiliza el itirerario completo del paquete donde recibe esas clases
        // com.example.obspringsecurityjwt.domain.User Pertenece a nuestra clase
        // org.springframework.security.core.userdetails.User Pertenece a spring
    }
}
