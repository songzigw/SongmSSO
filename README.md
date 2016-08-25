# SongmSSO
松美单点登入服务器（Songm single sign on server）

## 概述

**松美SSO**(SongmSSO)为单点登入服务器。

## 环境要求

Jdk8

## 使用指南

启动方式
```
SSOApplication.main();
```

## 开发指南

+ [Java语言后台服务开发API](https://github.com/songzigw/songm.sso.backstage.java)

### 协议封装

协议分为两部分，数据包头和包体

| 包头 | 包体 |
| --- | --- |
| 描述数据包整体信息，占整个数据包的20个字节(Byte) | 以Json格式封装了实际数据 |

----- 包头部分 -----

- Version
占2byte，描述协议版本号

- HeaderLen
占2byte，描述包头的长度

- Operation
占4byte，描述具体操作项，告诉程序数据的处理行为

- Sequence
占8byte，每次操作的唯一标识，每次操作生成一个唯一的序列码

- PacketLen
占4byte，描述整个数据包的字节大小（固定包头20byte + 包体实际字节）

----- 包体部分 -----

- Body
占用字节，以实际字节长度为准，以Json格式封装了实际数据

### 请求操作项

1、*连接并授权*

----- 请求数据包 -----
```
Operation = 1
Body = {}
```

----- 响应数据包 -----
```
Operation = 1
Body = {}
```

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## 开发者

作者: 张松 ([zhangsong](mailto:songzigw@163.com)) 

版本: 0.1 (2016/8/21)

