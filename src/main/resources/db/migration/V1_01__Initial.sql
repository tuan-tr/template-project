CREATE TABLE _user (
  created_at timestamptz NOT NULL,
  created_by varchar NOT NULL,
  updated_at timestamptz NOT NULL,
  updated_by varchar NOT NULL,
  version int,
  id varchar PRIMARY KEY,
  status varchar NOT NULL,
  name varchar NOT NULL,
  effective_start timestamptz,
  effective_end timestamptz
);

CREATE TABLE _group (
  created_at timestamptz NOT NULL,
  created_by varchar NOT NULL,
  updated_at timestamptz NOT NULL,
  updated_by varchar NOT NULL,
  id varchar PRIMARY KEY,
  status varchar NOT NULL,
  name varchar NOT NULL
);

CREATE TABLE user_group (
  created_at timestamptz NOT NULL,
  created_by varchar NOT NULL,
  updated_at timestamptz NOT NULL,
  updated_by varchar NOT NULL,
  id varchar PRIMARY KEY,
  user_id varchar NOT NULL,
  group_id varchar NOT NULL,
  UNIQUE (group_id, user_id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES _user (id),
  CONSTRAINT group_fk FOREIGN KEY (group_id) REFERENCES _group (id)
);
