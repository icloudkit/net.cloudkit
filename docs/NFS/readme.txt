NFS，全名叫Network File System，中文叫网络文件系统，是Linux、UNIX系统的分布式文件系统的一个组成部分，可实现在不同网络上共享远程文件系统。NFS由Sun公司开发，目前已经成为文件服务的一种标准之一（RFC1904，RFC1813）。其最大的功能就是可以通过网络，让不同操作系统的计算机可以共享数据，所以可以把NFS看做是一个文件服务器。NFS缺点是其读写性能比本地硬盘要差一些。

一、NFS服务常见故障排查：

NFS服务出现了故障，主要从以下几个方面检查原因：

（1）检查NFS客户机和服务器的负荷是否太高，Server和Client之间的网络是否正常；

（2）检查/etc/exports文件的正确性；

（3）必要时重启NFS和portmap服务；

（4）运行下列命令重新启动portmap和NFS:

# /etc/init.d/portmap restart
# /etc/init.d/nfs restart
# /etc/init.d/rpcbind restart （在RHEL/CentOS 6.x里面）
# chkconfig portmap on
# chkconfig nfs on
# chkconfig rpcbind on （在RHEL/CentOS 6.x里面）

注意：在RHEL/CentOS 6.x里面，portmap服务改名为rpcbind服务了；顺便说一下，rpcbind服务也是图形界面的关键基础服务，不启动此服务，不能启动图形桌面。

（5） 检查Client上的mount命令或/etc/fstab的语法是否正确;

（6） 查看内核是否支持NFS和RPC服务。一般正常安装的Linux系统都会默认支持NFS和RPC服务，除非你自己重新编译的内核，而且没选择nfs支持选项编译。

二、NFS常见故障解决方法：

1、The rpcbind failure error
故障现象：
nfs mount: server1:: RPC: Rpcbind failure
RPC: Timed Out
nfs mount: retrying: /mntpoint
原因：
第一，可能因为客户机的hosts文件中存在错误的ip地址、主机名或节点名组合；
第二，服务器因为过载而暂时停止服务。

2、The server not responding error
现象：
NFS server server2 not responding, still trying
原因：
第一，网络不通，用ping命令检测一下。
第二，服务器关机。

3、The NFS client fails a reboot error
现象：
启动客户机后停住了，不断显示如下提示信息：
Setting default interface for multicast: add net 224.0.0.0: gateway:
client_node_name.
原因：
在etc/vfstab的mount选项中使用了fg而又无法成功mount服务器上的资源，改成bg或将该行注释掉，直到服务器可用为止。

4、The service not responding error
现象：
nfs mount: dbserver: NFS: Service not responding
nfs mount: retrying: /mntpoint
原因：
第一，当前级别不是级别3，用who -r查看，用init 3切换。
第二，NFS Server守护进程不存在，用ps -ef | grep nfs检查，用/etc/init.d/nfs start启动。

5、The program not registered error
现象：
nfs mount: dbserver: RPC: Program not registered
nfs mount: retrying: /mntpoint
原因：
第一，当前级别不是级别3。
第二，mountd守护进程没有启动，用/etc/init.d/nfs脚本启动NFS守护进程。
第三，看/etc/dfs/dfstab中的条目是否正常。

6、The stale file handle error
现象：
stale NFS file handle
原因：
服务器上的共享资源移动位置了，在客户端使用umount和mount重新挂接就可以了。

7、The unknown host error
现象：
nfs mount: sserver1:: RPC: Unknown host
原因：
hosts文件中的内容不正确。

8、The mount point error
现象：
mount: mount-point /DS9 does not exist.
原因：
该挂接点在客户机上不存在，注意检查命令行或/etc/vfstab文件中相关条目的拼写。

9、The no such file error
现象：
No such file or directory.
原因：
该挂接点在服务器上不存在，注意检查命令行或/etc/vfstab文件中相关条目的拼写。

10、No route to host
错误现象：
# mount 10.10.11.211:/opt/data/xmldb /c2c-web1/data/xmldb -t nfs -o rw
mount: mount to NFS server ’10.10.11.211′ failed: System Error: No route to host.

原因：
防火墙被打开，关闭防火墙。
这个原因很多人都忽视了，如果开启了防火墙（包括iptables和硬件防火墙），NFS默认使用111端口，我们先要检测是否打开了这个端口，还要检查TCP_Wrappers的设定。

11、Not owner
现象：
# mount -F nfs -o rw 10.10.2.3:/mnt/c2c/data/resinfo2 /data/data/resinfo2
nfs mount: mount: /data/data/resinfo2: Not owner

原因：
这是Solaris 10版本挂载较低版本nfs时报的错误。

解决：
需要用-o vers=3参数

示例：
# mount -F nfs -o vers=3 10.10.2.3:/mnt/c2c/data/resinfo2 /data/data/resinfo2

12、RPC: Program not registered & retrying
现象：
nfs mount: 10.10.2.3: : RPC: Program not registered
nfs mount: retrying: /data/data/resinfo2

原因：
没有启动NFS共享端服务。

解决：需要重新启动share端的NFS服务，
Linux:
mount: RPC: Program not registered
# /etc/init.d/nfs restart

Solaris:
mount: RPC: Program not registered
# /etc/rc.d/init.d/nfs restart

13、can’t contact portmapper: RPC: Remote system error – Connection refused
现象：
# exportfs -a
can’t contact portmapper: RPC: Remote system error – Connection refused

原因：
出现这个错误信息是由于server端的portmap没有启动。

解决：
# /etc/init.d/portmap start
