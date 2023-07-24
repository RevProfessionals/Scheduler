CREATE TABLE event_types (
  event_type_id SERIAL PRIMARY KEY,
  type_name VARCHAR(255)
);

INSERT INTO event_types (type_name) VALUES
  ('Meeting'),
  ('Conference'),
  ('Workshop'),
  ('Seminar');

CREATE TABLE states (
  state_id SERIAL PRIMARY KEY,
  name VARCHAR(255)
);

INSERT INTO states (name) VALUES
  ('California'),
  ('New York'),
  ('Texas'),
  ('Florida');

CREATE TABLE roles (
  role_id SERIAL PRIMARY KEY,
  name VARCHAR(255)
);

INSERT INTO roles (name) VALUES
  ('Admin'),
  ('Organizer'),
  ('Participant');

CREATE TABLE locations (
  location_id SERIAL PRIMARY KEY,
  street_address VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  state_id INTEGER REFERENCES states (state_id)
);

INSERT INTO locations (street_address, city, state_id) VALUES
  ('123 Main St', 'Los Angeles', 1),
  ('456 Broadway St', 'New York', 2),
  ('789 Elm Ave', 'Dallas', 3),
  ('101 Palm Blvd', 'Miami', 4);

CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role_id INTEGER REFERENCES roles (role_id)
);

INSERT INTO users (first_name, last_name, email, password, role_id) VALUES
  ('John', 'Doe', 'john.doe@example.com', 'password123', 1),
  ('Jane', 'Smith', 'jane.smith@example.com', 'abcdefg123', 2),
  ('Bob', 'Johnson', 'bob.johnson@example.com', 'testpass', 3);

CREATE TABLE events (
  event_id SERIAL PRIMARY KEY,
  event_name VARCHAR(255) NOT NULL,
  event_start_date DATE NOT NULL,
  event_end_date DATE NOT NULL,
  event_start_time TIME NOT NULL,
  event_end_time TIME NOT NULL,
  type_id INTEGER REFERENCES event_types (event_type_id),
  location_id INTEGER REFERENCES locations (location_id),
  owner_id INTEGER REFERENCES users (user_id)
);

INSERT INTO events (event_name, event_start_date, event_end_date, event_start_time, event_end_time, type_id, location_id, owner_id) VALUES
  ('Team Meeting', '2023-07-26', '2023-07-26', '10:00:00', '11:30:00', 1, 1, 1),
  ('Conference XYZ', '2023-08-15', '2023-08-17', '09:00:00', '17:00:00', 2, 2, 2),
  ('Workshop ABC', '2023-09-05', '2023-09-06', '13:30:00', '16:00:00', 3, 3, 3);

CREATE TABLE participant (
  participant_id SERIAL PRIMARY KEY,
  event_id INTEGER REFERENCES events (event_id),
  user_id INTEGER REFERENCES users (user_id)
);

INSERT INTO participant (event_id, user_id) VALUES
  (1, 2),
  (2, 1),
  (3, 3);

CREATE TABLE shared_user (
  shared_user_id SERIAL PRIMARY KEY,
  owner_id INTEGER REFERENCES users (user_id),
  shared_id INTEGER REFERENCES users (user_id),
  role_id INTEGER REFERENCES roles (role_id)
);

INSERT INTO shared_user (owner_id, shared_id, role_id) VALUES
  (1, 2, 3),
  (2, 1, 2),
  (3, 3, 1);
