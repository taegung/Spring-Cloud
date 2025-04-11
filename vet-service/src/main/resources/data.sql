INSERT IGNORE INTO vets VALUES (1, 'James', 'Carter');
INSERT IGNORE INTO vets VALUES (2, 'Helen', 'Leary');
INSERT IGNORE INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT IGNORE INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT IGNORE INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT IGNORE INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT IGNORE INTO specialties VALUES (1, 'radiology');
INSERT IGNORE INTO specialties VALUES (2, 'surgery');
INSERT IGNORE INTO specialties VALUES (3, 'dentistry');

INSERT IGNORE INTO vet_specialties VALUES (2, 1);
INSERT IGNORE INTO vet_specialties VALUES (3, 2);
INSERT IGNORE INTO vet_specialties VALUES (3, 3);
INSERT IGNORE INTO vet_specialties VALUES (4, 2);
INSERT IGNORE INTO vet_specialties VALUES (5, 1);

-- 수의사 ID가 1인 경우
-- 월요일부터 일요일까지 모두 가능한 경우 (비트마스크: 1111111, 127)
INSERT INTO vet_availability (vet_id, available_days) VALUES
                                                          (1, 127);

-- 수의사 ID가 2인 경우
-- 월요일, 화요일, 토요일 가능한 경우 (비트마스크: 1000010, 66)
INSERT INTO vet_availability (vet_id, available_days) VALUES
    (2, 66);

-- 수의사 ID가 3인 경우
-- 화요일, 수요일, 목요일 가능한 경우 (비트마스크: 0011100, 28)
INSERT INTO vet_availability (vet_id, available_days) VALUES
    (3, 28);