# Maven 项目重构说明

## 重构目标

本次改动将项目从普通 Java 源码目录结构重构为规范 Maven 项目结构，使项目可以通过 Maven 进行编译、打包、测试和运行。

## 主要操作步骤

1. 检查现有项目结构
   - 原源码位于 `src/App.java`、`src/domain`、`src/repository`、`src/service`、`src/ui` 等旧目录。
   - `pom.xml` 中已配置 Maven 坐标和运行主类，但源码目录和包名尚未与 Maven 标准结构匹配。

2. 创建 Maven 标准目录
   - 新增 `src/main/java`
   - 新增 `src/main/resources`
   - 新增 `src/test/java`
   - 新增 `src/test/resources`

3. 移动源码文件
   - 将 `App.java` 移动到 `src/main/java/com/ljc/textgame/App.java`
   - 将实体类移动到 `src/main/java/com/ljc/textgame/domain`
   - 将仓储类移动到 `src/main/java/com/ljc/textgame/repository`
   - 将业务服务类移动到 `src/main/java/com/ljc/textgame/service`
   - 将控制台界面类移动到 `src/main/java/com/ljc/textgame/ui`

4. 调整包名和导入
   - 主包名统一为 `com.ljc.textgame`
   - 子包包括：
     - `com.ljc.textgame.domain`
     - `com.ljc.textgame.repository`
     - `com.ljc.textgame.service`
     - `com.ljc.textgame.ui`
   - 同步修正所有 `import` 路径。

5. 清理旧目录
   - 删除旧的空目录 `src/domain`
   - 删除旧的空目录 `src/repository`
   - 删除旧的空目录 `src/service`
   - 删除旧的空目录 `src/ui`

6. 修正输入读取问题
   - 原 `LoginUI.readInt()` 使用 `Scanner.hasNextInt()` 和 `nextInt()`，在管道输入或输入结束时可能抛出 `NoSuchElementException`。
   - 现在改为按行读取字符串，再使用 `Integer.parseInt()` 转换为数字。
   - 当标准输入结束时，默认返回退出选项 `3`，避免程序异常终止。

## 当前标准目录结构

```text
TextFightingGameCodex/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ljc/
│   │   │           └── textgame/
│   │   │               ├── App.java
│   │   │               ├── domain/
│   │   │               │   └── User.java
│   │   │               ├── repository/
│   │   │               │   └── UserRepository.java
│   │   │               ├── service/
│   │   │               │   ├── LoginResult.java
│   │   │               │   ├── UserService.java
│   │   │               │   └── ValidationResult.java
│   │   │               └── ui/
│   │   │                   └── LoginUI.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       └── resources/
└── README.md
```

## Maven 配置说明

`pom.xml` 中当前主类配置为：

```xml
<mainClass>com.ljc.textgame.App</mainClass>
```

因此项目可以通过 Maven Exec 插件运行。

## 常用命令

编译项目：

```powershell
mvn compile
```

打包项目：

```powershell
mvn package
```

运行测试：

```powershell
mvn test
```

运行项目：

```powershell
mvn exec:java
```

也可以直接运行编译后的主类：

```powershell
java -cp target\classes com.ljc.textgame.App
```

## 验证结果

本次重构后已执行以下验证：

- `mvn -q compile`：通过
- `mvn -q package`：通过
- `mvn -q test`：通过
- `cmd /c "echo 3| java -cp target\classes com.ljc.textgame.App"`：可以正常启动并退出

## 备注

本次重构主要调整项目结构、包名和运行方式，没有改变注册、登录、验证码、用户校验等核心业务逻辑。
