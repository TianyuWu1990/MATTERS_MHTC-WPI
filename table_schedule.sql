
-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               PostgreSQL 9.3.5, compiled by Visual C++ build 1600, 64-bit
-- Server OS:                    
-- HeidiSQL Version:             8.3.0.4823
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table mhtc_sch.schedules
CREATE TABLE IF NOT EXISTS mhtc_sch."schedules" (
	"sched_name" CHARACTER VARYING NULL,
	"sched_description" TEXT NULL,
	"sched_job" TEXT NULL,
	"sched_date" TEXT NULL,
	"job_name" CHARACTER VARYING NOT NULL,
	"sched_cron" BOOLEAN NOT NULL,
	PRIMARY KEY ("job_name")
);

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
