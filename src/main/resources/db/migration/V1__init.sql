CREATE TABLE authorization
(
    id                            VARCHAR(255) NOT NULL,
    registered_client_id          VARCHAR(255) NULL,
    principal_name                VARCHAR(255) NULL,
    authorization_grant_type      VARCHAR(255) NULL,
    authorized_scopes             LONGTEXT NULL,
    attributes                    LONGTEXT NULL,
    state                         LONGTEXT NULL,
    authorization_code_value      LONGTEXT NULL,
    authorization_code_issued_at  datetime NULL,
    authorization_code_expires_at datetime NULL,
    authorization_code_metadata   VARCHAR(255) NULL,
    access_token_value            LONGTEXT NULL,
    access_token_issued_at        datetime NULL,
    access_token_expires_at       datetime NULL,
    access_token_metadata         LONGTEXT NULL,
    access_token_type             VARCHAR(255) NULL,
    access_token_scopes           LONGTEXT NULL,
    refresh_token_value           LONGTEXT NULL,
    refresh_token_issued_at       datetime NULL,
    refresh_token_expires_at      datetime NULL,
    refresh_token_metadata        LONGTEXT NULL,
    oidc_id_token_value           LONGTEXT NULL,
    oidc_id_token_issued_at       datetime NULL,
    oidc_id_token_expires_at      datetime NULL,
    oidc_id_token_metadata        LONGTEXT NULL,
    oidc_id_token_claims          LONGTEXT NULL,
    user_code_value               LONGTEXT NULL,
    user_code_issued_at           datetime NULL,
    user_code_expires_at          datetime NULL,
    user_code_metadata            LONGTEXT NULL,
    device_code_value             LONGTEXT NULL,
    device_code_issued_at         datetime NULL,
    device_code_expires_at        datetime NULL,
    device_code_metadata          LONGTEXT NULL,
    CONSTRAINT pk_authorization PRIMARY KEY (id)
);

CREATE TABLE authorization_consent
(
    registered_client_id VARCHAR(255) NOT NULL,
    principal_name       VARCHAR(255) NOT NULL,
    authorities          LONGTEXT NULL,
    CONSTRAINT pk_authorizationconsent PRIMARY KEY (registered_client_id, principal_name)
);

CREATE TABLE client
(
    id                            VARCHAR(255) NOT NULL,
    client_id                     VARCHAR(255) NULL,
    client_id_issued_at           datetime NULL,
    client_secret                 VARCHAR(255) NULL,
    client_secret_expires_at      datetime NULL,
    client_name                   VARCHAR(255) NULL,
    client_authentication_methods LONGTEXT NULL,
    authorization_grant_types     LONGTEXT NULL,
    redirect_uris                 LONGTEXT NULL,
    post_logout_redirect_uris     LONGTEXT NULL,
    scopes                        LONGTEXT NULL,
    client_settings               LONGTEXT NULL,
    token_settings                LONGTEXT NULL,
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id         BINARY(16) NOT NULL,
    created_at datetime NULL,
    role_type  SMALLINT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE sessions
(
    id           BINARY(16) NOT NULL,
    created_at   datetime NULL,
    token        VARCHAR(4096) NULL,
    expirying_at datetime NULL,
    user_id      BINARY(16) NULL,
    status       SMALLINT NULL,
    CONSTRAINT pk_sessions PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         BINARY(16) NOT NULL,
    created_at datetime NULL,
    name       VARCHAR(255) NULL,
    email_id   VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    roles_id BINARY(16) NOT NULL,
    users_id BINARY(16) NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (roles_id, users_id)
);

ALTER TABLE sessions
    ADD CONSTRAINT FK_SESSIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES roles (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (users_id) REFERENCES users (id);