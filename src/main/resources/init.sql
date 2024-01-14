CREATE TABLE `event`
(
    `id`           bigint(20) NOT NULL,
    `eventRef`     varchar(100)  DEFAULT NULL,
    `desc1`        varchar(500)  DEFAULT NULL,
    `desc2`        varchar(500)  DEFAULT NULL,
    `desc3`        varchar(500)  DEFAULT NULL,
    `start`        datetime      DEFAULT NULL,
    `end`          datetime      DEFAULT NULL,
    `teacherName`  varchar(2000) DEFAULT NULL,
    `teacherEmail` varchar(500)  DEFAULT NULL,
    `locCode`      varchar(100)  DEFAULT NULL,
    `locAdd1`      varchar(100)  DEFAULT NULL,
    `locAdd2`      varchar(100)  DEFAULT NULL,
    PRIMARY KEY (`id`)
);