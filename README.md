# acm-team-website
ACM/ICPC程序设计实验室网站(基于java web struts2)

##数据库
    Mysql 5.5.59
    数据库名：team
    数据库建表脚本：src/team.sql

##框架
- struts2.2.5
- Hibernate5
- jquery2.2.1+bootstrap3.3.7
- wangeditor(http://www.wangeditor.com/)
- ckplayer(http://www.ckplayer.com/)

##特别说明
用户表(user)权限字段(power)说明：

    使用二进制压缩，从低位到高位依次 
    管理员 
    学习资料权限
    锁定个人信息
    学习资料上传权限