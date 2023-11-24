package com.example.obspringsecurityjwt.security.config;

import com.example.obspringsecurityjwt.security.jwt.JwtAuthEntryPoint;
import com.example.obspringsecurityjwt.security.jwt.JwtRequestFilter;
import com.example.obspringsecurityjwt.security.jwt.JwtTokenUtil;
import com.example.obspringsecurityjwt.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


// Punto principal, corazon de la seguridad se define aqui
/**
 * Clase para la configuración de seguridad Spring Security
 */
@Configuration // indica que esta clase es de configuracion, va a tener metodos que van a estar marcados con la anotacion @Bean
@EnableWebSecurity // permite a Spring aplicar esta configuracion a la configuracion de seguridad global
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;



    @Autowired
    JwtTokenUtil jwtTokenUtil;

    AuthenticationManager authenticationManager;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ================ CREACIÓN DE BEANS ======================
    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }


    //
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    //que PasswordEncoder voy a utilizar, en este caso BCrypt


    /**
     *  Configuracion global de CORS para toda la aplicacion
     *  para que se pueda acceder a la aplicacion desde un dominio u otro, prohibir que se acceda a mi backend desde diferentes dominios
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(List.of("http://localhost:4200", "https://angular-springboot-*.vercel.app"));
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200", "https://angular-springboot1-beta.vercel.app"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean  //este ya lo hice cuando empece seguridad por primera vez, solo que incorpore mas cosas
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //va a tener toda la cadena de filtros que se va a ir ejecutando
        http.csrf(AbstractHttpConfigurer::disable) // Cross-Site Request Forgery CSRF
                .cors(AbstractHttpConfigurer::disable) // CORS (Cross-origin resource sharing)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) //para gestion de excepciones
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // para indicar que no quiero que no haya sesion
                //antMatchers URL que voy a permitir
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .requestMatchers("/api/hello/**").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/").permitAll());
//                .anyRequest().authenticated()); //el resto tiene que estar autenticada


        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
