-- =============================================
-- Script de creación de la base de datos
-- Gestión de Usuarios - Arquitectura Hexagonal
-- =============================================

CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usuario administrador inicial (password: Admin1234!)
INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$placeholderHashReplaceWithRealBCryptHash',
    'ADMIN',
    'ACTIVE'
);

CREATE TABLE IF NOT EXISTS employees (
    id              VARCHAR(36)    NOT NULL PRIMARY KEY,
    document_number VARCHAR(20)    NOT NULL UNIQUE,
    first_name      VARCHAR(100)   NOT NULL,
    last_name       VARCHAR(100)   NOT NULL,
    email           VARCHAR(150)   NOT NULL UNIQUE,
    phone           VARCHAR(20)    NULL,
    base_salary     DECIMAL(12,2)  NOT NULL,
    status          ENUM('ACTIVE','INACTIVE','SUSPENDED','TERMINATED') NOT NULL DEFAULT 'ACTIVE',
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS contracts (
    id              VARCHAR(36)    NOT NULL PRIMARY KEY,
    employee_id     VARCHAR(36)    NOT NULL,
    contract_number VARCHAR(30)    NOT NULL UNIQUE,
    position        VARCHAR(100)   NOT NULL,
    contract_type   ENUM('FIXED_TERM','INDEFINITE','SERVICE','APPRENTICESHIP') NOT NULL,
    start_date      DATE           NOT NULL,
    end_date        DATE           NULL,
    monthly_salary  DECIMAL(12,2)  NOT NULL,
    status          ENUM('ACTIVE','SUSPENDED','FINISHED','CANCELLED') NOT NULL DEFAULT 'ACTIVE',
    created_at      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_employee
        FOREIGN KEY (employee_id) REFERENCES employees(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    INDEX idx_contract_employee_id (employee_id),
    INDEX idx_contract_number (contract_number),
    INDEX idx_contract_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

