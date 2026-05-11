# Text Fighting Game

一个基于 Java 控制台的文字战斗游戏练习项目。项目已经重构为标准 Maven 结构，并按简单分层方式组织代码：入口层、界面层、业务层、仓库层和领域对象层。

## 当前功能

- 用户注册
- 用户登录
- 用户名和密码合法性校验
- 登录验证码
- 用户账号保存到本地文件
- 登录后进入游戏主菜单
- 查看角色信息
- 开始简单回合制战斗
- 普通攻击、防御、逃跑
- 战斗胜利后获得经验和金币
- 角色升级后提升生命值、攻击力和防御力

## 技术栈

- Java 21
- Maven
- 控制台交互
- 本地文本文件存储账号数据

## 项目结构

```text
TextFightingGameCodex/
├── pom.xml
├── README.md
├── info.md
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── ljc/
│                   └── textgame/
│                       ├── App.java
│                       ├── domain/
│                       │   ├── Enemy.java
│                       │   ├── Player.java
│                       │   └── User.java
│                       ├── repository/
│                       │   └── UserRepository.java
│                       ├── service/
│                       │   ├── BattleService.java
│                       │   ├── EnemyFactory.java
│                       │   ├── GameService.java
│                       │   ├── LoginResult.java
│                       │   ├── UserService.java
│                       │   └── ValidationResult.java
│                       └── ui/
│                           ├── BattleUI.java
│                           ├── GameUI.java
│                           ├── InputHelper.java
│                           └── LoginUI.java
```

## 分层说明

### App

[App.java](src/main/java/com/ljc/textgame/App.java)

程序入口，负责创建并组装各个对象，例如 `UserRepository`、`UserService`、`GameService`、`BattleService`、`LoginUI` 等。

`App` 不直接处理业务逻辑，只负责启动程序和依赖装配。

### domain

领域对象层，保存游戏中的核心对象。

- [User.java](src/main/java/com/ljc/textgame/domain/User.java)：账号对象，保存用户编号、用户名、密码、账号状态。
- [Player.java](src/main/java/com/ljc/textgame/domain/Player.java)：玩家角色对象，保存等级、经验、生命值、攻击力、防御力、金币等属性。
- [Enemy.java](src/main/java/com/ljc/textgame/domain/Enemy.java)：敌人对象，保存生命值、攻击力、防御力、经验奖励、金币奖励等属性。

### repository

数据仓库层，负责数据保存和读取。

- [UserRepository.java](src/main/java/com/ljc/textgame/repository/UserRepository.java)

当前 `UserRepository` 使用内存列表保存用户，并将账号数据写入本地文件：

```text
data/users.txt
```

文件格式为：

```text
用户ID,用户名,密码,是否启用
```

注意：当前只持久化账号信息，角色等级、经验、金币等游戏存档数据还没有保存到文件。

### service

业务逻辑层，负责核心规则。

- [UserService.java](src/main/java/com/ljc/textgame/service/UserService.java)：处理注册、登录、用户名校验、密码校验、验证码生成。
- [GameService.java](src/main/java/com/ljc/textgame/service/GameService.java)：根据登录用户获取或创建玩家角色，并提供敌人创建逻辑。
- [BattleService.java](src/main/java/com/ljc/textgame/service/BattleService.java)：处理战斗伤害计算、玩家攻击、敌人攻击、胜利结算。
- [EnemyFactory.java](src/main/java/com/ljc/textgame/service/EnemyFactory.java)：创建随机敌人。
- [ValidationResult.java](src/main/java/com/ljc/textgame/service/ValidationResult.java)：封装校验是否成功以及提示信息。
- [LoginResult.java](src/main/java/com/ljc/textgame/service/LoginResult.java)：封装登录是否成功、提示信息和登录用户对象。

### ui

控制台界面层，负责菜单展示和用户输入。

- [LoginUI.java](src/main/java/com/ljc/textgame/ui/LoginUI.java)：登录、注册主界面。
- [GameUI.java](src/main/java/com/ljc/textgame/ui/GameUI.java)：登录后的游戏主菜单。
- [BattleUI.java](src/main/java/com/ljc/textgame/ui/BattleUI.java)：战斗界面。
- [InputHelper.java](src/main/java/com/ljc/textgame/ui/InputHelper.java)：统一处理控制台输入。

## 运行环境

需要安装：

- JDK 21 或更高版本
- Maven

检查版本：

```powershell
java -version
mvn -version
```

## 编译和运行

编译项目：

```powershell
mvn compile
```

运行项目：

```powershell
mvn exec:java
```

打包项目：

```powershell
mvn package
```

运行测试：

```powershell
mvn test
```

也可以直接运行编译后的主类：

```powershell
java -cp target\classes com.ljc.textgame.App
```

## 基本流程

```text
启动程序
  -> 登录或注册账号
  -> 登录成功后进入游戏主菜单
  -> 查看角色信息或开始战斗
  -> 战斗中可以攻击、防御或逃跑
  -> 胜利后获得经验和金币
```

## 账号规则

用户名规则：

- 长度 3 到 8 位
- 只能包含字母和数字
- 不能是纯数字
- 不能和已有用户名重复

密码规则：

- 长度 3 到 8 位
- 只能包含字母和数字
- 不能是纯数字

验证码规则：

- 长度 5 位
- 包含 1 位数字
- 其余为大小写英文字母
- 登录时忽略大小写比较

## 数据保存说明

账号数据会保存到：

```text
data/users.txt
```

该目录已在 `.gitignore` 中忽略，不会提交到 GitHub。这样可以避免把本地账号和密码数据上传到远程仓库。

当前角色数据由 `GameService` 保存在内存中：

```java
private final Map<String, Player> players = new HashMap<>();
```

因此角色数据只在程序运行期间有效，程序关闭后会丢失。后续可以新增 `PlayerRepository` 来保存角色存档。

## 已知问题

- 部分源码中的中文提示可能存在乱码，需要统一源码编码和终端编码为 UTF-8。
- 密码当前是明文保存，只适合作为课程练习项目，不适合真实生产环境。
- 角色存档尚未持久化。
- 目前还没有单元测试。
- `GameService` 和 `EnemyFactory` 都存在敌人创建相关逻辑，后续可以继续统一到 service 内的一个固定入口。

## 后续开发建议

- 修复源码中的中文乱码。
- 增加 `PlayerRepository`，保存角色等级、经验、金币等存档信息。
- 增加 JUnit 测试，覆盖用户校验、登录、战斗结算等核心逻辑。
- 整理敌人创建逻辑，避免多个类同时负责创建敌人。
- 增加装备、背包、技能、商店等玩法模块。
- 对密码进行加密或哈希处理。

## Git 提交说明

常用提交流程：

```powershell
git status
git add .
git commit -m "Update README"
git push
```
