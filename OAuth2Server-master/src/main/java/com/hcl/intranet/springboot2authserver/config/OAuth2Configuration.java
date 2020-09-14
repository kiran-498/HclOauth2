package com.hcl.intranet.springboot2authserver.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.hcl.intranet.springboot2authserver.repository.UserJDBCRepository;
import com.hcl.intranet.springboot2authserver.repository.UserRepository;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

	@Value("${check-user-scopes}")
	private Boolean checkUserScopes;

	/*@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;*/

	@Autowired
	private UserDetailsService userDetailsService;

/*	@Autowired
	private ClientDetailsService clientDetailsService;*/

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserJDBCRepository userRepository;
	
	public ClientDetailsService clientDetailsService() {
        return new ClientDetailsService() {
            @Override
            public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
            	
            	System.out.println("client id: " +clientId);
            	//clientId = "clientId";
            	if(userRepository.checkUser(clientId).equalsIgnoreCase("invalid")) {
            		System.out.println("user checked......................");
            		throw new ClientRegistrationException("client details not found");
            	}
                BaseClientDetails details = new BaseClientDetails();
                details.setClientId(clientId);
                details.setClientSecret("{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi");
                details.setAuthorizedGrantTypes(Arrays.asList("password","refresh_token") );
                details.setRefreshTokenValiditySeconds(10000);
                details.setScope(Arrays.asList("role_hr"));
                details.setRegisteredRedirectUri(Collections.singleton("http://anywhere.com"));
                details.setResourceIds(Arrays.asList("USER_ADMIN_RESOURCE"));
                Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
                details.setAuthorities(authorities);
                return details;
            }
        };
    }  
	
	@Bean
	public OAuth2RequestFactory requestFactory() {
		CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailsService());
		requestFactory.setCheckUserScopes(true);
		return requestFactory;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		/*JwtAccessTokenConverter converter = new CustomTokenEnhancer();
		converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "password".toCharArray()).getKeyPair("jwt"));
		System.out.println("jwt token converter is : "+ converter.toString());
		return converter; */
		 final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        //converter.setSigningKey("123");
	        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
	        		new ClassPathResource("jwt.jks"), "password".toCharArray());
	         converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
	        return converter;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
		  clients.withClientDetails(clientDetailsService());
	}
	
	
	@Bean
	public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
		return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
	}
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
		if (checkUserScopes)
			endpoints.requestFactory(requestFactory());
	}
	@Bean
	public AuthorizationServerTokenServices authorizationServerTokenServices() throws Exception {
	        DefaultTokenServices tokenServices = new DefaultTokenServices();
	        tokenServices.setTokenStore(tokenStore());
	        tokenServices.setSupportRefreshToken(true);
	        tokenServices.setClientDetailsService(clientDetailsService());
	        tokenServices.setRefreshTokenValiditySeconds(Integer.MAX_VALUE);
	        return tokenServices;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}