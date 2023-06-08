DROP TABLE IF EXISTS `tb_person`;

CREATE TABLE `tb_person` (
  `id_person` bigint NOT NULL AUTO_INCREMENT,
  `ds_first_name` varchar(80) NOT NULL,
  `ds_last_name` varchar(80) NOT NULL,
  `ds_address` varchar(100) NOT NULL,
  `ds_gender` varchar(6) NOT NULL,
  PRIMARY KEY (`id_person`)
)