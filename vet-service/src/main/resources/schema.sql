DROP DATABASE petclinic_vet;
CREATE DATABASE petclinic_vet;
use petclinic_vet;

CREATE TABLE IF NOT EXISTS vets (
                                    id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    INDEX(last_name)
    ) engine=InnoDB;

CREATE TABLE IF NOT EXISTS specialties (
                                           id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(80),
    INDEX(name)
    ) engine=InnoDB;

CREATE TABLE IF NOT EXISTS vet_specialties (
                                               vet_id BIGINT(20) UNSIGNED NOT NULL,
    specialty_id BIGINT(20) UNSIGNED NOT NULL,
    FOREIGN KEY (vet_id) REFERENCES vets(id),
    FOREIGN KEY (specialty_id) REFERENCES specialties(id),
    UNIQUE (vet_id,specialty_id)
    ) engine=InnoDB;

CREATE TABLE vet_availability (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  vet_id BIGINT(20) UNSIGNED NOT NULL,
                                  available_days TINYINT UNSIGNED, -- 요일 정보는 최대 7비트만 사용하므로 TINYINT로 저장 가능
                                  FOREIGN KEY (vet_id) REFERENCES vets(id)
);
