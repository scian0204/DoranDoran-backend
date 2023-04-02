# 한이음 프로젝트 - 도란도란
## 백엔드(Spring-boot)

프로젝트 생성 - https://start.spring.io/
- spring-version: 3.0.2
- java-version: oracle jdk 17.0.6(https://download.oracle.com/java/17/archive/jdk-17.0.6_windows-x64_bin.exe)
- Dependencies: spring boot devtools, lombok, spring web
- MariaDB : 
```
// 아파트 테이블
CREATE TABLE `Apart` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `apartName` varchar(100) NOT NULL,
  `dong` varchar(100) NOT NULL,
  `ho` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  PRIMARY KEY (`idx`),
  KEY `Apart_FK` (`userId`),
  CONSTRAINT `Apart_FK` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

// 유저 테이블
CREATE TABLE `User` (
  `userId` varchar(100) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `passsword` varchar(100) NOT NULL,
  `telNum` varchar(100) DEFAULT NULL,
  `isAdmin` varchar(100) DEFAULT NULL,
  `regDate` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

// 신고내역 테이블
CREATE TABLE `report` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) NOT NULL,
  `reportDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `occurDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `detail` text DEFAULT NULL,
  `isCheck` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `report_FK` (`userId`),
  CONSTRAINT `report_FK` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

// 소음예고내역 테이블
CREATE TABLE `report_schedule` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) NOT NULL,
  `reportDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `scheduleDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `reason` text DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `report_schedule_FK` (`userId`),
  CONSTRAINT `report_schedule_FK` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## TODO
1. 아파트 DB
   1. 아파트 이름
   2. 동
   2. 호
   3. 세대주 유저 id
2. 유저 DB
   1. 유저id
   2. 유저 이름
   3. 비밀번호
   4. 전화번호
   5. 관리자 여부 - null - 관리자 아님, 아파트 이름 - 그 아파트의 관리자
3. 신고내역 DB
   1. idx
   2. 신고자 id
   3. 신고 시간
   4. 소음 발생 시간
   5. 소음 상세 내용
   6. 확인 여부
4. 소음예고내역 DB
   1. idx
   2. 신고자 id
   3. 신고 시간
   4. 소음 발생 예정 시간
   5. 소음 발생 이유
5. 유저 관리 /api/users
   1. 가입 - 아파트 이름, 동 목록에서 고르도록, 없으면 직접 입력 후 db등록 || 나머지 직접 입력
   2. 회원정보 수정 - 패스워드 변경 등
   3. 회원 탈퇴
   4. 정보 확인
6. 신고 /api/report
   1. C
   2. R
   3. U
   4. D

7. 신고예고 /api/noise_schedule
   1. C
   2. R
   3. U
   4. D

# api요청시 주의사항
> axios.defaults.withCredentials = true; //axios 사용 컴포넌트 마다 한번씩 붙여넣을 것

이거 안쓰면 세션ID가 계속 바뀌기 때문에 로그인 유지가 안됨


## API 표
/api/users
<table>
<tr>
<th>URL</th>
<th>Method</th>
<th>설명</th>
<th>request</th>
<th>response</th>
</tr>
<tr>
<td> </td>
<td> </td>
<td> </td>
<td> </td>
<td> </td>
</tr>
</table>
<hr>
/api/boards
<table>
<tr>
<th>URL</th>
<th>Method</th>
<th>설명</th>
<th>request</th>
<th>response</th>
</tr>
<tr>
<td> </td>
<td> </td>
<td> </td>
<td> </td>
<td> </td>
</tr>
</table>