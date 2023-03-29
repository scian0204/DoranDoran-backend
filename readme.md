# 한이음 프로젝트 - 도란도란
## 백엔드(Spring-boot)

프로젝트 생성 - https://start.spring.io/
- spring-version: 3.0.2
- java-version: oracle jdk 17.0.6(https://download.oracle.com/java/17/archive/jdk-17.0.6_windows-x64_bin.exe)
- Dependencies: spring boot devtools, lombok, spring web
- MariaDB : 
```
// 게시글 테이블
CREATE TABLE `Board` (

)

// 유저 테이블
CREATE TABLE `User` (

)
```

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