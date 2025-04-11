CREATE TABLE reservations (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              owner_id BIGINT NOT NULL,
                              pet_id BIGINT NOT NULL,
                              vet_id BIGINT NOT NULL,
                              reservation_date DATE NOT NULL,
                              status VARCHAR(20) NOT NULL
);