CREATE SEQUENCE "ARCHIVEPROCESSSEQ" AS INTEGER  START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 NO CYCLE CACHE 50 NO ORDER;

CREATE TABLE "ARCH_CTL" (
		"CASE_ID" VARCHAR(64 OCTETS),
		"PROCESS_BPDID" VARCHAR(64 OCTETS),
		"PROCESS_STATE" XML,
		"PROCESS_TYPE" VARCHAR(128 OCTETS),
		"PROPERTIES" CLOB(10240 OCTETS) INLINE LENGTH 116,
		"CREATE_DT" VARCHAR(40 OCTETS),
		"COMPLETE_DT" VARCHAR(40 OCTETS),
		"REQUEST_DT" TIMESTAMP,
		"ARCHIVE_DT" TIMESTAMP,
		"DELETE_DT" TIMESTAMP
	)
	ORGANIZE BY ROW
	DATA CAPTURE NONE
	COMPRESS NO;

CREATE TABLE "PROCESSARCHIVAL" (
		"PROCESSARCHIVAL_ID" BIGINT NOT NULL,
		"CASENUMBER" VARCHAR(64 OCTETS) NOT NULL,
		"BPDID" VARCHAR(64 OCTETS),
		"PROCESSTYPE" VARCHAR(128 OCTETS),
		"DATE_VALUE" DATE,
		"TIME_VALUE" TIME,
		"DATA" XML,
		"CREATEDATE" DATE,
		"COMPLETEDATE" DATE
	)
	ORGANIZE BY ROW
	DATA CAPTURE NONE
	COMPRESS NO;

CREATE TABLE "PROCESSARCHIVAL_PROPERTIES" (
		"PROCESSARCHIVAL_ID" BIGINT NOT NULL,
		"KEY" VARCHAR(128 OCTETS) NOT NULL,
		"VALUE" VARCHAR(128 OCTETS) NOT NULL
	)
	ORGANIZE BY ROW
	DATA CAPTURE NONE
	COMPRESS NO;

CREATE INDEX "IDX1903280354370"
	ON "PROCESSARCHIVAL_PROPERTIES"
	("PROCESSARCHIVAL_ID"		ASC,
	  "VALUE"		ASC,
	  "KEY"		ASC)
	MINPCTUSED 0
	ALLOW REVERSE SCANS
	PAGE SPLIT SYMMETRIC
	COLLECT SAMPLED DETAILED STATISTICS
	COMPRESS NO;

CREATE INDEX "IDX1903280355210"
	ON "PROCESSARCHIVAL"
	("BPDID"		ASC,
	  "PROCESSARCHIVAL_ID"		DESC)
	MINPCTUSED 0
	ALLOW REVERSE SCANS
	PAGE SPLIT SYMMETRIC
	COLLECT SAMPLED DETAILED STATISTICS
	COMPRESS NO;

CREATE INDEX "IDX1903280355220"
	ON "PROCESSARCHIVAL"
	("CASENUMBER"		ASC)
	MINPCTUSED 0
	ALLOW REVERSE SCANS
	PAGE SPLIT SYMMETRIC
	COLLECT SAMPLED DETAILED STATISTICS
	COMPRESS NO;

CREATE INDEX "IDX2102120351300"
	ON "ARCH_CTL"
	("PROCESS_BPDID"		ASC,
	  "CASE_ID"		DESC)
	MINPCTUSED 0
	ALLOW REVERSE SCANS
	PAGE SPLIT SYMMETRIC
	COLLECT SAMPLED DETAILED STATISTICS
	COMPRESS NO;

CREATE UNIQUE INDEX "IDX1903280354510"
	ON "PROCESSARCHIVAL_PROPERTIES"
	("PROCESSARCHIVAL_ID"		ASC,
	  "KEY"		ASC)
INCLUDE ("VALUE")
	MINPCTUSED 0
	ALLOW REVERSE SCANS
	PAGE SPLIT SYMMETRIC
	COLLECT SAMPLED DETAILED STATISTICS
	COMPRESS NO;

CREATE UNIQUE INDEX "PK_PROCESSARCHIVAL"
	ON "PROCESSARCHIVAL"
	("PROCESSARCHIVAL_ID"		ASC)
	MINPCTUSED 0
	ALLOW REVERSE SCANS
	PAGE SPLIT SYMMETRIC
	COMPRESS NO;

CREATE UNIQUE INDEX "PK_PROCESSARCHIVAL_PROPERTIES"
	ON "PROCESSARCHIVAL_PROPERTIES"
	("PROCESSARCHIVAL_ID"		ASC,
	  "KEY"		ASC)
	MINPCTUSED 0
	ALLOW REVERSE SCANS
	PAGE SPLIT SYMMETRIC
	COMPRESS NO;

ALTER TABLE "PROCESSARCHIVAL" ADD CONSTRAINT "PK_PROCESSARCHIVAL" PRIMARY KEY
	("PROCESSARCHIVAL_ID");

ALTER TABLE "PROCESSARCHIVAL_PROPERTIES" ADD CONSTRAINT "PK_PROCESSARCH_PROP" PRIMARY KEY
	("PROCESSARCHIVAL_ID",
	 "KEY");