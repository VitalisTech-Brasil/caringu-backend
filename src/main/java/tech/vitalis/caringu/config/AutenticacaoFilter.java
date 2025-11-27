package tech.vitalis.caringu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.vitalis.caringu.service.AutenticacaoService;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoService autenticacaoService;

    private final GerenciadorTokenJwt jwtTokenManager;

    @Autowired
    private CookieJwtUtil cookieJwtUtil;

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String jwtToken = null;

        // Primeiro tenta obter o token do cookie (mais seguro)
        jwtToken = cookieJwtUtil.getJwtFromCookie(request);
        
        // Se não encontrar no cookie, tenta no header Authorization (compatibilidade)
        if (jwtToken == null) {
            String requestTokenHeader = request.getHeader("Authorization");
            if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
            }
        }

        if (jwtToken != null) {
            if (!jwtToken.contains(".") || jwtToken.chars().filter(ch -> ch == '.').count() != 2) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                username = jwtTokenManager.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException exception) {

                LOGGER.info("[FALHA AUTENTICACAO] - Token expirado, pessoa: {} - {}",
                        exception.getClaims().getSubject(), exception.getMessage());

                LOGGER.trace("[FALHA AUTENTICACAO] - stack trace: %s", exception);

                // Remove o cookie expirado se existir
                cookieJwtUtil.removeJwtCookie(response);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                Map<String, Object> errorDetails = new LinkedHashMap<>();
                errorDetails.put("timestamp", Instant.now().toString());
                errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
                errorDetails.put("error", "TokenExpired");
                errorDetails.put("message", "JWT token expired");
                errorDetails.put("path", request.getRequestURI());

                ObjectMapper mapper = new ObjectMapper();
                String responseBody = mapper.writeValueAsString(errorDetails);

                response.getWriter().write(responseBody);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            addUsernameInContext(request, username, jwtToken);
        }

        filterChain.doFilter(request, response);
    }

    private void addUsernameInContext(HttpServletRequest request, String username, String jwtToken) {

        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        if (jwtTokenManager.validateToken(jwtToken, userDetails)) {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
