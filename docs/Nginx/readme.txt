width=1        设置服务器的权重，默认为 1。
slow_start=30s 设置一台不健康的主机变成健康主机，或者当一台主机在被认为不可用变成可用时，将其权重由零恢复到标称值的时间。默认值为零，也就是说，禁用慢启动。
fail_timeout   在指定时间内连接到主机的失败次数，超过该次数该主机被认为不可用，默认为10s。
max_fails      设置由 fail_timeout 定义的时间段内连接该主机的失败次数，以此来断定 fail_timeout 定义的时间段内该主机是否可用，默认为1，零值的话禁用这个数量的尝试。
down           标记当前服务器为永不可用；和 ip_hash 指令一起使用。
backup         将当前服务器标记为备份服务器。当主服务器不可用时，向备用服务器传递请求。

upstream server{
    ip_hash;
    # down 表示单前的server暂时不参与负载
    server 127.0.0.1:8080 down;
    # weight 默认为1.weight越大，负载的权重就越大
    server 127.0.0.1:8081 weight=2;
    server 127.0.0.1:8082;
    # 其它所有的非backup机器down或者忙的时候，请求backup机器
    server 127.0.0.1:8083 backup;
}

如果群里面只有一台主机，那么 max_fails、 fail_timeout 和 slow_start 参数将被忽略，而且这样的主机也永远不会被认为不可用。


http://www.linuxvirtualserver.org/VS-NAT.html
http://www.uml.org.cn/zjjs/201211124.asp

XtraDB
MariaDB

JAVA_OPTS="-server -Dfile.encoding=UTF-8 -Xms4096m -Xmx4096m -XX:PermSize=256M -XX:MaxPermSize=256m -XX:NewSize=256m -XX:MaxNewSize=1024m"


操作系统平台：CentOS5.2
软件：LVS+keepalived

LVS+Keepalived 介绍

LVS

LVS是Linux Virtual Server的简写，意即Linux虚拟服务器，是一个虚拟的服务器集群系统。本项目在1998年5月由章文嵩博士成立，是中国国内最早出现的自由软件项目之一。目前有三种IP负载均衡技术（VS/NAT、VS/TUN和VS/DR）；
十种调度算法（rrr|wrr|lc|wlc|lblc|lblcr|dh|sh|sed|nq）。
Keepalvied
Keepalived在这里主要用作RealServer的健康状态检查以及LoadBalance主机和BackUP主机之间failover的实现

IP配置信息:
LVS-DR-Master          192.168.2.166
LVS-DR-BACKUP          192.168.2.167
LVS-DR-VIP             192.168.2.170
WEB1-Realserver        192.168.2.171
WEB2-Realserver        192.168.2.172
GateWay                192.168.2.253
安装LVS和Keepalvied软件包

1. 下载相关软件包
#mkdir /usr/local/src/lvs
#cd /usr/local/src/lvs
#wget
http://www.linuxvirtualserver.org/software/kernel-2.6/ipvsadm-1.24.tar.gz
#wget
http://www.keepalived.org/software/keepalived-1.1.15.tar.gz

2. 安装LVS和Keepalived
#lsmod |grep ip_vs
#uname -r
2.6.18-53.el5PAE
#ln -s /usr/src/kernels/2.6.18-53.el5PAE-i686/  /usr/src/linux

#tar zxvf ipvsadm-1.24.tar.gz
#cd ipvsadm-1.24
#make && make install
#find / -name ipvsadm  # 查看ipvsadm的位置

#tar zxvf keepalived-1.1.15.tar.gz
#cd keepalived-1.1.15
#./configure  && make && make install
#find / -name keepalived  # 查看keepalived位置

#cp /usr/local/etc/rc.d/init.d/keepalived /etc/rc.d/init.d/
#cp /usr/local/etc/sysconfig/keepalived /etc/sysconfig/
#mkdir /etc/keepalived
#cp /usr/local/etc/keepalived/keepalived.conf /etc/keepalived/
#cp /usr/local/sbin/keepalived /usr/sbin/
#service keepalived start|stop     #做成系统启动服务方便管理.
四. 配置LVS实现负载均衡

  1． LVS-DR，配置LVS脚本实现负载均衡

vi /usr/local/sbin/lvs-dr.sh

#!/bin/bash
# description: start LVS of DirectorServer
#Written by :NetSeek
http://www.linuxtone.org
GW=192.168.2.253

# website director vip.
WEB_VIP=192.168.2.170
WEB_RIP1=192.168.2.171
WEB_RIP2=192.168.2.172
. /etc/rc.d/init.d/functions

logger $0 called with $1

case "$1" in

start)
        # Clear all iptables rules.
        /sbin/iptables -F
        # Reset iptables counters.
        /sbin/iptables -Z
        # Clear all ipvsadm rules/services.
        /sbin/ipvsadm -C

#set lvs vip for dr
        /sbin/ipvsadm --set 30 5 60
        /sbin/ifconfig eth0:0 $WEB_VIP broadcast $WEB_VIP netmask 255.255.255.255 up
        /sbin/route add -host $WEB_VIP dev eth0:0
        /sbin/ipvsadm -A -t $WEB_VIP:80 -s wrr -p 3
        /sbin/ipvsadm -a -t $WEB_VIP:80 -r $WEB_RIP1:80 -g -w 1
        /sbin/ipvsadm -a -t $WEB_VIP:80 -r $WEB_RIP2:80 -g -w 1
        touch /var/lock/subsys/ipvsadm >/dev/null 2>&1

        # set Arp
        /sbin/arping -I eth0 -c 5 -s $WEB_VIP $GW >/dev/null 2>&1
       ;;
stop)
        /sbin/ipvsadm -C
        /sbin/ipvsadm -Z
        ifconfig eth0:0 down
        route del $WEB_VIP  >/dev/null 2>&1
        rm -rf /var/lock/subsys/ipvsadm >/dev/null 2>&1
        /sbin/arping -I eth0 -c 5 -s $WEB_VIP $GW
        echo "ipvsadm stoped"
       ;;

status)

        if [ ! -e /var/lock/subsys/ipvsadm ];then
                echo "ipvsadm is stoped"
                exit 1
        else
                ipvsadm -ln
                echo "..........ipvsadm is OK."
        fi
      ;;

*)
        echo "Usage: $0 {start|stop|status}"
        exit 1
esac

exit 0
2． 配置Realserver脚本.

#!/bin/bash
# Written by NetSeek
# description: Config realserver lo and apply noarp
WEB_VIP=192.168.2.170

. /etc/rc.d/init.d/functions

case "$1" in
start)
       ifconfig lo:0 $WEB_VIP netmask 255.255.255.255 broadcast $WEB_VIP
       /sbin/route add -host $WEB_VIP dev lo:0
       echo "1" >/proc/sys/net/ipv4/conf/lo/arp_ignore
       echo "2" >/proc/sys/net/ipv4/conf/lo/arp_announce
       echo "1" >/proc/sys/net/ipv4/conf/all/arp_ignore
       echo "2" >/proc/sys/net/ipv4/conf/all/arp_announce
       sysctl -p >/dev/null 2>&1
       echo "RealServer Start OK"

       ;;
stop)
       ifconfig lo:0 down
       route del $WEB_VIP >/dev/null 2>&1
       echo "0" >/proc/sys/net/ipv4/conf/lo/arp_ignore
       echo "0" >/proc/sys/net/ipv4/conf/lo/arp_announce
       echo "0" >/proc/sys/net/ipv4/conf/all/arp_ignore
       echo "0" >/proc/sys/net/ipv4/conf/all/arp_announce
       echo "RealServer Stoped"
       ;;
status)
        # Status of LVS-DR real server.
        islothere=`/sbin/ifconfig lo:0 | grep $WEB_VIP`
        isrothere=`netstat -rn | grep "lo:0" | grep $web_VIP`
        if [ ! "$islothere" -o ! "isrothere" ];then
            # Either the route or the lo:0 device
            # not found.
            echo "LVS-DR real server Stopped."
        else
            echo "LVS-DR Running."
        fi
;;
*)
        # Invalid entry.
        echo "$0: Usage: $0 {start|status|stop}"
        exit 1
;;
esac
exit 0

附上realserver机上的/etc/sysctl.conf：

# Kernel sysctl configuration file for Red Hat Linux
#
# For binary values, 0 is disabled, 1 is enabled.  See sysctl(8) and
# sysctl.conf(5) for more details.
# Controls IP packet forwarding
net.ipv4.ip_forward = 1
# Controls source route verification
net.ipv4.conf.default.rp_filter = 1
# Do not accept source routing
net.ipv4.conf.default.accept_source_route = 0
# Controls the System Request debugging functionality of the kernel
kernel.sysrq = 0
# Controls whether core dumps will append the PID to the core filename.
# Useful for debugging multi-threaded applications.
kernel.core_uses_pid = 1
net.ipv4.conf.lo.arp_ignore = 1
net.ipv4.conf.lo.arp_announce = 2
net.ipv4.conf.all.arp_ignore = 1
net.ipv4.conf.all.arp_announce = 2
或者采用secondary ip address方式配置
# vi /etc/sysctl.conf

添加以下内容如上所示：

net.ipv4.conf.lo.arp_ignore = 1
net.ipv4.conf.lo.arp_announce = 2
net.ipv4.conf.all.arp_ignore = 1
net.ipv4.conf.all.arp_announce = 2
#sysctl –p
#ip addr add 61.164.122.8/32 dev lo
#ip add list 查看是否绑定
3. 启动lvs-dr脚本和realserver启本，在DR上可以查看LVS当前状态:
#watch ipvsadm –ln

五．利用Keepalvied实现负载均衡和和高可用性

1.配置在主负载均衡服务器上配置keepalived.conf
#vi /etc/keepalived/keepalived.conf (主调度器)
! Configuration File for keepalived
global_defs {
   notification_email {

acassen@firewall.loc

failover@firewall.loc

sysadmin@firewall.loc
   }
   notification_email_from
Alexandre.Cassen@firewall.loc
   smtp_server 127.0.0.1
   smtp_connect_timeout 30
   router_id LVS_DEVEL
}
vrrp_instance VI_1 {
    state MASTER
    interface eth0
    virtual_router_id 51
    priority 100
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.2.170
    }
}
virtual_server 192.168.2.170 80 {
    delay_loop 6
    lb_algo wrr
    lb_kind DR
    persistence_timeout 60
    protocol TCP

    real_server 192.168.2.171 80 {
        weight 3
        TCP_CHECK {
        connect_timeout 10
        nb_get_retry 3
        delay_before_retry 3
        connect_port 80
}
    }
    real_server 192.168.2.172 80 {
        weight 3
        TCP_CHECK {
        connect_timeout 10
        nb_get_retry 3
        delay_before_retry 3
        connect_port 80
        }
     }
}

在备用调度器上：
#vi /etc/keepalived/keepalived.conf (备用调度器)
! Configuration File for keepalived
global_defs {
   notification_email {

acassen@firewall.loc

failover@firewall.loc

sysadmin@firewall.loc
   }
   notification_email_from
Alexandre.Cassen@firewall.loc
   smtp_server 127.0.0.1
   smtp_connect_timeout 30
   router_id LVS_DEVEL
}
vrrp_instance VI_1 {
    state BACKUP
    interface eth0
    virtual_router_id 51
    priority 99
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.2.170
    }
}
virtual_server 192.168.2.170 80 {
    delay_loop 6
    lb_algo wrr
    lb_kind DR
    persistence_timeout 60
    protocol TCP

    real_server 192.168.2.171 80 {
        weight 3
        TCP_CHECK {
        connect_timeout 10
        nb_get_retry 3
        delay_before_retry 3
        connect_port 80
}
    }
    real_server 192.168.2.172 80 {
        weight 3
        TCP_CHECK {
        connect_timeout 10
        nb_get_retry 3
        delay_before_retry 3
        connect_port 80
        }
     }
}

2. BACKUP服务器同上配置，先安装lvs再按装keepalived,仍后配置/etc/keepalived/keepalived.conf，只需将红色标示的部分改一下即可.
3. vi /etc/rc.local
   #/usr/local/sbin/lvs-dr.sh  将lvs-dr.sh这个脚本注释掉。
   #/usr/local/sbin/lvs-dr.sh stop 停止lvs-dr脚本
   #/etc/init.d/keepalived start  启动keepalived 服务，keepalived就能利用keepalived.conf 配
   置文件，实现负载均衡和高可用.
4. 查看lvs服务是否正常

#watch ipvsadm –ln
IP Virtual Server version 1.2.1 (size=4096)
Prot LocalAddressort Scheduler Flags
  -> RemoteAddressort           Forward Weight ActiveConn InActConn
TCP  61.164.122.8:80 wrr persistent 60
  -> 61.164.122.10:80            Route   3      0          0
  -> 61.164.122.9:80             Route   3      0          0
复制代码
#tail –f /var/log/message  监听日志，查看状态，测试LVS负载均衡及高可用性是否有效。
5．停Master服务器的keepalived服务，查看BAKCUP服务器是否能正常接管服务。
四．相关参考
1．LVS 基础知识汇总
LVS的算法介绍
http://www.linuxtone.org/viewthread.php?tid=69
学习LVS的三种转发模式
http://www.linuxtone.org/viewthread.php?tid=77
LVS中的IP负载均衡技术
http://www.linuxtone.org/viewthread.php?tid=68
更多的请到
http://www.linuxtone.org
负载均衡版查看
Keepalived 相关参考资料。

http://www.keepalived.org/documentation.html


添加用户组
sudo adduser --system --no-create-home --disabled-password --group nginx
groupadd -f nginx
useradd -g nginx nginx



域名跳转
if ($host != 'mbs.molmcs.com') {
    rewrite ^/(.*)$ http://mbs.molmcs.com/$1 permanent;
}
