#  图书管理系统 (Book Management System)

简洁高效的现代化图书管理系统后端服务，基于 Java 21 和 Spring Boot 3 构建。

##  项目简介

本项目是图书管理系统的后端核心，采用 RESTful API 设计，提供用户管理、图书借阅、分类管理等功能。基于 Spring Boot 3 + MyBatis-Plus 架构，集成了 JWT 认证和 Swagger 文档，旨在为前端应用提供稳定、安全的数据服务。

##  核心功能

*   **用户管理**：用户注册、登录认证 (JWT)、信息修改、权限控制。
*   **图书管理**：图书的增删改查、分类管理、库存控制。
*   **借阅系统**：图书借阅、归还流程、借阅记录追踪。
*   **API 文档**：集成 Swagger/OpenAPI，自动生成在线接口文档。

##  技术栈

*   **语言**: Java 21
*   **框架**: Spring Boot 3.4.13
*   **ORM**: MyBatis-Plus 3.5.7
*   **数据库**: MySQL 8.0+
*   **认证**: JWT (jjwt 0.11.5)
*   **工具**: Lombok, Maven
*   **API 文档**: SpringDoc OpenAPI 2.2.0

##  快速开始

### 前置要求

*   JDK 21+
*   Maven 3.8+
*   MySQL 8.0+

### 安装步骤

1.  **克隆仓库**
    ```bash
    git clone <repository-url>
    cd book_manage
    ```

2.  **配置数据库**
    *   创建数据库 `book_manage`。
    *   修改 `src/main/resources/application.yml` 中的数据库连接信息：
        ```yaml
        spring:
          datasource:
            url: jdbc:mysql://localhost:3306/book_manage?serverTimezone=GMT%2B8&...
            username: root  # 你的数据库用户名
            password: 'YOUR_PASSWORD' # 你的数据库密码
        ```

3.  **构建项目**
    ```bash
    ./mvnw clean package
    ```

4.  **运行服务**
    ```bash
    java -jar target/book-manage-0.0.1-SNAPSHOT.jar
    ```
    服务启动后，默认运行在 `http://localhost:8080`。

##  API 文档

项目启动后，访问以下地址查看完整的 API 文档和调试接口：

*   **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
*   **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

##  配置说明

主要配置文件位于 `src/main/resources/application.yml`：

| 配置项 | 说明 | 默认值 |
| :--- | :--- | :--- |
| `server.port` | 服务端口 | `8080` |
| `spring.datasource.url` | 数据库连接地址 | `jdbc:mysql://localhost:3306/book_manage...` |
| `spring.datasource.username` | 数据库用户名 | `root` |
| `spring.datasource.password` | 数据库密码 | `123456` |
| `mybatis-plus.mapper-locations` | Mapper XML 路径 | `classpath:mapper/*.xml` |

##  许可证

本项目采用 [MIT License](LICENSE) 许可证。
