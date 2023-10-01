# 한이음 프로젝트 - 도란도란
## 백엔드(Spring-boot)

프로젝트 생성 - https://start.spring.io/
- spring-version: 3.0.2
- java-version: oracle jdk 17.0.6(https://download.oracle.com/java/17/archive/jdk-17.0.6_windows-x64_bin.exe)
- Dependencies: spring boot devtools, lombok, spring web
- MariaDB : 
```
-- 유저 테이블
CREATE TABLE `User` ( 
  `userId` varchar(100) NOT NULL, -- 유저 ID
  `userName` varchar(100) NOT NULL, -- 유저 이름
  `password` varchar(100) NOT NULL, -- 암호
  `telNum` varchar(100) DEFAULT NULL, -- 전화번호
  `isAdmin` int(11) DEFAULT NULL, -- 관리자 여부 | null -> 일반유저, 아파트 이름 -> 해당 아파트 관리자
  `regDate` timestamp NOT NULL DEFAULT current_timestamp(), 
  PRIMARY KEY (`userId`), 
  KEY `User_FK` (`isAdmin`), 
  CONSTRAINT `User_FK` FOREIGN KEY (`isAdmin`) REFERENCES `ApartInfo` (`apartId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8; 

-- 아파트 정보 테이블
CREATE TABLE `ApartInfo` ( 
  `apartId` int(11) NOT NULL AUTO_INCREMENT, -- 아파트 ID
  `apartName` varchar(100) NOT NULL, -- 아파트 이름
  `location` varchar(100) NOT NULL, -- 아파트 위치
  PRIMARY KEY (`apartId`) 
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8; 

-- 아파트 테이블
CREATE TABLE `Apart` (
  `apartIdx` int(11) NOT NULL AUTO_INCREMENT, -- 아파트 idx
  `apartId` int(11) NOT NULL, -- 아파트 ID
  `dong` varchar(100) NOT NULL, -- 동
  `ho` varchar(100) NOT NULL, -- 호
  PRIMARY KEY (`apartIdx`),
  KEY `Apart_FK_1` (`apartId`),
  CONSTRAINT `Apart_FK_1` FOREIGN KEY (`apartId`) REFERENCES `ApartInfo` (`apartId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;

-- 아파트 유저 테이블
CREATE TABLE `ApartUser` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `apartIdx` int(11) NOT NULL, -- 아파트 idx
  `userId` varchar(100) NOT NULL, -- 유저 ID
  PRIMARY KEY (`idx`),
  KEY `ApartUser_FK` (`apartIdx`),
  KEY `ApartUser_FK_1` (`userId`),
  CONSTRAINT `ApartUser_FK` FOREIGN KEY (`apartIdx`) REFERENCES `Apart` (`apartIdx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ApartUser_FK_1` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 신고내역 테이블
CREATE TABLE `Report` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) NOT NULL, -- 신고자 ID
  `reportDate` timestamp NOT NULL DEFAULT current_timestamp(), -- 신고 시각
  `occurDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00', -- 소음 발생 시각
  `detail` text DEFAULT NULL, -- 상세 내용
  `isCheck` varchar(100) DEFAULT NULL, -- 확인 여부
  `apartId` int(11) NOT NULL, -- 아파트 ID
  PRIMARY KEY (`idx`),
  KEY `report_FK` (`userId`),
  KEY `Report_FK_1` (`apartId`),
  CONSTRAINT `Report_FK_1` FOREIGN KEY (`apartId`) REFERENCES `ApartInfo` (`apartId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_FK` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 소음예고내역 테이블
CREATE TABLE `NoiseSchedule` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) NOT NULL, -- 신고자 ID
  `reportDate` timestamp NOT NULL DEFAULT current_timestamp(), -- 신고 시각
  `startDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00', -- 소음 발생 시작 예정 시각
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00', -- 소음 발생 종료 예정 시각
  `reason` text DEFAULT NULL, -- 소음 발생 이유
  `apartId` int(11) NOT NULL DEFAULT 1, -- 아파트 ID
  PRIMARY KEY (`idx`),
  KEY `report_schedule_FK` (`userId`),
  KEY `NoiseSchedule_FK` (`apartId`),
  CONSTRAINT `NoiseSchedule_FK` FOREIGN KEY (`apartId`) REFERENCES `ApartInfo` (`apartId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_schedule_FK` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 센서 정보 테이블
CREATE TABLE `Sensor` (
  `sensorId` varchar(100) NOT NULL,
  `apartIdx` int(11) NOT NULL, -- 설치된 아파트의 동 호(apart의 apartIdx)
  `location` varchar(100) NOT NULL, -- 상세 위치 ex)주방, 안방 등
  PRIMARY KEY (`sensorId`),
  KEY `Sensor_FK` (`apartIdx`),
  CONSTRAINT `Sensor_FK` FOREIGN KEY (`apartIdx`) REFERENCES `Apart` (`apartIdx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 센서 감지 데이터 테이블
CREATE TABLE `SensorReport` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `sensorId` varchar(100) NOT NULL,
  `noiseLevel` double NOT NULL, -- 소음 정도 ex) dB
  `reportDate` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`idx`),
  KEY `SensorReport_FK` (`sensorId`),
  CONSTRAINT `SensorReport_FK` FOREIGN KEY (`sensorId`) REFERENCES `Sensor` (`sensorId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 유저별 경고 메시지 테이블
CREATE TABLE `WarningMessage` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) NOT NULL,
  `message` varchar(100) NOT NULL,
  `regDate` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`idx`),
  KEY `WarningMessage_FK` (`userId`),
  CONSTRAINT `WarningMessage_FK` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

``` mermaid
classDiagram
direction BT
class Apart {
- Integer apartIdx
- String dong
- String ho
- Integer apartId
  }
  class ApartInfo {
- Integer apartId
- String location
- String apartName
  }
  class ApartUser {
- String userId
- Integer apartIdx
- Integer idx
  }
  class NoiseSchedule {
- Integer idx
- String userId
- String reason
- Timestamp startDate
- Timestamp endDate
- Timestamp reportDate
  }
  class Report {
- Integer apartId
- String detail
- String isCheck
- Integer idx
- String userId
- Timestamp reportDate
- Timestamp occurDate
  }
  class Sensor {
- String location
- String sensorId
- Integer apartIdx
  }
  class SensorReport {
- Double noiseLevel
- Timestamp reportDate
- Integer idx
- String sensorId
  }
  class User {
- String userId
- Integer isAdmin
- Timestamp regDate
- String password
- String telNum
- String userName
  }
  class WarningMessage {
- Integer idx
- String message
- String userId
- Timestamp regDate
  }

Apart  -->  ApartUser
Apart  -->  Sensor
ApartInfo  -->  Apart
ApartInfo  -->  Report
Sensor  -->  SensorReport
User  -->  ApartUser
User  -->  NoiseSchedule
User  -->  Report
User  -->  WarningMessage
ApartInfo --> User
```

## TODO
로그인이 `session`방식에서 `JWT`방식으로 변경됨에 따라 API로직 변경
1. 로그인 확인
   1. 기존에는 `컨트롤러`에서 `HttpSession`객체를 받아와 `getAttribute`메서드를 통해 로그인을 확인
   2. `컨트롤러` 메서드에 `@CustomAuthorization` 어노테이션을 붙이면 로그인 확인을 해줌
      - `@CustomAuthorization(isAdmin = true)`를 사용할 시 관리자 권한인지 확인해줌
      - 상세한 로직은 `modules/CustomAuthorization(Interceptor)`파일 확인
   3. 따라서 해당 어노테이션을 붙인 API의 로직은 요청한 사용자가 인증된 사용자라는 것을 전제로 짜야함
2. API 요청한 유저의 `userId` 얻는 방식
   1. 기존에는 `컨트롤러`에서 `HttpSession`객체를 받아와 `getAttribute`메서드를 통해 `userId`를 얻음
   2. 의존주입으로 `modules/JwtProvider` 클래스를 생성
   3. `컨트롤러` 메서드에서 `HttpServletRequest` 클래스를 인자로 받음
   4. `jwtProvider`객체의 `getToken`메서드와 `getUserId`메서드를 이용
   5. 상세한 로직은 해당 클래스 파일 확인, `User`, `Apart` API 참조

## API 표
### Swagger로 대체
- 서버실행 후 http://localhost:8080/swagger-ui/index.html#/ 접속