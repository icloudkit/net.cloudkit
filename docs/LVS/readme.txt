http://www.linuxvirtualserver.org/zh/lvs1.html
http://www.linuxvirtualserver.org/zh/lvs2.html
http://www.linuxvirtualserver.org/zh/lvs3.html
http://www.linuxvirtualserver.org/zh/lvs4.html

1.LVS简介
最近学习了LVS（Linux Virtual Server）的集群技术，把搜集的一些资料整理一下。LVS（Linux Virtual Server）其实就是针对高可伸缩、高可用网络服务的需求，给出了基于IP层和基于内容请求分发的负载平衡调度解决方法，并在Linux内核中实现了这些方法，将一组服务器构成一个实现可伸缩的、高可用网络服务的虚拟服务器。
所以，lvs需要内核有ipvs支持，确保你的内核支持ipvs后，只需安装ipvsadm就可以把一台服务器配置成负载调度器（Load Balancer）。对外提供服务的IP，也就是我们访问的IP称做VIP。调度器LB的任务主要是分发请求，真正处理的是真实服务器(Real Server)。这就是LVS工作的基本方式和一些基本术语，下面是一张结构图。

负载调度器（load balancer）：它是整个集群对外面的前端机，负责将客户的请求发送到一组服务器上执行，而客户认为服务是来自一个IP地址（我们可称之为虚拟IP地址）上的。
服务器池（server pool）：是一组真正执行客户请求的服务器，执行的服务有WEB、MAIL、FTP和DNS等。
共享存储（shared storage）：它为服务器池提供一个共享的存储区，这样很容易使得服务器池拥有相同的内容，提供相同的服务。

2.IP虚拟服务器软件IPVS
IPVS软件实现了这三种IP负载均衡技术，它们的大致原理如下：

Virtual Server via Network Address Translation（VS/NAT）
通过网络地址转换，调度器重写请求报文的目标地址，根据预设的调度算法，将请求分派给后端的真实服务器；真实服务器的响应报文通过调度器时，报文的源地址被重写，再返回给客户，完成整个负载调度过程。

Virtual Server via IP Tunneling（VS/TUN）
采用NAT技术时，由于请求和响应报文都必须经过调度器地址重写，当客户请求越来越多时，调度器的处理能力将成为瓶颈。为了解决这个问题，调度器把请求报 文通过IP隧道转发至真实服务器，而真实服务器将响应直接返回给客户，所以调度器只处理请求报文。由于一般网络服务应答比请求报文大许多，采用 VS/TUN技术后，集群系统的最大吞吐量可以提高10倍。

Virtual Server via Direct Routing（VS/DR）
VS/DR通过改写请求报文的MAC地址，将请求发送到真实服务器，而真实服务器将响应直接返回给客户。同VS/TUN技术一样，VS/DR技术可极大地 提高集群系统的伸缩性。这种方法没有IP隧道的开销，对集群中的真实服务器也没有必须支持IP隧道协议的要求，但是要求调度器与真实服务器都有一块网卡连 在同一物理网段上。

三种IP负载均衡技术的优缺点比较:
杂项　　　　　　　　　VS/NAT　　　　 VS/TUN　　　　　　VS/DR
服务器操作系统　　　　任意　　　　　　支持隧道　　　　　多数(支持Non-arp )
服务器网络　　　　　　私有网络　　　　局域网/广域网　　 局域网
服务器数目(100M网络) 10-20　　　　　 100　　　　　　　 多(100)
服务器网关　　　　　　负载均衡器　　　自己的路由　　　　自己的路由
效率　　　　　　　　　一般　　　　　　高　　　　　　　　最高


针对不同的网络服务需求和服务器配置，IPVS调度器实现了如下八种负载调度算法：
轮叫（Round Robin）
调度器通过”轮叫”调度算法将外部请求按顺序轮流分配到集群中的真实服务器上，它均等地对待每一台服务器，而不管服务器上实际的连接数和系统负载。

加权轮叫（Weighted Round Robin）
调度器通过”加权轮叫”调度算法根据真实服务器的不同处理能力来调度访问请求。这样可以保证处理能力强的服务器处理更多的访问流量。调度器可以自动问询真实服务器的负载情况，并动态地调整其权值。

最少链接（Least Connections）
调度器通过”最少连接”调度算法动态地将网络请求调度到已建立的链接数最少的服务器上。如果集群系统的真实服务器具有相近的系统性能，采用”最小连接”调度算法可以较好地均衡负载。

加权最少链接（Weighted Least Connections）
在集群系统中的服务器性能差异较大的情况下，调度器采用”加权最少链接”调度算法优化负载均衡性能，具有较高权值的服务器将承受较大比例的活动连接负载。调度器可以自动问询真实服务器的负载情况，并动态地调整其权值。

基于局部性的最少链接（Locality-Based Least Connections）
“基于局部性的最少链接” 调度算法是针对目标IP地址的负载均衡，目前主要用于Cache集群系统。该算法根据请求的目标IP地址找出该目标IP地址最近使用的服务器，若该服务器 是可用的且没有超载，将请求发送到该服务器；若服务器不存在，或者该服务器超载且有服务器处于一半的工作负载，则用”最少链接”的原则选出一个可用的服务 器，将请求发送到该服务器。

带复制的基于局部性最少链接（Locality-Based Least Connections with Replication）
“带复制的基于局部性最少链接”调度算法也是针对目标IP地址的负载均衡，目前主要用于Cache集群系统。它与LBLC算法的不同之处是它要维护从一个 目标IP地址到一组服务器的映射，而LBLC算法维护从一个目标IP地址到一台服务器的映射。该算法根据请求的目标IP地址找出该目标IP地址对应的服务 器组，按”最小连接”原则从服务器组中选出一台服务器，若服务器没有超载，将请求发送到该服务器，若服务器超载；则按”最小连接”原则从这个集群中选出一 台服务器，将该服务器加入到服务器组中，将请求发送到该服务器。同时，当该服务器组有一段时间没有被修改，将最忙的服务器从服务器组中删除，以降低复制的 程度。

目标地址散列（Destination Hashing）
“目标地址散列”调度算法根据请求的目标IP地址，作为散列键（Hash Key）从静态分配的散列表找出对应的服务器，若该服务器是可用的且未超载，将请求发送到该服务器，否则返回空。

源地址散列（Source Hashing）
“源地址散列”调度算法根据请求的源IP地址，作为散列键（Hash Key）从静态分配的散列表找出对应的服务器，若该服务器是可用的且未超载，将请求发送到该服务器，否则返回空。

3.实现VS/NAT
环境：LB：ipvsadm，RS：LAMP
ipvsadm可以到官网下载安装：http://www.linuxvirtualserver.org/software/index.html；LAMP安装省略，网上资料很多。
VIP:192.168.1.100
LB:10.3.37.100
RS:10.3.37.101
RS:10.3.37.102
RS:10.3.37.103
RS:10.3.37.104

LB配置：
外部地址为192.168.1.100 内部地址为10.3.37.100，LVS在VS/NAT、VS/DR和VS/TUN3种方式下均需要打开ip_forward功能。

vi /etc/sysctl.conf
#加入一行
net.ipv4.ip_forward = 1
#退出编辑，执行sysctl -p使配置生效。
sysctl -p
ipvs的脚本（ipvsadin详解见下文）

复制代码
#!/bin/sh

ipvsadm -C
ipvsadm -A -t 192.168.1.100:80 -s wlc
ipvsadm -a -t 192.168.1.100:80 -r 10.3.37.101:80 -m
ipvsadm -a -t 192.168.1.100:80 -r 10.3.37.102:80 -m
ipvsadm -a -t 192.168.1.100:80 -r 10.3.37.103:80 -m
ipvsadm -a -t 192.168.1.100:80 -r 10.3.37.104:80 -m
复制代码
RealServer的配置（网关设置成BL内网IP:10.3.37.100）：
网关配置(/etc/sysconfig/network 文件的内容如下)

NETWORKING=yes
HOSTNAME=localhost.localdomain
GATEWAY=10.3.37.100
网卡配置文件(/etc/sysconfig/network-scripts/ifcfg-eth0的内容如下)
DEVICE=eth0
ONBOOT=yes
BOOTPROTO=static
IPADDR=10.3.37.101
NETMASK=255.255.255.0
BROADCAST=10.3.37.255

4.实现VS/DR
只要知道了原理和ipvsadm基本使用，配置和VS/NAT没多大差别，关键在于RS上要关闭arp。
RealServer的配置：

复制代码
vi /etc/sysctl.conf
#加入一行
net.ipv4.ip_forward = 1
net.ipv4.conf.lo.arp_ignore = 1
net.ipv4.conf.lo.arp_announce = 2
net.ipv4.conf.all.arp_ignore = 1
net.ipv4.conf.all.arp_announce = 2
#退出编辑，执行sysctl -p使配置生效。
sysctl -p
复制代码

5.ipvsadm详解
1，virtual-service-address:是指虚拟服务器的ip 地址
2，real-service-address:是指真实服务器的ip 地址
3，scheduler：调度方法
(lna@networksbase.com 翻译 ipvsadm v1.21 2004 年4 月)
ipvsadm 的用法和格式如下：
ipvsadm -A|E -t|u|f virutal-service-address:port [-s scheduler] [-p
[timeout]] [-M netmask]
ipvsadm -D -t|u|f virtual-service-address
ipvsadm -C
ipvsadm -R
ipvsadm -S [-n]
ipvsadm -a|e -t|u|f service-address:port -r real-server-address:port
[-g|i|m] [-w weight]
ipvsadm -d -t|u|f service-address -r server-address
ipvsadm -L|l [options]
ipvsadm -Z [-t|u|f service-address]
ipvsadm --set tcp tcpfin udp
ipvsadm --start-daemon state [--mcast-interface interface]
ipvsadm --stop-daemon
ipvsadm -h
命令选项解释：
有两种命令选项格式，长的和短的，具有相同的意思。在实际使用时，两种都可
以。
-A --add-service 在内核的虚拟服务器表中添加一条新的虚拟服务器记录。也
就是增加一台新的虚拟服务器。
-E --edit-service 编辑内核虚拟服务器表中的一条虚拟服务器记录。
-D --delete-service 删除内核虚拟服务器表中的一条虚拟服务器记录。
-C --clear 清除内核虚拟服务器表中的所有记录。
-R --restore 恢复虚拟服务器规则
-S --save 保存虚拟服务器规则，输出为-R 选项可读的格式
-a --add-server 在内核虚拟服务器表的一条记录里添加一条新的真实服务器
记录。也就是在一个虚拟服务器中增加一台新的真实服务器
-e --edit-server 编辑一条虚拟服务器记录中的某条真实服务器记录
-d --delete-server 删除一条虚拟服务器记录中的某条真实服务器记录
-L|-l --list 显示内核虚拟服务器表
-Z --zero 虚拟服务表计数器清零（清空当前的连接数量等）
--set tcp tcpfin udp 设置连接超时值
--start-daemon 启动同步守护进程。他后面可以是master 或backup，用来说
明LVS Router 是master 或是backup。在这个功能上也可以采用keepalived 的
VRRP 功能。
--stop-daemon 停止同步守护进程
-h --help 显示帮助信息
其他的选项:
-t --tcp-service service-address 说明虚拟服务器提供的是tcp 的服务
[vip:port] or [real-server-ip:port]
-u --udp-service service-address 说明虚拟服务器提供的是udp 的服务
[vip:port] or [real-server-ip:port]
-f --fwmark-service fwmark 说明是经过iptables 标记过的服务类型。
-s --scheduler scheduler 使用的调度算法，有这样几个选项
rr|wrr|lc|wlc|lblc|lblcr|dh|sh|sed|nq,
默认的调度算法是： wlc.
-p --persistent [timeout] 持久稳固的服务。这个选项的意思是来自同一个客
户的多次请求，将被同一台真实的服务器处理。timeout 的默认值为300 秒。
-M --netmask netmask persistent granularity mask
-r --real-server server-address 真实的服务器[Real-Server:port]
-g --gatewaying 指定LVS 的工作模式为直接路由模式（也是LVS 默认的模式）
-i --ipip 指定LVS 的工作模式为隧道模式
-m --masquerading 指定LVS 的工作模式为NAT 模式
-w --weight weight 真实服务器的权值
--mcast-interface interface 指定组播的同步接口
-c --connection 显示LVS 目前的连接 如：ipvsadm -L -c
--timeout 显示tcp tcpfin udp 的timeout 值 如：ipvsadm -L --timeout
--daemon 显示同步守护进程状态
--stats 显示统计信息
--rate 显示速率信息
--sort 对虚拟服务器和真实服务器排序输出
--numeric -n 输出IP 地址和端口的数字形式
