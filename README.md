# 코드스쿼드 도서관 웹앱 프로젝트 (codesquad_library)
***
## 개요
코드스쿼드 도서대여사업에 사용할 만한 웹 애플리케이션을 만드는 것을 목표로 합니다. 

### 사용 언어 및 기술
***
1. 언어 / 프레임워크 : Java 8 + Spring Boot
2. DB 입출력 엔진 : Spring JPA
3. 배포 : Amazon Elastic Beanstalk (예정)
4. DB : MySQL on Amazon RDS 

### 현재까지 구현한 기능 
***
1. User, Book 핵심 클래스와 DB 입출력을 위한 business logic
2. UserInfoController, BookInfoController, IndexController가 요구하는 템플릿 파일
3. 사진 입출력, 타임스탬프 등 코드 중복 제거를 위한 Handler 패키지

### 해결해야 할 문제
***
#### (앞으로의 해결할 문제는 이슈트래커를 적극 활용해 주십시오.)
1. Elastic Beanstalk을 통한 배포 시, 사진을 업로드받을 임시 디렉터리에 접근하지 못하는 문제 <br/>
(참조 : <https://stackoverflow.com/questions/27980478/how-can-i-create-a-tmp-directory-with-elastic-beanstalk>)

### 기여자 목록
***
#### (기여자의 이름과 Slack 계정 아이디를 적어주세요. 참가 의사가 있으신 분은 사전 고지 없이 이름부터 올려주셔도 무방합니다.)

1. 정휘준 / @wheejuni
2. 이태현 / @chocozero
3. 이장희 / @yesdoing
