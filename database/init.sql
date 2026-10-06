CREATE DATABASE IF NOT EXISTS pet_clinic;

USE pet_clinic;

CREATE TABLE IF NOT EXISTS owners (
    owner_id INT PRIMARY KEY AUTO_INCREMENT,
    owner_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(100),
    address VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS pets (
    pet_id INT PRIMARY KEY AUTO_INCREMENT,
    pet_name VARCHAR(100) NOT NULL,
    species VARCHAR(50),
    breed VARCHAR(50),
    age INT,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES owners(owner_id)
);

CREATE TABLE IF NOT EXISTS vets (
    vet_id INT PRIMARY KEY AUTO_INCREMENT,
    vet_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    phone VARCHAR(15),
    consultation_fee DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS appointments (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    pet_id INT,
    vet_id INT,
    appointment_date DATE,
    appointment_time TIME,
    reason VARCHAR(255),
    status VARCHAR(30) DEFAULT 'BOOKED',
    consultation_fee DECIMAL(10,2),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
    FOREIGN KEY (vet_id) REFERENCES vets(vet_id)
);

INSERT INTO owners
(owner_name, phone, email, address)
VALUES
('Kavi Bharathi', '9876543210', 'kavi@gmail.com', 'Chennai'),
('Arun Kumar', '9876501234', 'arun@gmail.com', 'Tambaram'),
('Priya Sharma', '9123456780', 'priya@gmail.com', 'Avadi'),
('Rahul Raj', '9988776655', 'rahul@gmail.com', 'Guindy');

INSERT INTO pets
(pet_name, species, breed, age, owner_id)
VALUES
('Bruno', 'Dog', 'Labrador', 3, 1),
('Milo', 'Cat', 'Persian', 2, 1),
('Rocky', 'Dog', 'German Shepherd', 5, 2),
('Luna', 'Cat', 'Siamese', 1, 3),
('Max', 'Dog', 'Beagle', 4, 4);

INSERT INTO vets
(vet_name, specialization, phone, consultation_fee)
VALUES
('Dr. Arun', 'General Veterinary', '9000000001', 500.00),
('Dr. Priya', 'Pet Surgery', '9000000002', 1000.00),
('Dr. Karthik', 'Dermatology', '9000000003', 750.00),
('Dr. Meena', 'General Veterinary', '9000000004', 600.00);

DELIMITER //

CREATE TRIGGER update_appointment_status
BEFORE INSERT ON appointments
FOR EACH ROW
BEGIN
    IF NEW.appointment_date < CURDATE() THEN
        SET NEW.status = 'COMPLETED';
    ELSE
        SET NEW.status = 'BOOKED';
    END IF;
END //

DELIMITER ;

INSERT INTO appointments
(pet_id, vet_id, appointment_date, appointment_time, reason, status, consultation_fee)
VALUES
(1, 1, '2026-10-05', '10:00:00', 'Regular Checkup', 'BOOKED', 500.00),
(2, 1, '2026-10-05', '11:00:00', 'Vaccination', 'BOOKED', 500.00),
(3, 2, '2026-10-06', '10:30:00', 'Surgery Consultation', 'BOOKED', 1000.00),
(4, 3, '2026-10-06', '12:00:00', 'Skin Allergy', 'BOOKED', 750.00),
(5, 1, '2026-10-07', '09:30:00', 'General Checkup', 'BOOKED', 500.00),
(1, 4, '2026-10-08', '11:30:00', 'Follow-up', 'BOOKED', 600.00);

DELIMITER //

CREATE PROCEDURE BookPetAppointment(
    IN p_pet_id INT,
    IN p_vet_id INT,
    IN p_appointment_date DATE,
    IN p_appointment_time TIME,
    IN p_reason VARCHAR(255)
)
BEGIN
    INSERT INTO appointments
    (
        pet_id,
        vet_id,
        appointment_date,
        appointment_time,
        reason,
        status,
        consultation_fee
    )
    VALUES
    (
        p_pet_id,
        p_vet_id,
        p_appointment_date,
        p_appointment_time,
        p_reason,
        'BOOKED',
        0.00
    );
END //

DELIMITER ;

DELIMITER //

CREATE FUNCTION CalculateConsultationFee(
    p_vet_id INT
)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE fee DECIMAL(10,2);

    SELECT consultation_fee
    INTO fee
    FROM vets
    WHERE vet_id = p_vet_id;

    RETURN fee;
END //

DELIMITER ;