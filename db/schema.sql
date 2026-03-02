CREATE TABLE IF NOT EXISTS claim_user (
    user_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS audit_log (
    audit_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(255),
    resource VARCHAR(255),
    timestamp TIMESTAMP,
    metadata TEXT
);

CREATE TABLE IF NOT EXISTS data_feed (
    feed_id BIGSERIAL PRIMARY KEY,
    feed_type VARCHAR(50),
    source_system VARCHAR(255),
    last_sync_date TIMESTAMP,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS claim_raw (
    raw_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    payload_json TEXT,
    ingested_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS claim_kpi (
    kpi_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    metric_name VARCHAR(255),
    metric_value NUMERIC(18,2),
    metric_date DATE
);

CREATE TABLE IF NOT EXISTS risk_indicator (
    indicator_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    indicator_type VARCHAR(50),
    severity VARCHAR(50),
    triggered_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS risk_score (
    score_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    score_value NUMERIC(18,2),
    computed_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS denial_pattern (
    pattern_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    denial_code VARCHAR(100),
    reason VARCHAR(255),
    occurrence_date DATE
);

CREATE TABLE IF NOT EXISTS leakage_flag (
    leakage_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    leakage_type VARCHAR(50),
    estimated_loss NUMERIC(18,2),
    identified_date DATE
);

CREATE TABLE IF NOT EXISTS adjuster_performance (
    perf_id BIGSERIAL PRIMARY KEY,
    adjuster_id VARCHAR(100),
    claims_handled INTEGER,
    avg_tat NUMERIC(18,2),
    quality_score NUMERIC(18,2),
    period VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS sla_violation (
    violation_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    violation_type VARCHAR(100),
    violation_date DATE
);

CREATE TABLE IF NOT EXISTS claim_cost (
    cost_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    cost_type VARCHAR(50),
    amount NUMERIC(18,2),
    cost_date DATE
);

CREATE TABLE IF NOT EXISTS claim_reserve (
    reserve_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    reserve_amount NUMERIC(18,2),
    updated_date DATE
);

CREATE TABLE IF NOT EXISTS aging_record (
    aging_id BIGSERIAL PRIMARY KEY,
    claim_id VARCHAR(100),
    aging_days INTEGER,
    aging_bucket VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS analytics_report (
    report_id BIGSERIAL PRIMARY KEY,
    scope VARCHAR(255),
    metrics TEXT,
    generated_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS notification (
    notification_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    message TEXT,
    category VARCHAR(50),
    status VARCHAR(50),
    created_date TIMESTAMP
);
