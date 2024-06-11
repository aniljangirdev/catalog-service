CREATE TABLE user_data (
      id                  BIGSERIAL PRIMARY KEY NOT NULL,
      username             varchar(255) NOT NULL,
      email_id            varchar(255) UNIQUE NOT NULL,
      created_date        timestamp NOT NULL,
      last_modified_date  timestamp NOT NULL,
      version             integer NOT NULL
);