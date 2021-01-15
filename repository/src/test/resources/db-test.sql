DROP TABLE IF EXISTS gift_certificate_tag, gift_certificate, tag;

CREATE TABLE gift_certificate (
	id LONG AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	description TEXT,
	price NUMERIC(10, 1) NOT NULL,
	duration INTEGER NOT NULL,
	create_date TIMESTAMP DEFAULT NOW() NOT NULL,
	last_update_date TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE tag (
	id LONG AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE gift_certificate_tag (
	gift_certificate_id INTEGER REFERENCES gift_certificate(id) ON DELETE CASCADE,
	tag_id INTEGER REFERENCES tag(id) ON DELETE CASCADE,
	PRIMARY KEY (gift_certificate_id, tag_id)
);

INSERT INTO gift_certificate VALUES
(1, 'Trip', 'Incredible journey. 25 countries. 4 weeks', 5600.0, 60, '2021-01-13T18:27:45.610874', '2021-01-13T18:27:45.610874'),
(2, 'Skydiving', null, 250.0, 30, '2021-01-13T18:27:45.610874', '2021-01-13T18:27:45.610874');

INSERT INTO tag(name) VALUES
('incredible'),
('travel');

INSERT INTO gift_certificate_tag VALUES
(1, 1),
(2, 1),
(2, 2);
