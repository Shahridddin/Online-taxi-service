package uz.pdp.online_taxi_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import uz.pdp.online_taxi_service.entity.AuthUser;
import uz.pdp.online_taxi_service.repository.AuthUserRepository;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthUserRepository authUserRepository;
    private final CustomAuthenticationSuccessHandler successHandler;

    private static final String[] PUBLIC_PAGES = {
            "/auth/login",
            "/auth/logout",
            "/auth/register",
            "/auth/create",
            "/auth/logout/account",
            "/auth/login/page",
            "/css/**",
            "/js/**"
    };

    private static final String[] ADMIN_PAGES = {
            "/admin/page",
            "/auth/driver/register",
            "/auth/driver/create",

            "/auth/active/drivers",
            "/auth/active/driver/delete/{id}",
            "/auth/active/driver/block/{id}",

            "/auth/inactive/drivers",
            "/auth/inactive/driver/delete/{id}",
            "/auth/inactive/driver/inBlock/{id}",

            "/auth/active/users",
            "/auth/active/user/delete/{id}",
            "/auth/active/user/block/{id}",

            "/auth/inactive/users",
            "/auth/inactive/user/delete/{id}",
            "/auth/inactive/user/inBlock/{id}",

            "/auth/ticket",
            "/auth/ticket/create/{id}",

            "/auth/ticket/list",
            "auth/ticket/delete/{id}"
    };

    private static final String[] USERS_PAGES = {
            "/user/page",
            "/user/search/list"
    };

    public SecurityConfiguration(AuthUserRepository authUserRepository, CustomAuthenticationSuccessHandler successHandler) {
        this.authUserRepository = authUserRepository;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(PUBLIC_PAGES).permitAll()

                        .requestMatchers(USERS_PAGES).hasRole("USER")

                        .requestMatchers(ADMIN_PAGES).hasRole("ADMIN")


                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService customUserDetails() {
        return username -> {
            Optional<AuthUser> byUsername = authUserRepository.findByUsername(username);
            if (byUsername.isEmpty()) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            AuthUser authUser = byUsername.get();

            return new CustomUserDetails(
                    authUser.getUsername(),
                    authUser.getPassword(),
                    authUser.getEmail(),
                    authUser.isActive(),
                    authUser.getRole()
            );
        };
    }
}
