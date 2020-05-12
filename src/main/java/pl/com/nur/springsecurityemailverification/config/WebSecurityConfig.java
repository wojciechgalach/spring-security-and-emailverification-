package pl.com.nur.springsecurityemailverification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private DataSource dataSource;
    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/forAdmin").hasRole("ADMIN")
                .antMatchers("/delete/{id}").hasRole("ADMIN")
                .antMatchers("/forUser").hasAnyRole("USER", "ADMIN")
                .antMatchers("/add").hasAnyRole("USER", "ADMIN")
                .antMatchers("/singup").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
                .and()
                .rememberMe().tokenValiditySeconds(1*60*60).rememberMeCookieName("rehresh").rememberMeParameter("remember")
//                .rememberMe().tokenValiditySeconds(1*60*60)
                .tokenRepository(persistentTokenRepository())  // trzymamy sejsje po stornie bazy danych
                .and()
                .logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");;
    }

    @Bean
    PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

}
