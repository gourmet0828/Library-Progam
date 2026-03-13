# Library-Progam

## 프로젝트 소개
이 프로젝트는 Java로 구현한 도서관 관리 프로그램이다.  
콘솔 기반 프로그램으로 도서 정보, 회원 정보, 대출 및 반납 기록을 관리할 수 있도록 구현하였다.

프로그램은 여러 클래스로 기능을 분리하여 구조적으로 설계하였으며, 텍스트 파일을 이용하여 데이터를 저장하고 불러오는 방식으로 동작한다.

---

## 개발 환경

- Language : Java
- IDE : IntelliJ IDEA
- OS : Windows 10 / Windows 11
- 실행 방식 : 콘솔 프로그램

---

## 주요 기능

### 도서 관리
- 도서 등록
- 도서 목록 조회
- 도서 정보 수정
- 도서 삭제

### 회원 관리
- 회원 등록
- 회원 정보 조회
- 회원 정보 수정
- 회원 삭제

### 대출 관리
- 도서 대출
- 도서 반납
- 대출 기록 관리

### 로그 관리
- 프로그램 사용 기록 저장
- 대출 및 반납 기록 관리

---

## 데이터 저장 방식

프로그램에서 사용하는 데이터는 텍스트 파일을 이용하여 저장한다.

사용되는 데이터 파일 예시

Author_Data.txt  
BookList.txt  
BookLoanList.txt  
LoanDuration.txt  
LocalDate.txt  
Log.txt  

프로그램 실행 시 해당 파일을 읽어 데이터를 불러오며, 프로그램 종료 시 변경된 내용을 다시 저장한다.

---

## 프로젝트 구조
src
Account
AuthorList
Constant
Date
ItemList
LoanList
Log
Processor
Strategy
LibraryApp.java
Main.java


### 구조 설명

Account  
회원 정보 관련 클래스

AuthorList  
저자 정보 관리

ItemList  
도서 목록 관리

LoanList  
도서 대출 및 반납 정보 관리

Processor  
프로그램 주요 기능 처리

Strategy  
기능 처리 로직 분리

Log  
프로그램 사용 기록 관리

Constant  
프로그램에서 사용하는 상수 정의

Date  
날짜 관련 처리

---

## 실행 방법

1. IntelliJ IDEA에서 프로젝트를 실행한다.
2. `Main.java`를 실행한다.
3. 콘솔에 출력되는 메뉴를 통해 원하는 기능을 선택하여 사용할 수 있다.

---

## 프로젝트 목적

Java 기반 프로그램 설계와 구현을 통해  
객체지향 구조 설계와 파일 입출력을 활용한 데이터 관리 방식을 학습하는 것을 목표로 하였다.
