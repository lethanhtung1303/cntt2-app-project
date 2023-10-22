# Project CNTT2 (TDTU)

## Technologies
- Java Spring Boot

## Personal information
- Ho&Ten: Le Thanh Tung - MSSV: 51800830

## Software required
- IntelliJ IDEA
- H2 Database

## How to configure & run
- Bước 1: Mở chạy h2.sh (Mac), h2.bat (Win)
  - Create BD:
    - Saved Settings: `Generic H2 (Embedded)`
    - Driver Class: `org.h2.Driver`
    - JDBC URL: `jdbc:h2:~/project`
    - User Name: `user`
    - Password: `test111`
    - Select `Test Connection`
    - Select `Connect`
    - Select `Disconnect`
  - Connect
    - Saved Settings: `Generic H2 (Server)`
    - Driver Class: `org.h2.Driver`
    - JDBC URL: `jdbc:h2:tcp://localhost/~/project;MODE=Oracle`
    - User Name: `user`
    - Password: `test111`
    - Select `Test Connection`
    - Select `Connect`
- Bước 2: Insert databse từ file db/schema.sql
- Bước 3: Chạy task mbGenerator/util/mbGenerator
- Bước 4: Chạy task apispecs/Tasks/openapi tools/openApiGenerate
- Bước 5: Chạy task Tasks/application/bootRun