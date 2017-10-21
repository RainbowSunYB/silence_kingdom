CREATE TABLE IF NOT EXISTS history (
  id                   INTEGER PRIMARY KEY,
  total_minutes        INTEGER,
  rage_point_threshold INTEGER,
  average_rage_point   REAL,
  card_id              INTEGER,
  is_succeed           TEXT,
  is_completed         TEXT,
  created_time         TEXT
);