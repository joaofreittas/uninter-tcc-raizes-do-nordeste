package com.uninter.raizesdonordeste.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthConstants {

    // Authorization
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    public static final int BEARER_LENGTH = 7;

    // Documentation
    public static final String API_TITLE = "Gerenciador de Unidades";
    public static final String PROJECT_DESCRIPTION = "Projeto que cuida de todo o gerenciamento de unidades, cadastro de funcionários, promoções, unidades, desativação, estoque etc.";
    public static final String PROJECT_VERSION = "v1.0.0";
    public static final String CONTACT_NAME = "João Lucas M Freitas.";
    public static final String CONTACT_EMAIL = "joao.frhb@gmail.com";
    public static final String BEARER_AUTH = "bearerAuth";
    public static final String BEARER_SCHEME = "bearer";
    public static final String BEARER_FORMAT = "JWT";
    public static final String AUTH_DESCRIPTION = "Insira o token JWT obtido no endpoint /api/auth/login";

}
